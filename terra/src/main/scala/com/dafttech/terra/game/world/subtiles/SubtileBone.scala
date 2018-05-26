package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class SubtileBone extends Subtile {
  override def getImage: Task[TextureRegion] = Resources.TILES.getImage("bone")
}