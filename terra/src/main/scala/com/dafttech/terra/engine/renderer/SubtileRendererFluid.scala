package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.Facing
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.{Subtile, SubtileFluid}
import com.dafttech.terra.game.world.tiles.TileFalling
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK

import scala.concurrent.duration._

object SubtileRendererFluid { // TODO: SINGLETON?
  var $Instance: SubtileRenderer = new SubtileRendererFluid
}

class SubtileRendererFluid extends SubtileRendererMask {
  override def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition) {
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

    val renderFluid = render.asInstanceOf[SubtileFluid]
    var height: Float = renderFluid.pressure / renderFluid.maxPressure * METERS_PER_BLOCK
    if (height < METERS_PER_BLOCK && renderFluid.isFluid(tp, Facing.Top)) {
      val above: SubtileFluid = renderFluid.getFluid(tp, Facing.Top)._1
      height += above.pressure / above.maxPressure * METERS_PER_BLOCK
    }
    if (height < METERS_PER_BLOCK && renderFluid.isFluid(tp, Facing.Bottom)) {
      val below: SubtileFluid = renderFluid.getFluid(tp, Facing.Bottom)._1
      if (below.pressure < below.maxPressure) height = 0
    }
    if (height > METERS_PER_BLOCK) height = METERS_PER_BLOCK
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = render.getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, screenVec.x.toFloat + offX, screenVec.y.toFloat + offY + (METERS_PER_BLOCK - height), 1, 1, METERS_PER_BLOCK, height, 1, 1, rotation)
  }
}