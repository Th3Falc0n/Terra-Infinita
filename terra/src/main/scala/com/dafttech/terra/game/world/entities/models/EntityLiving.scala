package com.dafttech.terra.game.world.entities.models

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import monix.eval.Task

abstract class EntityLiving(pos: Vector2d, s: Vector2d)(implicit world: GameWorld) extends Entity(pos, s) {
  private var maxHealth: Float = 10
  private var health: Float = maxHealth

  override def getImage: Task[TextureRegion] = null

  def damage(damage: Float): EntityLiving = heal(-damage)

  def heal(health: Float): EntityLiving = {
    this.health += health
    if (this.health < 0) this.health = 0
    if (this.health > maxHealth) this.health = maxHealth
    this
  }

  def jump(): Unit = addVelocity(Vector2d(0, -30))

  def walkLeft(): Unit = addForce(Vector2d(-10f * getCurrentAcceleration, 0))

  def walkRight(): Unit = addForce(Vector2d(10f * getCurrentAcceleration, 0))

  protected def setMaxHealth(maxHealth: Float): EntityLiving = {
    this.maxHealth = maxHealth
    this
  }

  def getHealth: Float = health

  def getMaxHealth: Float = maxHealth
}