package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.EntityGlowstick
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources

class ItemGlowstick extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    val a = new EntityGlowstick(causer.getPosition, causer.worldObj)
    a.setVelocity((Vector2.mousePos - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.08)
    true
  }

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0.4

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("glowstick")
}