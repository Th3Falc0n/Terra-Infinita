package com.dafttech.terra.engine

import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}

case class TilePosition(world: World, pos: Vector2i) {
  def getTile: Tile = world.getTile(pos)

  def setTile(tile: Tile, notify: Boolean = true): Unit = world.setTile(pos, tile, notify = true)

  def moveTile(target: Vector2i): TilePosition = {
    val tile = getTile
    val targetPos = TilePosition(world, target)
    setTile(new TileAir)
    targetPos.setTile(tile)
    targetPos
  }
}
