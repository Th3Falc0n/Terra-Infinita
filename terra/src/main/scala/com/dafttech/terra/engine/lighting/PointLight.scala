package com.dafttech.terra.engine.lighting

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

class PointLight extends Light {
  private[lighting] var position: Vector2 = Vector2.Null
  private[lighting] var size: Float = .0f

  def this(p: Vector2, s: Float) {
    this()
    position = p
    size = s
  }

  def setPosition(p: Vector2) {
    position = p
  }

  def getSize: Float = {
    return size
  }

  def setSize(size: Float) {
    this.size = size
  }

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity) {
    val p: Vector2 = position.toRenderPosition(pointOfView.getPosition)
    if (!(screen.batch.getColor == color)) {
      screen.batch.end
      screen.batch.setColor(color)
      screen.batch.begin
    }
    screen.batch.draw(Resources.LIGHT.getImage("pointlight"), p.x.toFloat - size, p.y.toFloat - size, size * 2, size * 2)
  }

  def getRenderPosition: Vector2 = {
    return position
  }
}