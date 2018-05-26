package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.Facing
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.{Subtile, SubtileFluid}
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import scala.concurrent.duration._

object SubtileRendererFluid { // TODO: SINGLETON?
  var $Instance: SubtileRenderer = new SubtileRendererFluid
}

class SubtileRendererFluid extends SubtileRendererMask {
  override def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition) {
    val screenVec: Vector2 = tp.pos.toScreenPos(pointOfView)
    val rotation: Float = if (rendererArguments.nonEmpty) rendererArguments(0).asInstanceOf[Float] else 0
    var offX: Float = 0
    var offY: Float = 0
    if (!render.isTileIndependent && tp.getTile != null) {
      val offset: Vector2 = tp.getTile.getRenderer.getOffset
      if (offset != null) {
        offX = offset.x.toFloat * BLOCK_SIZE
        offY = offset.y.toFloat * BLOCK_SIZE
      }
    }
    val renderFluid = render.asInstanceOf[SubtileFluid]
    var height: Float = renderFluid.pressure / renderFluid.maxPressure * BLOCK_SIZE
    if (height < BLOCK_SIZE && renderFluid.isFluid(tp, Facing.Top)) {
      val above: SubtileFluid = renderFluid.getFluid(tp, Facing.Top)._1
      height += above.pressure / above.maxPressure * BLOCK_SIZE
    }
    if (height < BLOCK_SIZE && renderFluid.isFluid(tp, Facing.Bottom)) {
      val below: SubtileFluid = renderFluid.getFluid(tp, Facing.Bottom)._1
      if (below.pressure < below.maxPressure) height = 0
    }
    if (height > BLOCK_SIZE) height = BLOCK_SIZE
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = render.getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, screenVec.x.toFloat + offX, screenVec.y.toFloat + offY + (BLOCK_SIZE - height), 1, 1, BLOCK_SIZE, height, 1, 1, rotation)
  }
}