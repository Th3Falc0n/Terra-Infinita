package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.living.Player
import com.dafttech.terra.resources.Resources

class TileFence extends Tile {
  override def getImage: TextureRegion = Resources.TILES.getImage("fence")

  override def isCollidableWith(entity: Entity): Boolean = !entity.isInstanceOf[Player]

  override def isOpaque: Boolean = false
}