package com.dafttech.terra.engine

import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.{Tile, TileAir}

case class TilePosition(world: World, pos: Vector2i) {
  def getTile: Tile = world.getTile(pos)

  def setTile(tile: Tile, notify: Boolean = true): Unit = world.setTile(pos, tile, notify = true)

  def moveTile(target: TilePosition): TilePosition = {
    val tile = getTile
    setTile(new TileAir)
    target.setTile(tile)
    target
  }

  def withWorld(world: World): TilePosition =
    TilePosition(world, pos)

  def withPosition(position: Vector2i): TilePosition =
    TilePosition(world, position)

  def mapPosition(f: Vector2i => Vector2i): TilePosition =
    withPosition(f(pos))
}
