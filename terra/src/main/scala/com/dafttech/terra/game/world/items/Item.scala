package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInventory, TilePosition}
import com.dafttech.terra.game.world.entities.EntityItem
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.persistence.GameObject
import monix.eval.Task

import scala.concurrent.duration._

abstract class Item extends GameObject with IDrawableInventory {
  val getImage: Task[TextureRegion]

  def use(causer: EntityLiving, position: Vector2d): Boolean

  def getNextUseDelay(causer: EntityLiving, position: Vector2d, leftClick: Boolean): Double = 0

  def getUsedItemNum(causer: EntityLiving, position: Vector2d): Int = 1

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  def spawnAsEntity(tilePosition: TilePosition) = new EntityItem(tilePosition.pos.toEntityPos, this)(tilePosition.world)

  override def drawInventory(pos: Vector2d, screen: AbstractScreen): Unit = { // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(
      image,
      pos.x.toFloat + 4,
      (pos.y + 4 + 12 * (1 - (image.getRegionHeight / image.getRegionWidth))).toFloat,
      24,
      (24 * (image.getRegionHeight / image.getRegionWidth)).toFloat
    )
  }

  def maxStackSize: Int = 99
}

abstract class ItemTile extends Item {
  final override def update(delta: Float): Unit = ()
}
