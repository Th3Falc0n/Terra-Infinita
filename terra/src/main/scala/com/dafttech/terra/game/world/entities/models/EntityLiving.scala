package com.dafttech.terra.game.world.entities.models

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import monix.eval.Task

abstract class EntityLiving(pos: Vector2d, s: Vector2d)(implicit world: GameWorld) extends Entity(pos) {
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

  def jump(): Unit = body.applyLinearImpulse(0, -1, 0, 0, true)

  def walkLeft(): Unit = body.applyForceToCenter(-1, 0f, true)

  def walkRight(): Unit =  body.applyForceToCenter(1, 0f, true)

  protected def setMaxHealth(maxHealth: Float): EntityLiving = {
    this.maxHealth = maxHealth
    this
  }

  def getHealth: Float = health

  def getMaxHealth: Float = maxHealth
}