package com.dafttech.terra.engine.renderer

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.subtiles.Subtile

abstract class SubtileRenderer {
  def draw(screen: AbstractScreen, render: Subtile, pointOfView: Entity, rendererArguments: AnyRef*)(implicit tp: TilePosition)
}