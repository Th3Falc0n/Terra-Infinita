package com.dafttech.terra.game.world.environment

import com.dafttech.terra.game.world.World

abstract class Weather {
  def update(delta: Float)(implicit world: World): Unit

  def getWindSpeed(implicit world: World): Float
}