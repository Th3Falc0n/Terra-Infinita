package com.dafttech.terra.engine.lighting

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.Entity

abstract class Light {
  private[lighting] var color: Color = Color.WHITE

  def setColor(c: Color) {
    color = c
    color.a = 1
  }

  def getColor: Color = {
    return color
  }

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity)

  def setPosition(p: Vector2)
}