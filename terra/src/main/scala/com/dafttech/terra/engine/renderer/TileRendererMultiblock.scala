package com.dafttech.terra.engine.renderer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.{AbstractScreen, Vector2, Vector2i}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE

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

  override def draw(pos: Vector2, world: World, screen: AbstractScreen, tile: Tile, pointOfView: Entity, rendererArguments: AnyRef*): Unit = {
    val screenVec: Vector2 = tile.getPosition.toScreenPos(pointOfView)
    val texture: TextureRegion = tile.getImage
    val cols: Int = multiblockSize.x
    val rows: Int = multiblockSize.y
    var col: Int = Math.abs(pos.x).toInt % cols
    var row: Int = Math.abs(pos.y).toInt % rows
    if (pos.x < 0) col = ((cols - 1) - col + (cols - 1)) % cols
    if (pos.y < 0) row = ((rows - 1) - row + (rows - 1)) % rows
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