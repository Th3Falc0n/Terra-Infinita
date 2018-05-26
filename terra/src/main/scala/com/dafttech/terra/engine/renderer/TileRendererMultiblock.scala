package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, Vector2, Vector2i}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import scala.concurrent.duration._

object TileRendererMultiblock {
  def setRegion(texture: TextureRegion, x: Float, y: Float, width: Float, height: Float): Unit = {
    val invTexWidth: Float = 1.0F / texture.getTexture.getWidth
    val invTexHeight: Float = 1.0F / texture.getTexture.getHeight
    texture.setRegion(x * invTexWidth, y * invTexHeight, (x + width) * invTexWidth, (y + height) * invTexHeight)
  }
}

class TileRendererMultiblock extends TileRendererBlock {
  protected var multiblockSize: Vector2i = _

  def this(multiblockSize: Vector2i) {
    this()
    this.multiblockSize = multiblockSize
  }

  override def draw(screen: AbstractScreen, tile: Tile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition): Unit = {
    val screenVec: Vector2 = tp.pos.toScreenPos(pointOfView)
    import monix.execution.Scheduler.Implicits.global
    val texture = tile.getImage.runSyncUnsafe(5.seconds)
    val cols: Int = multiblockSize.x
    val rows: Int = multiblockSize.y
    var col: Int = Math.abs(tp.pos.x).toInt % cols
    var row: Int = Math.abs(tp.pos.y).toInt % rows
    if (tp.pos.x < 0) col = ((cols - 1) - col + (cols - 1)) % cols
    if (tp.pos.y < 0) row = ((rows - 1) - row + (rows - 1)) % rows
    val newTexture: TextureRegion = new TextureRegion(texture)
    val width: Double = newTexture.getRegionWidth / cols.toDouble
    val height: Double = newTexture.getRegionHeight / rows.toDouble
    val x: Double = width * col
    val y: Double = height * row
    TileRendererMultiblock.setRegion(newTexture, x.toFloat, y.toFloat, width.toFloat, height.toFloat)
    newTexture.flip(texture.isFlipX, texture.isFlipY)
    screen.batch.draw(newTexture, (screenVec.x + offset.x * BLOCK_SIZE).toFloat, (screenVec.y + offset.y * BLOCK_SIZE).toFloat, BLOCK_SIZE, BLOCK_SIZE)
  }
}