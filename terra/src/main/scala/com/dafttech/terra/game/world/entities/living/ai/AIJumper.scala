package com.dafttech.terra.game.world.entities.living.ai

class AIJumper extends ArtificialIntelligence {
  override def update(delta: Float): Unit = if (!assignedEntity.isInAir) assignedEntity.jump()
}