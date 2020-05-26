package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, TilePosition}
import com.dafttech.terra.game.world.entities.{Entity, EntityItem}
import com.dafttech.terra.game.world.items.persistence.GameObject
import com.dafttech.terra.game.world.items.{Item, TileItem}
import com.dafttech.terra.game.world.subtiles.Subtile
import monix.eval.Task

abstract class Tile extends GameObject with IDrawableInWorld {
  private var subtiles: Seq[Subtile] = List.empty

  private var breakingProgress: Float = 0
  private var hardness: Float = 1

  var receivesSunlight: Boolean = false
  var sunlightFilter: TilePosition = _

  def isLightEmitter: Boolean = false

  def getEmittedLight: PointLight = null

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

  def addSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles ++ subtile.toList
    this
  }

  def removeSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles.diff(subtile)
    this
  }

  def removeAndUnlinkSubtile(subtile: Subtile*): Tile = {
    subtiles = subtiles.diff(subtile)
    this
  }

  def getSubtiles: Seq[Subtile] = subtiles

  def hasSubtile[A <: Subtile](subtileClass: Class[A], inherited: Boolean): Boolean =
    getSubtile(subtileClass, inherited) != null

  def getSubtile[A <: Subtile](subtileClass: Class[A], inherited: Boolean): A =
    subtiles.find(subtile =>
      (inherited && subtileClass.isAssignableFrom(subtile.getClass)) ||
        (!inherited && subtileClass == subtile.getClass)
    ).map(_.asInstanceOf[A]).getOrElse(null.asInstanceOf[A])

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    for (subtile <- subtiles) subtile.draw(screen, pointOfView)
  }

  final def tick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    onTick(delta)

    subtiles.foreach(_.tick(delta))
  }

  def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = ()

  def onTileSet(implicit tilePosition: TilePosition): Unit = ()

  def onNeighborChange(changed: TilePosition)(implicit tilePosition: TilePosition): Unit = ()

  def onTileDestroyed(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  def onTilePlaced(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    subtiles.foreach(_.update(delta))

    if (breakingProgress > 0) breakingProgress -= delta
  }

  def setHardness(hardness: Float): Tile = {
    this.hardness = hardness
    this
  }

  final def setReceivesSunlight(is: Boolean)(implicit tilePosition: TilePosition): Unit = {
    receivesSunlight = is
    if (!is) this.sunlightFilter = null
    if (!isOpaque)
      tilePosition.world.getNextTileBelow(tilePosition.pos).filter(_.getTile != this).foreach { b =>
        b.getTile.setReceivesSunlight(is)
        b.getTile.sunlightFilter = if (is) tilePosition else null
      }
  }

  def getFilterColor: Color = Color.WHITE

  final def getSunlightColor: Color =
    if (sunlightFilter == null) Color.WHITE
    else {
      var sunlightColor = sunlightFilter.getTile.getSunlightColor.cpy.mul(sunlightFilter.getTile.getFilterColor)

      for (subtile <- sunlightFilter.getTile.subtiles)
        if (subtile.providesSunlightFilter) sunlightColor = sunlightColor.cpy.mul(subtile.getFilterColor)

      sunlightColor
    }

  def damage(damage: Float, causer: Entity)(implicit tilePosition: TilePosition): Unit = {
    breakingProgress += damage
    if (breakingProgress > hardness) tilePosition.world.destroyTile(tilePosition.pos, causer)
  }
}