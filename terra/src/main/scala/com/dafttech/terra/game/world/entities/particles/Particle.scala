package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.{ Body, FixtureDef, PolygonShape }
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import monix.eval.Task

abstract class Particle(pos: Vector2d, life: Double)(implicit world: GameWorld) extends Entity(pos) {
  val lifetimeMax: Double = life
  var lifetime: Double = lifetimeMax

  private[particles] var fadeOut = false

  def setFadeOut(fo: Boolean): Unit = fadeOut = fo

  override def getImage: Task[TextureRegion] = null

  def isDead: Boolean = lifetime < 0

  override def createFixture(body: Body) = {
    val fd = new FixtureDef()

    fd.shape = new PolygonShape()
    fd.shape.asInstanceOf[PolygonShape].setAsBox(0.01f, 0.01f)
    fd.isSensor = true

    body.createFixture(fd)
  }


  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    lifetime -= delta
    if (fadeOut) setAlpha((1 - (lifetime / lifetimeMax)).toFloat)
    if (lifetime < 0) this.getWorld.removeEntity(this)
  }
}