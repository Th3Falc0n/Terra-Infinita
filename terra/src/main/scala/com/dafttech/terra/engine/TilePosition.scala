package com.dafttech.terra.engine

import com.dafttech.terra.game.world.World

case class TilePosition(world: World, pos: Vector2i) {
  def tile = world.getTile(pos)
}
