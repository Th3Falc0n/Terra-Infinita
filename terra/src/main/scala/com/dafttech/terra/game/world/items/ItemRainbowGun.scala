package com.dafttech.terra.game.world.items

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.EntityRainbow
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.resources.Resources

class ItemRainbowGun extends ItemEntitySpawner {
  override def spawnEntity(causer: EntityLiving, position: Vector2): Boolean = {
    val a = new EntityRainbow(causer.getPosition)(causer.world)
    a.setVelocity((Vector2.mousePos - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)) * 0.08)
    val time = (System.currentTimeMillis() % 1000) / 100f
    val offset120 = Math.PI * 2 / 3
    val r = Math.sin(time + offset120 * 0).toFloat / 2f + 0.5f
    val g = Math.sin(time + offset120 * 1).toFloat / 2f + 0.5f
    val b = Math.sin(time + offset120 * 2).toFloat / 2f + 0.5f
    a.getEmittedLight.setColor(new Color(r, g, b, 0.5f))
    true
  }

  override def getUsedItemNum(causer: EntityLiving, position: Vector2): Int = 0

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0

  override def getImage: TextureRegion = Resources.ITEMS.getImage("rainbowgun")
}