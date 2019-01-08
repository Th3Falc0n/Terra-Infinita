package com.dafttech.terra.engine.lighting

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

import scala.concurrent.duration._

class PointLight extends Light {
  private[lighting] var size: Double = 0

  def this(s: Float) {
    this()
    size = s
  }

  def getSize: Double = size

  def setSize(size: Double): Unit = this.size = size

  def drawToLightmap(screen: AbstractScreen, pointOfView: Entity, pos: Vector2d) {
    val p: Vector2d = pos.toRenderPosition(pointOfView.getPosition)
    if (!(screen.batch.getColor == color)) {
      screen.batch.end()
      screen.batch.setColor(color)
      screen.batch.begin()
    }
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = Resources.LIGHT.getImage("pointlight").runSyncUnsafe(5.seconds)
    screen.batch.draw(image, (p.x - size).toFloat, (p.y - size).toFloat, size.toFloat * 2, size.toFloat * 2)
  }
}