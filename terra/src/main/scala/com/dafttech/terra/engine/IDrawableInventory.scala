package com.dafttech.terra.engine

trait IDrawableInventory {
  def update(delta: Float)

  def drawInventory(pos: Vector2, screen: AbstractScreen)
}