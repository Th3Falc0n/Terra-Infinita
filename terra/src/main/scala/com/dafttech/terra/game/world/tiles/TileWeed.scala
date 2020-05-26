package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.{GameWorld, TileInstance}
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileWeed() extends Tile {
  override val getImage: Task[TextureRegion] = Resources.TILES.getImageTask("weed")

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def update(tileInstance: TileInstance, delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(tileInstance, delta)

    if (TerraInfinita.rnd.nextInt(20) == 0)
      tryGrow(tilePosition.world, tilePosition.pos + (TerraInfinita.rnd.nextInt(3) - 1, TerraInfinita.rnd.nextInt(3) - 1))
  }

  def tryGrow(world: GameWorld, pos: Vector2i): Unit = {
    if (world.getTile(pos) != null && !world.getTile(pos).tile.isInstanceOf[TileGrass]) return

    val canGrowOn = world.getTile(pos + (0, 1)).tile match {
      case _: TileWeed => true
      case _: TileDirt => true
      case _ => false
    }

    if (canGrowOn)
      world.setTile(pos, TileInstance(new TileWeed()), notify = true)
  }
}