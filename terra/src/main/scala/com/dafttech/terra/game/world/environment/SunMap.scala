package com.dafttech.terra.game.world.environment

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.Tile

class SunMap {
  private var heights = Map.empty[Int, Int]
  private var topTiles = Map.empty[Int, TilePosition]

  private def unsetSunlightForX(t: TilePosition): Unit =
    topTiles.get(t.pos.x).foreach(_.tile.setReceivesSunlight(is = false)(t))

  private def setSunlightForX(t: TilePosition): Unit = {
    topTiles = topTiles + (t.pos.x -> t)
    t.tile.setReceivesSunlight(is = true)(t)
  }

  private def getHeightForX(x: Int): Int = heights.getOrElse(x, Int.MaxValue)

  private def setHeightForX(x: Int, h: Int): Unit = heights = heights + (x -> h)

  private def getReceivingTile(x: Int): TilePosition = topTiles.getOrElse(x, null)

  private def isReceivingTile(t: TilePosition): Boolean = getReceivingTile(t.pos.x) == t

  def postTilePlace(t: TilePosition): Unit =
    if (!t.tile.isAir)
      if (getHeightForX(t.pos.x) > t.pos.y) {
        setHeightForX(t.pos.x, t.pos.y)
        unsetSunlightForX(t)
        setSunlightForX(t)
      } else if (getReceivingTile(t.pos.x) != null) {
        unsetSunlightForX(getReceivingTile(t.pos.x))
        setSunlightForX(getReceivingTile(t.pos.x))
      }

  def postTileRemove(t: TilePosition): Unit =
    if (isReceivingTile(t)) {
      unsetSunlightForX(t)
      t.world.getNextTileBelow(t.pos).foreach { b =>
        setSunlightForX(b)
        setHeightForX(b.pos.x, b.pos.y)
      }
    } else if (getReceivingTile(t.pos.x) != null) {
      unsetSunlightForX(getReceivingTile(t.pos.x))
      setSunlightForX(getReceivingTile(t.pos.x))
    }
}