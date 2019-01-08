package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.subtiles.SubtileGlowGoo
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class ItemGlowGooBucket extends Item {
  override def update(delta: Float): Unit = {
  }

  override val getImage: Task[TextureRegion] = Resources.TILES.getImage("glowgoo", 0)

  override def use(causer: EntityLiving, position: Vector2d): Boolean = {
    val tile = causer.world.getTile(position.toWorldPosition)
    tile.addSubtile(new SubtileGlowGoo)
    true
  }

  override def getUsedItemNum(causer: EntityLiving, position: Vector2d): Int = 0

  override def getNextUseDelay(causer: EntityLiving, position: Vector2d, leftClick: Boolean): Double = 0.08
}