package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.vector.Vector2d

class ContainerList(p: Vector2d, s: Vector2d) extends GUIContainer(p, s) {
  private var distance: Float = 2

  def this(p: Vector2d, s: Vector2d, d: Float) {
    this(p, s)
    distance = d
  }

  override def draw(screen: AbstractScreen): Unit = {
    var y: Float = size.y.toFloat

    for (o <- objects) {
      y -= o.size.y.toFloat + distance
      if (y > 0) {
        o.position = Vector2d(0, y)
        o.draw(screen)
      }
    }
  }
}