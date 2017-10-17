package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.Facing
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.{Subtile, SubtileFluid}
import com.dafttech.terra.resources.Options.BLOCK_SIZE

object SubtileRendererFluid {
  var $Instance: SubtileRenderer = new SubtileRendererFluid
}

class SubtileRendererFluid extends SubtileRendererMask {
  override def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*) {
    val screenVec: Vector2 = render.getTile.getPosition.toScreenPos(pointOfView)
    val rotation: Float = if (rendererArguments.length > 0) rendererArguments(0).asInstanceOf[Float] else 0
    var offX: Float = 0
    var offY: Float = 0
    if (!render.isTileIndependent && render.getTile != null) {
      val offset: Vector2 = render.getTile.getRenderer.getOffset
      if (offset != null) {
        offX = offset.x.toFloat * BLOCK_SIZE
        offY = offset.y.toFloat * BLOCK_SIZE
      }
    }
    var height: Float = (render.asInstanceOf[SubtileFluid]).pressure / (render.asInstanceOf[SubtileFluid]).maxPressure * BLOCK_SIZE
    if (height < BLOCK_SIZE && (render.asInstanceOf[SubtileFluid]).isFluid(render.getTile.getWorld, Facing.TOP)) {
      val above: SubtileFluid = (render.asInstanceOf[SubtileFluid]).getFluid(render.getTile.getWorld, Facing.TOP)
      height += above.pressure / above.maxPressure * BLOCK_SIZE
    }
    if (height < BLOCK_SIZE && (render.asInstanceOf[SubtileFluid]).isFluid(render.getTile.getWorld, Facing.BOTTOM)) {
      val below: SubtileFluid = (render.asInstanceOf[SubtileFluid]).getFluid(render.getTile.getWorld, Facing.BOTTOM)
      if (below.pressure < below.maxPressure) height = 0
    }
    if (height > BLOCK_SIZE) height = BLOCK_SIZE
    screen.batch.draw(render.getImage, screenVec.x.toFloat + offX, screenVec.y.toFloat + offY + (BLOCK_SIZE - height), 1, 1, BLOCK_SIZE, height, 1, 1, rotation)
  }
}