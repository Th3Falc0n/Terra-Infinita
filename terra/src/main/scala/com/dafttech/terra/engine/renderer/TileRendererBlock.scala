package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import scala.concurrent.duration._

object TileRendererBlock {
  var $Instance: TileRenderer = new TileRendererBlock
}

class TileRendererBlock extends TileRenderer {
  def draw(screen: AbstractScreen, tile: Tile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition): Unit = {
    val screenVec: Vector2 = tp.pos.toScreenPos(pointOfView)
    import monix.execution.Scheduler.Implicits.global
    val image = tile.getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, (screenVec.x + offset.x * BLOCK_SIZE).toFloat, (screenVec.y + offset.y * BLOCK_SIZE).toFloat, BLOCK_SIZE, BLOCK_SIZE)
  }
}