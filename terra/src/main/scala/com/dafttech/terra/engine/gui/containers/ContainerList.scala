package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.{AbstractScreen, Vector2}

class ContainerList(p: Vector2, s: Vector2) extends GUIContainer(p, s) {
  private var distance: Float = 2

  def this(p: Vector2, s: Vector2, d: Float) {
    this(p, s)
    distance = d
  }

  override def draw(screen: AbstractScreen) {
    var y: Float = size.y

    for (o <- objects) {
      y -= o.size.y + distance
      if (y > 0) {
        o.position = new Vector2(0, y)
        o.draw(screen)
      }
    }
  }
}