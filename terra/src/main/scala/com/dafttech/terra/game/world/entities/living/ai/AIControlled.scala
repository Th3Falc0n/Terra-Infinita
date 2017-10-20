package com.dafttech.terra.game.world.entities.living.ai

import com.dafttech.terra.engine.input.InputHandler

class AIControlled extends ArtificialIntelligence {
  override def update(delta: Float): Unit = {
    if (InputHandler.isKeyDown("LEFT")) assignedEntity.walkLeft()
    if (InputHandler.isKeyDown("RIGHT")) assignedEntity.walkRight()
    if (InputHandler.isKeyDown("JUMP") && !assignedEntity.isInAir) assignedEntity.jump()
  }
}