package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class TileSand() extends TileFalling {
  override def getImage: TextureRegion = Resources.TILES.getImage("sand")

  override def getFallSpeed(world: World) = 20

  override def getFallDelay(world: World) = 0.2f
}