package com.dafttech.terra.game.world.entities

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.tiles.TileTorch

class EntityTorchArrow(pos: Vector2d)(implicit world: GameWorld) extends EntityFlamingArrow(pos) {
  override def placeBlockOnHit(tilePosition: TilePosition): Unit = {
    val spreadTile = tilePosition.getTile
    if (spreadTile.isReplacable) world.setTile(tilePosition.pos, new TileTorch, notify = true)
  }
}