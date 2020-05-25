package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.GameWorld

abstract class Weather {
  def update(delta: Float)(implicit world: GameWorld): Unit

  def getWindSpeed(implicit world: GameWorld): Float
}