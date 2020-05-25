package com.dafttech.terra.game.world.entities.models

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.living.ai.ArtificialIntelligence

abstract class EntityLivingAI(pos: Vector2d, s: Vector2d, var assignedAI: ArtificialIntelligence)(implicit world: GameWorld) extends EntityLiving(pos, s) {
  assignedAI.setEntity(this)
  private[models] var deltaCount: Float = 0

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    deltaCount += delta
    if (deltaCount > 0.1f) {
      assignedAI.update(deltaCount)
      deltaCount = 0
    }
  }
}