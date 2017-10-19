package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.EntityDynamite
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources

class ItemDynamite extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    new EntityDynamite(causer.getPosition, causer.worldObj, 3, 4)
    true
  }

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("dynamite")

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 1
}