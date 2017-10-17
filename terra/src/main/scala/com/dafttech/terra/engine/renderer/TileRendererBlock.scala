package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE

object TileRendererBlock {
  var $Instance: TileRenderer = new TileRendererBlock
}

class TileRendererBlock extends TileRenderer {
  def draw(pos: Vector2, world: World, screen: AbstractScreen, tile: Tile, pointOfView: Entity, rendererArguments: AnyRef*) {
    val screenVec: Vector2 = tile.getPosition.toScreenPos(pointOfView)
    screen.batch.draw(tile.getImage, (screenVec.x + offset.x * BLOCK_SIZE).toFloat, (screenVec.y + offset.y * BLOCK_SIZE).toFloat, BLOCK_SIZE, BLOCK_SIZE)
  }
}