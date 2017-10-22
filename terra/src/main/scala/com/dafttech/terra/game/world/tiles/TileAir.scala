package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources

class TileAir extends Tile {
  override def getImage: TextureRegion = Resources.TILES.getImage("air")

  override def isAir: Boolean = true

  override def update(delta: Float): Unit = ()
}