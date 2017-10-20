package com.dafttech.terra.game.world.entities.living.ai

import com.dafttech.terra.game.world.entities.models.EntityLivingAI

class ArtificialIntelligence {
  private[ai] var assignedEntity: EntityLivingAI = null

  def setEntity(entityLivingAI: EntityLivingAI): Unit = assignedEntity = entityLivingAI

  def update(delta: Float): Unit = {
  }
}