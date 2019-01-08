package com.dafttech.terra.engine.gui.containers

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.vector.Vector2d

class ContainerOnscreen extends GUIContainer(p = Vector2d.Zero, s = Vector2d(Gdx.graphics.getWidth, Gdx.graphics.getHeight)) {
  private[containers] var active: Boolean = false

  def setActive(b: Boolean): Unit = active = b

  override def providesActiveHierarchy: Boolean = active
}