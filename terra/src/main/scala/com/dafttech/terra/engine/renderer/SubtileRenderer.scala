package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.Subtile

abstract class SubtileRenderer {
  def draw2(screen: AbstractScreen, render: Subtile, pointOfView: Entity): Unit =
    draw(screen, render, pointOfView)

  def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*)
}