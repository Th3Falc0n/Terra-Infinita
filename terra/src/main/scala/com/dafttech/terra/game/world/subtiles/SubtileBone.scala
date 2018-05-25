package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources

class SubtileBone extends Subtile {
  override def getImage: TextureRegion = Resources.TILES.getImage("bone")
}