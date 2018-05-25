package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class SubtileDryGrass extends SubtileGrass {
  override def getImage: TextureRegion = Resources.TILES.getImage("mask_grass_dry")

  override def spread(tilePosition: TilePosition): Unit = ()
}