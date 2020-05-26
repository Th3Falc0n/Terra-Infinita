package com.dafttech.terra.game.world.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.{Body, BodyDef, PolygonShape}
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, TilePosition}
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.{GameWorld, TileInstance}
import com.dafttech.terra.resources.Options
import com.dafttech.terra.resources.Options.{METERS_PER_BLOCK, PIXELS_PER_METER}
import monix.eval.Task

import scala.concurrent.duration._

abstract class Entity(pos: Vector2d)(implicit val world: GameWorld) extends GameObject with IDrawableInWorld {

  val bodyDef = new BodyDef

  bodyDef.`type` = BodyDef.BodyType.DynamicBody
  bodyDef.position.set(pos.x.toFloat, pos.y.toFloat)

  modifyBodyDef(bodyDef)

  val body = world.physicsWorld.createBody(bodyDef)

  body.setUserData(this)
  createFixture(body)

  world.addEntity(this)

  def modifyBodyDef(definition: BodyDef) = { }

  def createFixture(body: Body) = {
    val shape = new PolygonShape()

    shape.setAsBox(METERS_PER_BLOCK / 2, METERS_PER_BLOCK / 2)

    body.createFixture(shape, 1)
    shape.dispose()
  }


  protected var color: Color = Color.WHITE

  private var scale: Float = 1f

  def getWorld: GameWorld = world

  def getPosition: Vector2d = {
    val pos = body.getPosition

    Vector2d(pos.x, pos.y)
  }

  def setScale(s: Float): Unit = scale = s

  def setAlpha(v: Float): Unit = color.a = v

  def isInAir: Boolean = false

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

  final def onRemove(): Unit = {
    world.physicsWorld.destroyBody(body)
  }

  def onTerrainCollision(tilePosition: TilePosition): Unit = ()

  def onEntityCollision(e: Entity): Unit = ()

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
      screenVec.x.toFloat - width/2f,
      screenVec.y.toFloat - height/2f,
      width/2f,
      height/2f,
      width,
      height,
      scale, scale, body.getAngle / (2f * math.Pi.toFloat) * 360f)
  }

  def getImage: Task[TextureRegion]

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = { }

  def getUndergroundTile: TileInstance = {
    val pos = getPosition.toWorldPosition
    world.getTile(Vector2i(pos.x, pos.y + 1))
  }

  def isInRenderRange(player: Player): Boolean = {
    val sx = 2 + Gdx.graphics.getWidth / Options.METERS_PER_BLOCK / 2
    val sy = 2 + Gdx.graphics.getHeight / Options.METERS_PER_BLOCK / 2
    if (getPosition.x >= player.getPosition.x - sx * Options.METERS_PER_BLOCK && getPosition.x <= player.getPosition.x + sx * Options.METERS_PER_BLOCK && getPosition.y >= player.getPosition.y - sy * Options.METERS_PER_BLOCK && getPosition.y <= player.getPosition.y + sy * Options.METERS_PER_BLOCK) return true
    false
  }

  final def isLightEmitter: Boolean = getEmittedLight != null

  def getEmittedLight: PointLight = null

  final def tick(world: GameWorld): Unit = onTick(world)

  def onTick(world: GameWorld): Unit = {

  }
}