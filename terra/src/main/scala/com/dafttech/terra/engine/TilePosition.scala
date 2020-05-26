package com.dafttech.terra.engine

import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.tiles.TileAir
import com.dafttech.terra.game.world.{GameWorld, TileInstance}

case class TilePosition(world: GameWorld, pos: Vector2i) {
  def getTile: TileInstance = world.getTile(pos)

  def setTile(tile: TileInstance, notify: Boolean = true): Unit = world.setTile(pos, tile, notify = true)

  def moveTile(target: TilePosition): TilePosition = {
    val tile = getTile
    setTile(TileInstance(new TileAir))
    target.setTile(tile)
    target
  }

  def withWorld(world: GameWorld): TilePosition =
    TilePosition(world, pos)

  def withPosition(position: Vector2i): TilePosition =
    TilePosition(world, position)

  def mapPosition(f: Vector2i => Vector2i): TilePosition =
    withPosition(f(pos))
}
