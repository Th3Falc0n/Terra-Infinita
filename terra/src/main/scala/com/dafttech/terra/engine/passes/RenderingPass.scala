package com.dafttech.terra.engine.passes

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity

object RenderingPass {
  var rpObjects: PassObjects = new PassObjects
  var rpLighting: PassLighting = new PassLighting
  var rpGaussian: PassGaussian = new PassGaussian
  var rpGUIContainer: PassGUIContainer = new PassGUIContainer
}

abstract class RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, w: World, arguments: AnyRef*)

  Events.EVENTMANAGER.registerEventListener(this)
}