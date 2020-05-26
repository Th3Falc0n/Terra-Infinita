package com.dafttech.terra.game.world.entities

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.tiles.TileSapling
import com.dafttech.terra.game.world.{GameWorld, TileInstance}

class EntitySaplingArrow(pos: Vector2d)(implicit world: GameWorld) extends EntityFlamingArrow(pos) {
  override def placeBlockOnHit(p: TilePosition): Unit = {
    val spreadTile = p.world.getTile(p.pos)
    if (spreadTile.tile.isReplacable) p.world.setTile(p.pos, TileInstance(new TileSapling()), notify = true)
  }
}