package com.dafttech.terra.game.world.entities

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.TileTorch

class EntityTorchArrow(pos: Vector2, world: World) extends EntityFlamingArrow(pos, world) {
  override def placeBlockOnHit(x: Int, y: Int): Unit = {
    val spreadTile = worldObj.getTile(x, y)
    if (spreadTile.isReplacable) worldObj.setTile(x, y, new TileTorch, notify = true)
  }
}