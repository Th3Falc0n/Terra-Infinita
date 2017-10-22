package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererMultiblock}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class TileLeaf extends TileLog {
  override def getLog: TileLog = new TileLeaf()

  override def getImage: TextureRegion = Resources.TILES.getImage("leaf")

  override def isOpaque: Boolean = false

  override def isFlatTo(world: World, pos: Vector2i): Boolean = world.getTile(pos).isInstanceOf[TileLog]

  override def getFilterColor: Color = new Color(0.94f, 0.99f, 0.91f, 1)

  override def getRenderer: TileRenderer = new TileRendererMultiblock(new Vector2i(3, 3))
}