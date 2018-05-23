package com.dafttech.terra.engine.gui.containers

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.Vector2

class ContainerOnscreen extends GUIContainer(p = Vector2.Null, s = new Vector2(Gdx.graphics.getWidth, Gdx.graphics.getHeight)) {
  private[containers] var active: Boolean = false

  def setActive(b: Boolean): Unit = active = b

  override def providesActiveHierarchy: Boolean = active
}