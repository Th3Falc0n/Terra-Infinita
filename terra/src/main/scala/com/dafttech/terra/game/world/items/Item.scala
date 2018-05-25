package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInventory, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.EntityItem
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.persistence.GameObject

abstract class Item extends GameObject with IDrawableInventory {
  def getImage: TextureRegion

  def use(causer: EntityLiving, position: Vector2): Boolean

  def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0

  def getUsedItemNum(causer: EntityLiving, position: Vector2): Int = 1

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  def spawnAsEntity(position: Vector2, world: World) = new EntityItem(position, world, Vector2(0.5f, 0.5f), this)

  override def drawInventory(pos: Vector2, screen: AbstractScreen): Unit =
    screen.batch.draw(
      getImage,
      pos.x.toFloat + 4,
      (pos.y + 4 + 12 * (1 - (getImage.getRegionHeight / getImage.getRegionWidth))).toFloat,
      24,
      (24 * (getImage.getRegionHeight / getImage.getRegionWidth)).toFloat
    )

  def maxStackSize: Int = 99
}

abstract class ItemTile extends Item {
  override def update(delta: Float): Unit = ()
}
