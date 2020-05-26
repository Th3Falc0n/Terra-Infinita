package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.TileInstance
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.items.{Item, TileItem}
import monix.eval.Task

abstract class Tile extends GameObject {
  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

  def getFilterColor: Color = Color.WHITE

  def getImage: Task[TextureRegion]

  /**
    * Defines whether this tile graphically connects with another one.
    *
    * @param tile
    * @return
    */
  def connectsTo(tile: Tile): Boolean = true

  def isAir: Boolean = false

  def isReplacable: Boolean = isAir

  def isOpaque: Boolean = !isAir

  def isCollidableWith(entity: Entity): Boolean = !isAir

  def isFlammable: Boolean = false

  def isWaterproof: Boolean = isOpaque

  def isBreakable: Boolean = !isAir

  def getTemperature: Int = 0

  def getDroppedItems: Seq[Item] = List(TileItem(this))

  def spawnAsEntity(tilePosition: TilePosition): Seq[EntityItem] = {
    getDroppedItems.map { item =>
      new EntityItem(tilePosition.pos.toEntityPos + (0.5, 0.5), item)(tilePosition.world)
    }
  }

  def getWalkFriction: Float = 1

  def getWalkAcceleration: Float = 1


  def draw(instance: TileInstance, screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    for (subtile <- instance.subtiles) subtile.draw(screen, pointOfView)
  }

  final def tick(instance: TileInstance, delta: Float)(implicit tilePosition: TilePosition): Unit = {
    onTick(delta)

    instance.subtiles.foreach(_.tick(delta))
  }

  def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = ()

  def onTileSet(implicit tilePosition: TilePosition): Unit = ()

  def onNeighborChange(changed: TilePosition)(implicit tilePosition: TilePosition): Unit = ()

  def onTileDestroyed(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  def onTilePlaced(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  def update(instance: TileInstance, delta: Float)(implicit tilePosition: TilePosition): Unit = {
    instance.subtiles.foreach(_.update(delta))

    if (instance.breakingProgress > 0) instance.breakingProgress -= delta
  }

  private var _hardness: Float = 1

  def hardness: Float = _hardness

  def setHardness(hardness: Float): Tile = {
    _hardness = hardness
    this
  }
}