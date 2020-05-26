package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.input.MousePos
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.EntitySaplingArrow
import com.dafttech.terra.game.world.entities.models.EntityLiving

class ItemSaplingArrow extends ItemArrow {
  override def spawnEntity(causer: EntityLiving, position: Vector2d): Boolean = {
    val a = new EntitySaplingArrow(causer.getPosition)(causer.world)

    val velocity = (MousePos.vector2d - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.2
    a.body.setLinearVelocity(velocity.x.toFloat, velocity.y.toFloat)
    true
  }
}