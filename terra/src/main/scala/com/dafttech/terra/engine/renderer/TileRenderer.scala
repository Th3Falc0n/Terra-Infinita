package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.Entity

abstract class TileRenderer {
  protected var offset: Vector2d = Vector2d.Zero

  def draw(screen: AbstractScreen, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition)

  def setOffset(offset: Vector2d): Unit = this.offset = offset

  def getOffset: Vector2d = offset
}