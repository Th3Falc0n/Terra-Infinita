package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options

abstract class TileRenderer {
  protected var offset: Vector2 = Vector2.Null

  def draw(screen: AbstractScreen, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition)

  def setOffset(offset: Vector2): Unit = this.offset = offset

  def getOffset: Vector2 = offset
}