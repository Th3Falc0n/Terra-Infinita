package com.dafttech.terra.game.world.entities.particles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import monix.eval.Task

abstract class Particle(pos: Vector2d, life: Double, s: Vector2d)(implicit world: GameWorld) extends Entity(pos, s) {
  val lifetimeMax: Double = life
  var lifetime: Double = lifetimeMax

  private[particles] var fadeOut = false

  isDynamicEntity = true

  def setFadeOut(fo: Boolean): Unit = fadeOut = fo

  override def getImage: Task[TextureRegion] = null

  override def collidesWith(e: Entity): Boolean = false

  def isDead: Boolean = lifetime < 0

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    lifetime -= delta
    if (fadeOut) setAlpha((1 - (lifetime / lifetimeMax)).toFloat)
    if (lifetime < 0) this.getWorld.removeEntity(this)
  }

  override def getInAirFriction: Double = 0.025
}