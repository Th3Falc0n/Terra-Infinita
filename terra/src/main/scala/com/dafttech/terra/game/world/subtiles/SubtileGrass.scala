package com.dafttech.terra.game.world.subtiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.TileDirt
import com.dafttech.terra.resources.Resources

class SubtileGrass() extends Subtile {
  override def getImage: TextureRegion = Resources.TILES.getImage("mask_grass")

  private val spreadDistance: Int = 3

  override def onTick(world: World, delta: Float): Unit =
    if (world.getTile(getTile.getPosition.$minus(0, 1)).isOpaque) getTile.removeSubtile(this)
    else if (world.getTile(getTile.getPosition.$minus(0, 1)).getTemperature > 50 && !this.isInstanceOf[SubtileDryGrass]) {
      getTile.removeSubtile(this)
      getTile.addSubtile(new SubtileDryGrass)
    }
    else spread(world)

  def spread(world: World): Unit = {
    val spreadPosition = getTile.getPosition.$plus(new Random().nextInt(spreadDistance * 2) - spreadDistance, new Random().nextInt(spreadDistance * 2) - spreadDistance)
    val spreadTile = world.getTile(spreadPosition)
    if (spreadTile != null && spreadTile.isInstanceOf[TileDirt] && world.getTile(spreadPosition.$minus(0, 1)).isAir) if (!spreadTile.hasSubtile(classOf[SubtileGrass], true)) spreadTile.addSubtile(new SubtileGrass)
  }
}