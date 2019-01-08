package com.dafttech.terra.engine.lighting

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.Entity

abstract class Light {
  private[lighting] var color: Color = Color.WHITE

  def setColor(c: Color): Unit = {
    color = c
    color.a = 1
  }

  def getColor: Color = color

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity, pos: Vector2d)
}