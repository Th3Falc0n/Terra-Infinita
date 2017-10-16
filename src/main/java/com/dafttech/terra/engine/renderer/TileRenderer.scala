package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile

abstract class TileRenderer {
  protected var offset: Vector2 = new Vector2

  def draw2(pos: Vector2, world: World, screen: AbstractScreen, render: Tile, pointOfView: Entity) =
    draw(pos, world, screen, render, pointOfView)

  def draw(pos: Vector2, world: World, screen: AbstractScreen, render: Tile, pointOfView: Entity, rendererArguments: AnyRef*)

  def setOffset(offset: Vector2) {
    this.offset = offset
  }

  def getOffset: Vector2 = {
    return offset
  }
}