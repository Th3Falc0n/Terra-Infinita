package com.dafttech.terra.game.world.tiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.items.persistence.Persistent
import com.dafttech.terra.game.world.subtiles.SubtileGrass
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileGrass() extends Tile {
  @Persistent private val grassIndex: Int = TerraInfinita.rnd.nextInt(5)

  override val getImage: Task[TextureRegion] = Resources.TILES.getImage("grass", grassIndex)

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def onNeighborChange(changed: TilePosition)(implicit tilePosition: TilePosition): Unit = {
    if (tilePosition.world.getTile(tilePosition.pos + (0, 1)).isAir)
      tilePosition.world.destroyTile(tilePosition.pos + (0, 1), null)
  }

  override def isFlammable: Boolean = true

  override def isReplacable: Boolean = true

  private val spreadDistance: Int = 3

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    val spreadPosition = tilePosition.pos + (
      new Random().nextInt(spreadDistance * 2) - spreadDistance,
      new Random().nextInt(spreadDistance * 2) - spreadDistance
    )

    val spreadTile = tilePosition.world.getTile(spreadPosition)

    spreadPosition - (0, 1)

    if (spreadTile != null &&
      tilePosition.world.getTile(spreadPosition).isAir && spreadTile.hasSubtile(classOf[SubtileGrass], inherited = false))
      tilePosition.world.setTile(spreadPosition, new TileGrass, notify = true)
  }
}