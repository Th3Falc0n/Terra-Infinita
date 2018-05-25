package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.EntityDiggerBeam
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources

class ItemDigStaff extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    val a = new EntityDiggerBeam(causer.getPosition)(causer.world)
    a.setVelocity((Vector2.mousePos - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.2)
    false
  }

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 1

  override def getImage: TextureRegion = Resources.ITEMS.getImage("digStaff")
}