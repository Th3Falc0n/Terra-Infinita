package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class SubtileDryGrass extends SubtileGrass {
  override def getImage: Task[TextureRegion] = Resources.TILES.getImage("mask_grass_dry")

  override def spread(tilePosition: TilePosition): Unit = ()
}