package com.dafttech.terra.game.world.tiles

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererBlock}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

abstract class TileFalling extends Tile {
  private var renderOffset: Vector2 = Vector2.Null
  private var createTime: Float = 0

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.onTick(delta)

    fallIfPossible

    if (!renderOffset.isNull) {
      val possSpeed = getFallSpeed(tilePosition.world) * delta
      if (renderOffset.x > 0) renderOffset = renderOffset - (if (possSpeed > renderOffset.x) renderOffset.x else possSpeed, 0)
      if (renderOffset.y > 0) renderOffset = renderOffset - (0, if (possSpeed > renderOffset.y) renderOffset.y else possSpeed)
      if (renderOffset.x < 0) renderOffset = renderOffset + (if (possSpeed > -renderOffset.x) -renderOffset.x else possSpeed, 0)
      if (renderOffset.y < 0) renderOffset = renderOffset + (0, if (possSpeed > -renderOffset.y) -renderOffset.y else possSpeed)
    }
  }

  def fall(x: Int, y: Int)(implicit tilePosition: TilePosition): Unit = {
    renderOffset = renderOffset - (x, y)
    tilePosition.moveTile(tilePosition.pos + (x, y))
  }

  def fallIfPossible(implicit tilePosition: TilePosition): Unit =
    if (renderOffset.isNull) {
      if (createTime == 0) createTime = tilePosition.world.time
      if (createTime + getFallDelay(tilePosition.world) < tilePosition.world.time && tilePosition.world.getTile(tilePosition.pos + (0, 1)).isReplacable) fall(0, 1)
    }

  def getRenderOffset: Vector2 = renderOffset

  override def getRenderer: TileRenderer = {
    val tileRenderer = new TileRendererBlock
    tileRenderer.setOffset(getRenderOffset)
    tileRenderer
  }

  override def onTileSet(implicit tilePosition: TilePosition): Unit = fallIfPossible

  override def onNeighborChange(changed: TilePosition)(implicit tilePosition: TilePosition): Unit = fallIfPossible

  //override def onTileDestroyed(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  //override def onTilePlaced(causer: Entity)(implicit tilePosition: TilePosition): Unit = ()

  def getFallSpeed(world: World): Float

  def getFallDelay(world: World): Float
}