package com.dafttech.terra.engine

import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

trait IDrawableInWorld {
  def update(world: World, delta: Float)

  def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity)
}