package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileStone() extends Tile {
  override val getImage: Task[TextureRegion] = Resources.TILES.getImage("stone")
}