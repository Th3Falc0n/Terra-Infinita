package com.dafttech.terra.engine.lighting

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

class PointLight extends Light {
  private[lighting] var size: Double = 0

  def this(s: Float) {
    this()
    size = s
  }

  def getSize: Double = size

  def setSize(size: Double): Unit = this.size = size

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity, pos: Vector2) {
    val p: Vector2 = pos.toRenderPosition(pointOfView.getPosition)
    if (!(screen.batch.getColor == color)) {
      screen.batch.end()
      screen.batch.setColor(color)
      screen.batch.begin()
    }
    screen.batch.draw(Resources.LIGHT.getImage("pointlight"), (p.x - size).toFloat, (p.y - size).toFloat, size.toFloat * 2, size.toFloat * 2)
  }
}