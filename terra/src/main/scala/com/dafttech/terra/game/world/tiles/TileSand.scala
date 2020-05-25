package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileSand() extends TileFalling {
  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("sand")

  override def getFallSpeed(world: GameWorld) = 20

  override def getFallDelay(world: GameWorld) = 0.2f
}