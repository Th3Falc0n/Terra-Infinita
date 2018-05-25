package com.dafttech.terra.game.world.entities

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.TileSapling

class EntitySaplingArrow(pos: Vector2)(implicit world: World) extends EntityFlamingArrow(pos) {
  override def placeBlockOnHit(p: TilePosition): Unit = {
    val spreadTile = p.world.getTile(p.pos)
    if (spreadTile.isReplacable) p.world.setTile(p.pos, new TileSapling, notify = true)
  }
}