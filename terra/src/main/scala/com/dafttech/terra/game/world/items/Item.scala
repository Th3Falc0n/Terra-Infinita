package com.dafttech.terra.game.world.items

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInventory, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.EntityItem
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.persistence.GameObject
import monix.eval.Task
import scala.concurrent.duration._

abstract class Item extends GameObject with IDrawableInventory {
  val getImage: Task[TextureRegion]

  def use(causer: EntityLiving, position: Vector2): Boolean

  def getNextUseDelay(causer: EntityLiving, position: Vector2, leftClick: Boolean): Double = 0

  def getUsedItemNum(causer: EntityLiving, position: Vector2): Int = 1

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  def spawnAsEntity(tilePosition: TilePosition) = new EntityItem(tilePosition.pos.toEntityPos, Vector2(0.5f, 0.5f), this)(tilePosition.world)

  override def drawInventory(pos: Vector2, screen: AbstractScreen): Unit = { // TODO: Scheduler
    import monix.execution.Scheduler.Implicits.global
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
