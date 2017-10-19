package com.dafttech.terra.game.world.items

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.models.EntityLiving

abstract class ItemEntitySpawner extends Item {
  override def update(delta: Float): Unit = ()

  override def use(causer: EntityLiving, position: Vector2): Boolean = spawnEntity(causer, position)

  def spawnEntity(causer: EntityLiving, position: Vector2): Boolean
}