package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileAir extends Tile {
  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("air")

  override def isAir: Boolean = true
}