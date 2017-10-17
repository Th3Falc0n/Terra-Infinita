package com.dafttech.terra.engine.gui.modules

import com.dafttech.terra.engine.gui.containers.GUIContainer

abstract class GUIModule {
  var container: GUIContainer = null

  def getContainer: GUIContainer = {
    return container
  }

  def create

  def update(delta: Float) {
  }
}