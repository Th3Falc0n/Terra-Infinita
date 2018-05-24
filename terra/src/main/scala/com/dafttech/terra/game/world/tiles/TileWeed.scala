package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

class TileWeed() extends Tile {
  override def getImage: TextureRegion = Resources.TILES.getImage("weed")

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)

    if (TerraInfinita.rnd.nextInt(20) == 0)
      tryGrow(world, getPosition + (TerraInfinita.rnd.nextInt(3) - 1, TerraInfinita.rnd.nextInt(3) - 1))
  }

  def tryGrow(world: World, pos: Vector2i): Unit = {
    if (world.getTile(pos) != null && !world.getTile(pos).isInstanceOf[TileGrass]) return

    val canGrowOn = world.getTile(pos + (0, 1)) match {
      case _: TileWeed => true
      case _: TileDirt => true
      case _ => false
    }

    if (canGrowOn)
      world.setTile(pos, new TileWeed, notify = true)
  }
}