package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.{ Body, BodyDef, PolygonShape }
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{ Vector2d, Vector2i }
import com.dafttech.terra.engine.{ AbstractScreen, IDrawableInWorld, TilePosition }
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.persistence.{ GameObject, Persistent }
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.game.world.{ Facing, GameWorld }
import com.dafttech.terra.resources.Options
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK
import com.dafttech.terra.resources.Options.PIXELS_PER_METER
import monix.eval.Task

import scala.concurrent.duration._

abstract class Entity(pos: Vector2d)(implicit val world: GameWorld) extends GameObject with IDrawableInWorld {
  val bodyDef = new BodyDef

  bodyDef.`type` = BodyDef.BodyType.DynamicBody
  bodyDef.position.set(pos.x.toFloat, pos.y.toFloat)
  bodyDef.fixedRotation = true

  val shape = new PolygonShape()

  shape.setAsBox(METERS_PER_BLOCK/2, METERS_PER_BLOCK/2)

  val body = world.physicsWorld.createBody(bodyDef)

  body.createFixture(shape, 1)
  shape.dispose()

  protected var color: Color = Color.WHITE
  private[entities] var gravityFactor: Double = 1
  protected var inAir = false

  world.addEntity(this)

  @Persistent protected var isDynamicEntity: Boolean = false

  def setColor(clr: Color): Unit = color = clr

  def setAlpha(v: Float): Unit = color.a = v

  def getPosition: Vector2d = {
    val pos = body.getPosition

    Vector2d(pos.x, pos.y)
  }

  def setPosition(pos: Vector2d): Entity = ???

  def getWorld: GameWorld = world

  def isInAir: Boolean = inAir

  def setHasGravity(v: Boolean): Unit = if (!v) gravityFactor = 0

  def setGravityFactor(f: Double): Unit = gravityFactor = f

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

  /*def checkEntityCollisions(): Unit = {
    val oVel = velocity
    for (entity <- world.getEntities) {
      if (!((entity == this) || !(entity.collidesWith(this) && this.collidesWith(entity)) || velocity.`length²` < entity.velocity.`length²`)) {
        val otherRect = Vector2d(entity.getPosition.x, entity.getPosition.y).rectangleTo(Vector2d(entity.getSize.x * Options.BLOCK_SIZE, entity.getSize.y * Options.BLOCK_SIZE))
        val playerRect = Vector2d(getPosition.x, getPosition.y).rectangleTo(Vector2d(Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y))
        if (collisionDetect(oVel, playerRect, otherRect)) onEntityCollision(entity)
      }
    }
  }

  def checkTerrainCollisions(world: GameWorld): Unit = {
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
  }*/

  def onTerrainCollision(tilePosition: TilePosition): Unit = ()

  def onEntityCollision(e: Entity): Unit = ()

  /*def collisionDetect(oVel: Vector2d, a: Rectangle, b: Rectangle): Boolean = {
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
  }*/

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    val screenVec = this.getPosition.toRenderPosition(pointOfView.getPosition)
    screen.batch.setColor(color)
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = getImage.runSyncUnsafe(5.seconds)

    val width = (image.getRegionWidth * METERS_PER_BLOCK) / PIXELS_PER_METER.toFloat
    val height = (image.getRegionHeight * METERS_PER_BLOCK) / PIXELS_PER_METER.toFloat

    screen.batch.draw(
      image,
      screenVec.x.toFloat,
      screenVec.y.toFloat,
      width / 2,
      height / 2,
      width,
      height,
      1, 1, body.getAngle)
  }

  def getImage: Task[TextureRegion]

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    /*val newDelta = delta * Options.BLOCK_SIZE
    if (gravityFactor != 0) addForce(Vector2d(0, 9.81f * gravityFactor))
    velocity = velocity + (accelleration.x * newDelta, accelleration.y * newDelta)
    accelleration = Vector2d.Zero
    if (velocity.`length²` > 0) {
      val stepLength = 10f / velocity.length.toFloat
      inAir = true
      var i = 0f
      while (i < newDelta) {
        var asl = stepLength
        if (i + asl > newDelta) asl -= (i + asl) - newDelta

        setPosition(getPosition + (velocity * asl))

        checkTerrainCollisions(world.localPlayer.getWorld)
        if (hasEntityCollision) checkEntityCollisions()

        i += stepLength
      }
    }
    velocity = velocity.withY(velocity.y * (1 - 0.025f * newDelta))
    velocity = velocity.withX(velocity.x * (1 - getCurrentFriction * newDelta))
    if (alignToVelocity && velocity.`length²` > 0.1f) setRotation(velocity.rotation + getVelocityOffsetAngle)
    if (isDynamicEntity && !isInRenderRange(tilePosition.world.localPlayer)) tilePosition.world.removeEntity(this)*/
  }

  def alignToVelocity: Boolean = false

  def getVelocityOffsetAngle: Double = 0

  def getUndergroundTile: Tile = {
    val pos = getPosition.toWorldPosition
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
    val sx = 2 + Gdx.graphics.getWidth / Options.METERS_PER_BLOCK / 2
    val sy = 2 + Gdx.graphics.getHeight / Options.METERS_PER_BLOCK / 2
    if (getPosition.x >= player.getPosition.x - sx * Options.METERS_PER_BLOCK && getPosition.x <= player.getPosition.x + sx * Options.METERS_PER_BLOCK && getPosition.y >= player.getPosition.y - sy * Options.METERS_PER_BLOCK && getPosition.y <= player.getPosition.y + sy * Options.METERS_PER_BLOCK) return true
    false
  }

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  final def tick(world: GameWorld): Unit = onTick(world)

  def onTick(world: GameWorld): Unit = {
  }
}