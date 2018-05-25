package com.dafttech.terra.game.world.entities

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.TileTorch

class EntityTorchArrow(pos: Vector2)(implicit world: World) extends EntityFlamingArrow(pos) {
  override def placeBlockOnHit(tilePosition: TilePosition): Unit = {
    val spreadTile = tilePosition.tile
    if (spreadTile.isReplacable) world.setTile(tilePosition.pos, new TileTorch, notify = true)
  }
}