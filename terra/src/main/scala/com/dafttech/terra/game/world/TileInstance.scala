package com.dafttech.terra.game.world

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, TilePosition}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.game.world.tiles.Tile

case class TileInstance[Props](tile: Tile[Props],
                               props: Props,
                               subtiles: Seq[Subtile] = List.empty) extends IDrawableInWorld {
  var breakingProgress: Float = 0
  var receivesSunlight: Boolean = false
  var sunlightFilter: TilePosition = _

  def withSubtiles(subtiles: Seq[Subtile]): TileInstance[Props] = {
    val newTileInstance = copy(subtiles = subtiles)
    newTileInstance.breakingProgress = breakingProgress
    newTileInstance
  }

  def addSubtile(subtiles: Subtile*): TileInstance[Props] =
    withSubtiles(this.subtiles ++ subtiles)

  def removeSubtile(subtile: Subtile*): TileInstance[Props] =
    withSubtiles(subtiles.diff(subtile))

  def hasSubtile[A <: Subtile](subtileClass: Class[A], inherited: Boolean): Boolean =
    getSubtile(subtileClass, inherited) != null

  def getSubtile[A <: Subtile](subtileClass: Class[A], inherited: Boolean): A =
    subtiles.find(subtile =>
      (inherited && subtileClass.isAssignableFrom(subtile.getClass)) ||
        (!inherited && subtileClass == subtile.getClass)
    ).map(_.asInstanceOf[A]).getOrElse(null.asInstanceOf[A])

  final def setReceivesSunlight(is: Boolean)(implicit tilePosition: TilePosition): Unit = {
    receivesSunlight = is
    if (!is) this.sunlightFilter = null
    if (!tile.isOpaque)
      tilePosition.world.getNextTileBelow(tilePosition.pos).filter(_.getTile != this).foreach { b =>
        b.getTile.setReceivesSunlight(is)
        b.getTile.sunlightFilter = if (is) tilePosition else null
      }
  }

  final def getSunlightColor: Color =
    if (sunlightFilter == null) Color.WHITE
    else {
      var sunlightColor = sunlightFilter.getTile.getSunlightColor.cpy.mul(sunlightFilter.getTile.tile.getFilterColor)

      for (subtile <- sunlightFilter.getTile.subtiles)
        if (subtile.providesSunlightFilter) sunlightColor = sunlightColor.cpy.mul(subtile.getFilterColor)

      sunlightColor
    }

  def damage(damage: Float, causer: Entity)(implicit tilePosition: TilePosition): Unit = {
    breakingProgress += damage
    if (breakingProgress > tile.hardness) tilePosition.world.destroyTile(tilePosition.pos, causer)
  }

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit =
    tile.update(this, delta)

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit =
    tile.draw(this, screen, pointOfView)(tilePosition)
}
