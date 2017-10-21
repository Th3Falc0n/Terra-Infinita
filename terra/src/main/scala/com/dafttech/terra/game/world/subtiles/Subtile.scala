package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.renderer.{SubtileRenderer, SubtileRendererMask}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.Item
import com.dafttech.terra.game.world.tiles.Tile

abstract class Subtile() extends Item with IDrawableInWorld {
  protected var tile: Tile = _

  override def use(causer: EntityLiving, position: Vector2): Boolean = false

  def getRenderer: SubtileRenderer = SubtileRendererMask.$Instance

  def getTile: Tile = tile

  def setTile(t: Tile): Unit = tile = t

  def canBePlacedOn(tile: Tile) = true

  override def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit =
    getRenderer.draw2(screen, this, pointOfView)

  override def update(world: World, delta: Float): Unit = ()

  override def update(delta: Float): Unit = ()

  final def tick(world: World, delta: Float): Unit = onTick(world, delta)

  def onTick(world: World, delta: Float): Unit = ()

  def isTileIndependent: Boolean = false

  def providesSunlightFilter: Boolean = false

  def getFilterColor: Color = Color.WHITE
}