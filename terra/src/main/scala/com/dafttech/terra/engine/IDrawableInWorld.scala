package com.dafttech.terra.engine

import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

trait IDrawableInWorld {
  def update(delta: Float)(implicit tilePosition: TilePosition)

  def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition)
}