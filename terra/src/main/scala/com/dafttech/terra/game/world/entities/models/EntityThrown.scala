package com.dafttech.terra.game.world.entities.models

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity

abstract class EntityThrown(pos: Vector2d)(implicit world: GameWorld) extends Entity(pos) {
  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = super.update(delta)
}