package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.input.MousePos
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.EntityTorchArrow
import com.dafttech.terra.game.world.entities.models.EntityLiving

class ItemTorchArrow extends ItemArrow {
  override def spawnEntity(causer: EntityLiving, position: Vector2d): Boolean = {
    val a = new EntityTorchArrow(causer.getPosition)(causer.world)
    a.setVelocity((MousePos.vector2d - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.2)
    true
  }
}
