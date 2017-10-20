package com.dafttech.terra.game.world.entities.models

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

abstract class EntityThrown(pos: Vector2, world: World, size: Vector2) extends Entity(pos, world, size) {
  override def update(world: World, delta: Float): Unit = super.update(world, delta)

  override def alignToVelocity: Boolean = true

  override def getInAirFriction: Double = 0.025
}