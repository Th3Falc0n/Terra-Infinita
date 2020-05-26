package com.dafttech.terra.game.world.subtiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.tiles.TileDirt
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class SubtileGrass extends Subtile {
  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("mask_grass")

  private val spreadDistance: Int = 3

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit =
    if (tilePosition.world.getTile(tilePosition.pos - (0, 1)).tile.isOpaque) tilePosition.getTile.removeSubtile(this)
    else if (tilePosition.world.getTile(tilePosition.pos - (0, 1)).tile.getTemperature > 50 && !this.isInstanceOf[SubtileDryGrass]) {
      tilePosition.getTile.removeSubtile(this)
      tilePosition.getTile.addSubtile(new SubtileDryGrass)
    }
    else spread(tilePosition)

  def spread(tilePosition: TilePosition): Unit = {
    val spreadPosition = tilePosition.pos + (new Random().nextInt(spreadDistance * 2) - spreadDistance, new Random().nextInt(spreadDistance * 2) - spreadDistance)
    val spreadTile = tilePosition.world.getTile(spreadPosition)
    if (spreadTile != null && spreadTile.tile.isInstanceOf[TileDirt] && tilePosition.world.getTile(spreadPosition - (0, 1)).tile.isAir) if (!spreadTile.hasSubtile(classOf[SubtileGrass], true)) spreadTile.addSubtile(new SubtileGrass)
  }
}