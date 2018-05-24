package com.dafttech.terra.game.world.tiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.items.persistence.Persistent
import com.dafttech.terra.game.world.subtiles.SubtileGrass
import com.dafttech.terra.resources.Resources

class TileGrass() extends Tile {
  @Persistent private val grassIndex: Int = TerraInfinita.rnd.nextInt(5)

  override def getImage: TextureRegion = Resources.TILES.getImage("grass", grassIndex)

  override def isCollidableWith(entity: Entity): Boolean = false

  override def isOpaque: Boolean = false

  override def onNeighborChange(world: World, changed: Tile): Unit =
    if (world.getTile(getPosition + (0, 1)).isAir)
      world.destroyTile(getPosition.x, getPosition.y, null)

  override def onTileDestroyed(world: World, causer: Entity): Unit = ()

  override def onTilePlaced(world: World, causer: Entity): Unit = ()

  override def onTileSet(world: World): Unit = ()

  override def isFlammable: Boolean = true

  override def isReplacable: Boolean = true

  private val spreadDistance: Int = 3

  override def onTick(world: World, delta: Float): Unit = {
    val spreadPosition = getPosition + (
      new Random().nextInt(spreadDistance * 2) - spreadDistance,
      new Random().nextInt(spreadDistance * 2) - spreadDistance
    )

    val spreadTile = world.getTile(spreadPosition)

    spreadPosition - (0, 1)

    if (spreadTile != null &&
      world.getTile(spreadPosition).isAir && spreadTile.hasSubtile(classOf[SubtileGrass], inherited = false))
      world.setTile(spreadPosition, new TileGrass, notify = true)
  }
}