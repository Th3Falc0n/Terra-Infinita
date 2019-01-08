package com.dafttech.terra.engine

import com.dafttech.terra.engine.vector.Vector2d

trait IDrawableInventory {
  def update(delta: Float)

  def drawInventory(pos: Vector2d, screen: AbstractScreen)
}