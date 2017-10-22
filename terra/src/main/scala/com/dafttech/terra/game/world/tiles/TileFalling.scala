package com.dafttech.terra.game.world.tiles

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererBlock}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

abstract class TileFalling() extends Tile {
  private var renderOffset: Vector2 = Vector2.Null
  private var createTime: Float = 0

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)
    fallIfPossible(world)
    if (!renderOffset.isNull) {
      val possSpeed = getFallSpeed(world) * delta
      if (renderOffset.x > 0) renderOffset = renderOffset - (if (possSpeed > renderOffset.x) renderOffset.x else possSpeed, 0)
      if (renderOffset.y > 0) renderOffset = renderOffset - (0, if (possSpeed > renderOffset.y) renderOffset.y else possSpeed)
      if (renderOffset.x < 0) renderOffset = renderOffset + (if (possSpeed > -renderOffset.x) -renderOffset.x else possSpeed, 0)
      if (renderOffset.y < 0) renderOffset = renderOffset + (0, if (possSpeed > -renderOffset.y) -renderOffset.y else possSpeed)
    }
  }

  def fall(world: World, x: Int, y: Int): Unit = {
    renderOffset = renderOffset.$minus(x, y)
    world.setTile(getPosition.$plus(x, y), this, notify = true)
  }

  def fallIfPossible(world: World): Unit =
    if (renderOffset.isNull) {
      if (createTime == 0) createTime = world.time
      if (createTime + getFallDelay(world) < world.time && world.getTile(getPosition.$plus(0, 1)).isReplacable) fall(world, 0, 1)
    }

  def getRenderOffset: Vector2 = renderOffset

  override def getRenderer: TileRenderer = {
    val tileRenderer = new TileRendererBlock
    tileRenderer.setOffset(getRenderOffset)
    tileRenderer
  }

  override def onTileSet(world: World): Unit = fallIfPossible(world)

  override def onNeighborChange(world: World, changed: Tile): Unit = fallIfPossible(world)

  override def onTileDestroyed(world: World, causer: Entity): Unit = {
  }

  override def onTilePlaced(world: World, causer: Entity): Unit = {
  }

  def getFallSpeed(world: World): Float

  def getFallDelay(world: World): Float
}