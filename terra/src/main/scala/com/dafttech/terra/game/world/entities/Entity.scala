package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, TilePosition}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.persistence.{GameObject, Persistent}
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.game.world.{Chunk, Facing, World}
import com.dafttech.terra.resources.Options
import monix.eval.Task

import scala.concurrent.duration._

abstract class Entity(pos: Vector2d, s: Vector2d)(implicit val world: World) extends GameObject with IDrawableInWorld {
  addToWorld(world, pos)

  private[entities] var chunk: Chunk = _
  @Persistent protected var position: Vector2d = pos
  @Persistent protected var velocity: Vector2d = Vector2d.Zero
  protected var accelleration: Vector2d = Vector2d.Zero
  @Persistent protected var size: Vector2d = s
  @Persistent protected var rotation: Double = 0

  protected var color: Color = Color.WHITE
  private[entities] var gravityFactor: Double = 1
  protected var inAir = false
  protected var inWorld = true
  @Persistent protected var isDynamicEntity: Boolean = false

  def setMidPos(pos: Vector2d): Unit = setPosition(pos + (-size.x * Options.BLOCK_SIZE / 2f, -size.y * Options.BLOCK_SIZE / 2f))

  def getMidPos: Vector2d = getPosition + (size.x * Options.BLOCK_SIZE / 2f, size.y * Options.BLOCK_SIZE / 2f)

  def setColor(clr: Color): Unit = color = clr

  def setAlpha(v: Float): Unit = color.a = v

  def setRotation(angle: Double): Unit = rotation = angle

  def getPosition: Vector2d = position

  def setPosition(pos: Vector2d): Entity = {
    val newChunk = world.getOrCreateChunk(pos)
    if (newChunk != null && (chunk ne newChunk)) {
      addToWorld(newChunk, pos)
      onRechunk(newChunk, pos)
    }
    position = pos
    this
  }

  def onRechunk(newChunk: Chunk, pos: Vector2d): Unit = {
  }

  def remove: Boolean =
    if (chunk != null) {
      inWorld = false
      chunk.removeEntity(this)
    } else
      false

  private def addToWorld(world: World, pos: Vector2d): Unit = addToWorld(world.getOrCreateChunk(pos), pos)

  private def addToWorld(chunk: Chunk, pos: Vector2d): Unit = {
    remove
    inWorld = true
    if (chunk.addEntity(this)) this.chunk = chunk
  }

  def getWorld: World = world

  def isInAir: Boolean = inAir

  def setHasGravity(v: Boolean): Unit = if (!v) gravityFactor = 0

  def setGravityFactor(f: Double): Unit = gravityFactor = f

  def setSize(size: Vector2d): Unit = this.size = size

  def setSize(x: Float, y: Float): Unit = setSize(Vector2d(x, y))

  @SuppressWarnings(Array("unused"))
  private def drawRect(rect: Rectangle, rend: ShapeRenderer, color: Color): Unit = {
    rend.begin(ShapeType.Filled)
    rend.setColor(color.r, color.g, color.b, 1)
    var v2 = Vector2d(rect.x, rect.y)
    v2 = v2.toRenderPosition(getPosition)
    rend.rect(v2.x.toFloat, v2.y.toFloat, rect.width, rect.height)
    rend.flush()
    rend.end()
  }

  def collidesWith(e: Entity): Boolean = true

  def hasEntityCollision: Boolean = false

  def checkEntityCollisions(): Unit = {
    val oVel = velocity
    for (entity <- chunk.getLocalEntities) {
      if (!((entity == this) || !(entity.collidesWith(this) && this.collidesWith(entity)) || velocity.`length²` < entity.velocity.`length²`)) {
        val otherRect = Vector2d(entity.getPosition.x, entity.getPosition.y).rectangleTo(Vector2d(entity.getSize.x * Options.BLOCK_SIZE, entity.getSize.y * Options.BLOCK_SIZE))
        val playerRect = Vector2d(getPosition.x, getPosition.y).rectangleTo(Vector2d(Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y))
        if (collisionDetect(oVel, playerRect, otherRect)) onEntityCollision(entity)
      }
    }
  }

  def checkTerrainCollisions(world: World): Unit = {
    val mid = getPosition.toWorldPosition
    val oVel = velocity

    for {
      x <- mid.x - 1 to mid.x + 2 + size.x.toInt
      y <- mid.y - 1 to mid.y + 2 + size.y.toInt
    } {
      if (world.getTile(Vector2i(x, y)) != null && world.getTile(Vector2i(x, y)).isCollidableWith(this)) {
        val tileRect = new Rectangle(x * Options.BLOCK_SIZE, y * Options.BLOCK_SIZE, Options.BLOCK_SIZE, Options.BLOCK_SIZE)
        val playerRect = Vector2d(getPosition.x, getPosition.y).rectangleTo(Vector2d(Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y))
        if (collisionDetect(oVel, playerRect, tileRect)) onTerrainCollision(new TilePosition(world, Vector2i(x, y)))
      }
    }
  }

  def onTerrainCollision(tilePosition: TilePosition): Unit = ()

  def onEntityCollision(e: Entity): Unit = ()

