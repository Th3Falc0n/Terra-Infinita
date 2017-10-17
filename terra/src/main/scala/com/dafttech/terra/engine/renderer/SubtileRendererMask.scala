package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.Subtile
import com.dafttech.terra.resources.Options.BLOCK_SIZE

object SubtileRendererMask {
  var $Instance: SubtileRenderer = new SubtileRendererMask
}

class SubtileRendererMask extends SubtileRenderer {
  def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*) {
    val screenVec: Vector2 = render.getTile.getPosition.toScreenPos(pointOfView)
    val rotation: Float = if (rendererArguments.length > 0) rendererArguments(0).asInstanceOf[Float] else 0
    var offX: Float = 0
    var offY: Float = 0
    if (!render.isTileIndependent && render.getTile != null) {
      val offset: Vector2 = render.getTile.getRenderer.getOffset
      if (offset != null) {
        offX = offset.x * BLOCK_SIZE
        offY = offset.y * BLOCK_SIZE
      }
    }
    screen.batch.draw(render.getImage, screenVec.x + offX, screenVec.y + offY, 1, 1, BLOCK_SIZE, BLOCK_SIZE, 1, 1, rotation)
  }
}