package com.dafttech.terra.engine.passes

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity

object RenderingPass {
  val rpObjects = new PassObjects
  val rpLighting = new PassLighting
  val rpGaussian = new PassGaussian
  val rpGUIContainer = new PassGUIContainer
  val rpBackground = new PassBackgrounds
}

abstract class RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, w: GameWorld, arguments: AnyRef*)

  Events.EVENTMANAGER.registerEventListener(this)
}