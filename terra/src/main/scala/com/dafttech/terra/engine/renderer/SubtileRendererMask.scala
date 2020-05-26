package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.game.world.tiles.TileFalling
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK

import scala.concurrent.duration._

object SubtileRendererMask { // TODO: SINGLETON?
  var $Instance: SubtileRenderer = new SubtileRendererMask
}

class SubtileRendererMask extends SubtileRenderer {
  def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition) {
    val screenVec: Vector2d = tp.pos.toScreenPos(pointOfView)
    val rotation: Float = if (rendererArguments.nonEmpty) rendererArguments(0).asInstanceOf[Float] else 0
    var offX: Float = 0
    var offY: Float = 0

    if (!render.isTileIndependent && tp.getTile != null && tp.getTile.tile.isInstanceOf[TileFalling]) {
      val offset: Vector2d = tp.getTile.asInstanceOf[TileFalling].renderOffset
      if (offset != null) {
        offX = offset.x.toFloat * METERS_PER_BLOCK
        offY = offset.y.toFloat * METERS_PER_BLOCK
      }
    }

    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = render.getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, screenVec.x.toFloat + offX, screenVec.y.toFloat + offY, 1, 1, METERS_PER_BLOCK, METERS_PER_BLOCK, 1, 1, rotation)
  }
}