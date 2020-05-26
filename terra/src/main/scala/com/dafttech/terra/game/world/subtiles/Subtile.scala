package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.renderer.{SubtileRenderer, SubtileRendererMask}
import com.dafttech.terra.engine.{AbstractScreen, IDrawableInWorld, TilePosition}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import monix.eval.Task

abstract class Subtile extends IDrawableInWorld {
  def getImage: Task[TextureRegion]

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