  def collisionDetect(oVel: Vector2d, a: Rectangle, b: Rectangle): Boolean = {
    if (a.overlaps(b)) {
      var fVertical: Facing = null
      var fHorizontal: Facing = null
      var distVertical: Double = 0
      var distHorizontal: Double = 0
      var posVertical: Double = 0
      var posHorizontal: Double = 0
      var hcv = false
      var hch = false
      if (oVel.y > 0) {
        fVertical = Facing.Bottom
        distVertical = (a.y + a.height) - b.y
        posVertical = b.y - 0.01f - a.height
        hcv = true
      }
      else if (oVel.y < 0) {
        fVertical = Facing.Top
        distVertical = (b.y + b.height) - a.y
        posVertical = (b.y + b.height) + 0.01f
        hcv = true
      }
      if (oVel.x > 0) {
        fHorizontal = Facing.Right
        distHorizontal = (a.x + a.width) - b.x
        posHorizontal = b.x - 0.01f - a.width
        hch = true
      }
      else if (oVel.x < 0) {
        fHorizontal = Facing.Left
        distHorizontal = (b.x + b.width) - a.x
        posHorizontal = (b.x + b.width) + 0.01f
        hch = true
      }
      if ((hcv && !hch) || ((hcv && hch) && distVertical < distHorizontal)) {
        collisionResponse(fVertical, posVertical)
        return true
      }
      if ((hch && !hcv) || ((hcv && hch) && !(distVertical < distHorizontal))) {
        collisionResponse(fHorizontal, posHorizontal)
        return true
      }
    }
    false
  }

  def collisionResponse(facing: Facing, `val`: Double): Unit = {
    if (facing.isVertical) {
      velocity = velocity.withY(0)
      setPosition(getPosition.withY(`val`))
    }
    else {
      velocity = velocity.withX(0)
      setPosition(getPosition.withX(`val`))
    }
    if (facing eq Facing.Bottom) inAir = false
  }

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    val screenVec = this.getPosition.toRenderPosition(pointOfView.getPosition)
    screen.batch.setColor(color)
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(
      image,
      screenVec.x.toFloat,
      screenVec.y.toFloat,
      (Options.BLOCK_SIZE * size.x / 2).toFloat,
      (Options.BLOCK_SIZE * size.y / 2).toFloat,
      (Options.BLOCK_SIZE * size.x).toFloat,
      (Options.BLOCK_SIZE * size.y).toFloat,
      1, 1, rotation.toFloat)
  }

  def getImage: Task[TextureRegion]

  def setVelocity(velocity: Vector2d): Entity = {
    this.velocity = velocity
    this
  }

  def addForce(f: Vector2d): Entity = {
    accelleration = accelleration + f
    this
  }

  def addVelocity(v: Vector2d): Entity = {
    velocity = velocity + v
    this
  }

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    val newDelta = delta * Options.BLOCK_SIZE
    if (gravityFactor != 0) addForce(Vector2d(0, 9.81f * gravityFactor))
    velocity = velocity + (accelleration.x * newDelta, accelleration.y * newDelta)
    accelleration = Vector2d.Zero
    if (velocity.`length²` > 0) {
      val stepLength = 10f / velocity.length.toFloat
      inAir = true
      for (i <- Iterable.iterate(0f, (newDelta / stepLength).toInt)(_ + stepLength)) {
        var asl = stepLength
        if (i + asl > newDelta) asl -= (i + asl) - newDelta

        setPosition(getPosition + (velocity * asl))

        if (!inWorld) return

        checkTerrainCollisions(world.localPlayer.getWorld)
        if (hasEntityCollision) checkEntityCollisions()
      }
    }
    velocity = velocity.withY(velocity.y * (1 - 0.025f * newDelta))
    velocity = velocity.withX(velocity.x * (1 - getCurrentFriction * newDelta))
    if (alignToVelocity && velocity.`length²` > 0.1f) setRotation(velocity.rotation + getVelocityOffsetAngle)
    if (isDynamicEntity && !isInRenderRange(tilePosition.world.localPlayer)) tilePosition.world.removeEntity(this)
  }

  def alignToVelocity: Boolean = false

  def getVelocityOffsetAngle: Double = 0

  def getUndergroundTile: Tile = {
    val pos = (position + (size.x * Options.BLOCK_SIZE, size.y * Options.BLOCK_SIZE)).toWorldPosition
    world.getTile(Vector2i(pos.x, pos.y + 1))
  }

  def getCurrentFriction: Double = {
    if (getUndergroundTile != null && !inAir) return getUndergroundTile.getWalkFriction
    getInAirFriction
  }

  def getCurrentAcceleration: Double = {
    if (getUndergroundTile != null && !inAir) return getUndergroundTile.getWalkAcceleration
    1
  }

  def getInAirFriction: Double = 1

  def isInRenderRange(player: Player): Boolean = {
    val sx = 2 + Gdx.graphics.getWidth / Options.BLOCK_SIZE / 2
    val sy = 2 + Gdx.graphics.getHeight / Options.BLOCK_SIZE / 2
    if (position.x >= player.getPosition.x - sx * Options.BLOCK_SIZE && position.x <= player.getPosition.x + sx * Options.BLOCK_SIZE && position.y >= player.getPosition.y - sy * Options.BLOCK_SIZE && position.y <= player.getPosition.y + sy * Options.BLOCK_SIZE) return true
    false
  }

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  def getSize: Vector2d = size

  final def tick(world: World): Unit = onTick(world)

  def onTick(world: World): Unit = {
  }
}