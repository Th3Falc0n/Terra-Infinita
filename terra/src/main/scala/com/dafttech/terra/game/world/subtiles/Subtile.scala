package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.renderer.{SubtileRenderer, SubtileRendererMask}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.items.ItemTile
import com.dafttech.terra.game.world.tiles.Tile

abstract class Subtile extends ItemTile with IDrawableInWorld {
  override def use(causer: EntityLiving, position: Vector2): Boolean = false

  def getRenderer: SubtileRenderer = SubtileRendererMask.$Instance

  def canBePlacedOn(tile: Tile) = true

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit =
    getRenderer.draw(screen, this, pointOfView)

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = ()

  final def tick(delta: Float)(implicit tilePosition: TilePosition): Unit = onTick(delta)

  def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = ()

  def isTileIndependent: Boolean = false

  def providesSunlightFilter: Boolean = false

  def getFilterColor: Color = Color.WHITE
}