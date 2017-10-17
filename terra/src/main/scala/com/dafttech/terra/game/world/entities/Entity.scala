package com.dafttech.terra.game.world.entities


/*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.IDrawableInWorld
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.game.world.Chunk
import com.dafttech.terra.game.world.Facing
import com.dafttech.terra.game.world.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.items.persistence.Persistent
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options

abstract class Entity2 protected() extends GameObject with IDrawableInWorld {
  private[entities] var chunk: Chunk = null
  @Persistent protected var position = new Vector2()
  @Persistent protected var velocity = new Vector2()
  protected var accelleration = new Vector2()
  @Persistent protected var size = new Vector2()
  @Persistent protected var rotation = .0
  var worldObj: World = null
  protected var color: Color = Color.WHITE
  private[entities] var gravityFactor = 1f
  protected var inAir = false
  protected var inWorld = true
  @Persistent protected var isDynamicEntity = false

  def this(pos: Vector2, world: World, s: Vector2) {
    this()
    worldObj = world
    addToWorld(world, pos)
    setPosition(pos)
    size = s
  }

  def setMidPos(pos: Vector2): Unit = setPosition(pos.addNew(-size.x * Options.BLOCK_SIZE / 2f, -size.y * Options.BLOCK_SIZE / 2f))

  def getMidPos: Vector2 = getPosition.add(size.x * Options.BLOCK_SIZE / 2f, size.y * Options.BLOCK_SIZE / 2f)

  def setColor(clr: Color): Unit = color = clr

  def setAlpha(v: Float): Unit = color.a = v

  def setRotation(angle: Float): Unit = rotation = angle

  def getPosition: Vector2 = position.clone

  def setPosition(pos: Vector2): Entity = {
    val newChunk = worldObj.getOrCreateChunk(pos)
    if (newChunk != null && (chunk ne newChunk)) {
      addToWorld(newChunk, pos)
      onRechunk(newChunk, pos)
    }
    position.set(pos)
    this
  }

  def onRechunk(newChunk: Chunk, pos: Vector2): Unit = {
  }

  def remove: Boolean = {
    if (chunk != null) {
      inWorld = false
      return chunk.removeEntity(this)
    }
    false
  }

  private def addToWorld(world: World, pos: Vector2) = addToWorld(world.getOrCreateChunk(pos), pos)

  private def addToWorld(chunk: Chunk, pos: Vector2) = {
    remove
    inWorld = true
    if (chunk.addEntity(this)) this.chunk = chunk
  }

  def getWorld: World = worldObj

  def isInAir: Boolean = inAir

  def setHasGravity(v: Boolean): Unit = if (!v) gravityFactor = 0

  def setGravityFactor(f: Float): Unit = gravityFactor = f

  def setSize(x: Float, y: Float): Unit = size = new Vector2(x, y)

  @SuppressWarnings(Array("unused")) private def drawRect(rect: Rectangle, rend: ShapeRenderer, color: Color) = {
    rend.begin(ShapeType.Filled)
    rend.setColor(color.r, color.g, color.b, 1)
    var v2 = new Vector2(rect.x, rect.y)
    v2 = v2.toRenderPosition(getPosition)
    rend.rect(v2.x, v2.y, rect.width, rect.height)
    rend.flush()
    rend.end()
  }

  def collidesWith(e: Entity) = true

  def hasEntityCollision = false

  def checkEntityCollisions(): Unit = {
    val oVel = velocity.clone
    for (entity <- chunk.getLocalEntities) {
      if ((entity eq this) || !(entity.collidesWith(this) && this.collidesWith(entity)) || velocity.len2 < entity.velocity.len2) continue //todo: continue is not supported
      val otherRect = new Rectangle(entity.getPosition.x, entity.getPosition.y, entity.getSize.x * Options.BLOCK_SIZE, entity.getSize.y * Options.BLOCK_SIZE)
      val playerRect = new Rectangle(getPosition.x, getPosition.y, Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y)
      if (collisionDetect(oVel, playerRect, otherRect)) onEntityCollision(entity)
    }
  }

  def checkTerrainCollisions(world: World): Unit = {
    val mid = getPosition.toWorldPosition
    var playerRect = null
    var tileRect = null
    val oVel = velocity.clone
    var x = mid.getX - 1
    while ( {
      x <= mid.getX + 2 + size.x
    }) {
      var y = mid.getY - 1
      while ( {
        y <= mid.getY + 2 + size.y
      }) {
        if (world.getTile(x, y) != null && world.getTile(x, y).isCollidableWith(this)) {
          tileRect = new Rectangle(x * Options.BLOCK_SIZE, y * Options.BLOCK_SIZE, Options.BLOCK_SIZE, Options.BLOCK_SIZE)
          playerRect = new Rectangle(getPosition.x, getPosition.y, Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y)
          if (collisionDetect(oVel, playerRect, tileRect)) onTerrainCollision(world.getTile(x, y))
        }
        {
          y += 1; y - 1
        }
      }
      {
        x += 1; x - 1
      }
    }
  }

  def onTerrainCollision(t: Tile): Unit = {
  }

  def onEntityCollision(e: Entity): Unit = {
  }

  def collisionDetect(oVel: Vector2, a: Rectangle, b: Rectangle): Boolean = {
    if (a.overlaps(b)) {
      var fVertical = null
      var fHorizontal = null
      var distVertical = 0
      var distHorizontal = 0
      var posVertical = 0
      var posHorizontal = 0
      var hcv = false
      var hch = false
      if (oVel.y > 0) {
        fVertical = Facing.BOTTOM$.MODULE$
        distVertical = (a.y + a.height) - b.y
        posVertical = b.y - 0.01f - a.height
        hcv = true
      }
      else if (oVel.y < 0) {
        fVertical = Facing.TOP$.MODULE$
        distVertical = (b.y + b.height) - a.y
        posVertical = (b.y + b.height) + 0.01f
        hcv = true
      }
      if (oVel.x > 0) {
        fHorizontal = Facing.RIGHT$.MODULE$
        distHorizontal = (a.x + a.width) - b.x
        posHorizontal = b.x - 0.01f - a.width
        hch = true
      }
      else if (oVel.x < 0) {
        fHorizontal = Facing.LEFT$.MODULE$
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

  def collisionResponse(facing: Facing, `val`: Float): Unit = {
    if (facing.isVertical) {
      velocity.y_$eq(0)
      setPosition(getPosition.setY(`val`))
    }
    else {
      velocity.x_$eq(0)
      setPosition(getPosition.setX(`val`))
    }
    if (facing eq Facing.BOTTOM$.MODULE$) inAir = false
  }

  override def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit = {
    val screenVec = this.getPosition.toRenderPosition(pointOfView.getPosition)
    screen.batch.setColor(color)
    screen.batch.draw(this.getImage, screenVec.x, screenVec.y, Options.BLOCK_SIZE * size.x / 2, Options.BLOCK_SIZE * size.y / 2, Options.BLOCK_SIZE * size.x, Options.BLOCK_SIZE * size.y, 1, 1, rotation)
  }

  def getImage: TextureRegion

  def setVelocity(velocity: Vector2): Entity = {
    this.velocity = velocity
    this
  }

  def addForce(f: Vector2): Entity = {
    this.accelleration.add(f)
    this
  }

  def addVelocity(v: Vector2): Entity = {
    this.velocity.add(v)
    this
  }

  override def update(world: World, delta: Float): Unit = {
    delta *= Options.BLOCK_SIZE
    if (gravityFactor != 0) addForce(new Vector2(0, 9.81f * gravityFactor))
    velocity.addX(accelleration.x * delta)
    velocity.addY(accelleration.y * delta)
    accelleration.setNull
    if (velocity.len2 > 0) {
      val stepLength = 10f / velocity.len
      inAir = true
      var i = 0
      while ( {
        i < delta
      }) {
        var asl = stepLength
        if (i + asl > delta) asl -= (i + asl) - delta
        setPosition(getPosition.add(velocity.mulNew(asl)))
        if (!inWorld) return
        checkTerrainCollisions(worldObj.localPlayer.getWorld)
        if (this.hasEntityCollision) checkEntityCollisions()
        i += stepLength
      }
    }
    velocity.mulY(1 - 0.025f * delta)
    velocity.mulX(1 - getCurrentFriction * delta)
    if (alignToVelocity && velocity.len2 > 0.1f) setRotation(velocity.angle + getVelocityOffsetAngle)
    if (isDynamicEntity && !isInRenderRange(world.localPlayer)) world.removeEntity(this)
  }

  def alignToVelocity = false

  def getVelocityOffsetAngle = 0

  def getUndergroundTile: Tile = {
    val pos = position.addNew(size.x * Options.BLOCK_SIZE, size.y * Options.BLOCK_SIZE).toWorldPosition
    worldObj.getTile(pos.x, pos.y + 1)
  }

  def getCurrentFriction: Float = {
    if (getUndergroundTile != null && !inAir) return getUndergroundTile.getWalkFriction
    getInAirFriction
  }

  def getCurrentAcceleration: Float = {
    if (getUndergroundTile != null && !inAir) return getUndergroundTile.getWalkAcceleration
    1
  }

  def getInAirFriction = 1

  def isInRenderRange(player: Player): Boolean = {
    val sx = 2 + Gdx.graphics.getWidth / Options.BLOCK_SIZE / 2
    val sy = 2 + Gdx.graphics.getHeight / Options.BLOCK_SIZE / 2
    if (position.x >= player.getPosition.x - sx * Options.BLOCK_SIZE && position.x <= player.getPosition.x + sx * Options.BLOCK_SIZE && position.y >= player.getPosition.y - sy * Options.BLOCK_SIZE && position.y <= player.getPosition.y + sy * Options.BLOCK_SIZE) return true
    false
  }

  def isLightEmitter = false

  def getEmittedLight: PointLight = null

  def getSize: Vector2 = size

  final def tick(world: World): Unit = onTick(world)

  def onTick(world: World): Unit = {
  }
}*/