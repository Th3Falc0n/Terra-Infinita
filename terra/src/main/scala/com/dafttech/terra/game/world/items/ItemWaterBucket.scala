package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.subtiles.SubtileWater
import com.dafttech.terra.resources.Resources

class ItemWaterBucket extends Item {
  override def update(delta: Float): Unit = {
  }

  override def getImage: TextureRegion = Resources.TILES.getImage("water")

  override def use(causer: EntityLiving, position: Vector2): Boolean = {
    val tile = causer.worldObj.getTile(position.toWorldPosition)
    tile.addSubtile(new SubtileWater)
    true
  }

  override def getUsedItemNum(causer: EntityLiving, position: Vector2): Int = 0

  override def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0.08
}