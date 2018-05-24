package com.dafttech.terra.engine.lighting

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

class PointLight extends Light {
  private[lighting] var position: Vector2 = Vector2.Null
  private[lighting] var size: Double = 0

  def this(p: Vector2, s: Float) {
    this()
    position = p
    size = s
  }

  def setPosition(p: Vector2) : Unit = position = p

  def getSize: Double = size

  def setSize(size: Double): Unit = this.size = size

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity) {
    val p: Vector2 = position.toRenderPosition(pointOfView.getPosition)
    if (!(screen.batch.getColor == color)) {
      screen.batch.end()
      screen.batch.setColor(color)
      screen.batch.begin()
    }
    screen.batch.draw(Resources.LIGHT.getImage("pointlight"), (p.x - size).toFloat, (p.y - size).toFloat, size.toFloat * 2, size.toFloat * 2)
  }

  def getRenderPosition: Vector2 = position
}