package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.Tile

class SunMap {
  private var heights = Map.empty[Int, Int]
  private var topTiles = Map.empty[Int, Tile]

  private def unsetSunlightForX(w: World, t: Tile): Unit =
    topTiles.get(t.getPosition.x).foreach(_.setReceivesSunlight(w, false))

  private def setSunlightForX(w: World, t: Tile): Unit = {
    topTiles = topTiles + (t.getPosition.x -> t)
    t.setReceivesSunlight(w, true)
  }

  private def getHeightForX(x: Int): Int = heights.getOrElse(x, Int.MaxValue)

  private def setHeightForX(x: Int, h: Int): Unit = heights = heights + (x -> h)

  private def getReceivingTile(x: Int): Tile = topTiles.getOrElse(x, null)

  private def isReceivingTile(t: Tile): Boolean = getReceivingTile(t.getPosition.x) == t

  def postTilePlace(w: World, t: Tile): Unit =
    if (!t.isAir)
      if (getHeightForX(t.getPosition.x) > t.getPosition.y) {
        setHeightForX(t.getPosition.x, t.getPosition.y)
        unsetSunlightForX(w, t)
        setSunlightForX(w, t)
      } else if (getReceivingTile(t.getPosition.x) != null) {
        unsetSunlightForX(w, getReceivingTile(t.getPosition.x))
        setSunlightForX(w, getReceivingTile(t.getPosition.x))
      }

  def postTileRemove(w: World, t: Tile): Unit =
    if (isReceivingTile(t)) {
      unsetSunlightForX(w, t)
      w.getNextTileBelow(t.getPosition).foreach {b =>
        setSunlightForX(w, b)
        setHeightForX(b.getPosition.x, b.getPosition.y)
      }
    } else if (getReceivingTile(t.getPosition.x) != null) {
      unsetSunlightForX(w, getReceivingTile(t.getPosition.x))
      setSunlightForX(w, getReceivingTile(t.getPosition.x))
    }
}