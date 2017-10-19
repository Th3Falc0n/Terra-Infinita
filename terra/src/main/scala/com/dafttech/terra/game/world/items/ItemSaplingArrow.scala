package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.EntitySaplingArrow
import com.dafttech.terra.game.world.entities.models.EntityLiving

class ItemSaplingArrow extends ItemArrow {
  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    val a = new EntitySaplingArrow(causer.getPosition, causer.worldObj)
    a.setVelocity((Vector2.mousePos - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.2)
    true
  }
}