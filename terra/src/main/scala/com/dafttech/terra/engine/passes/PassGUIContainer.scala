package com.dafttech.terra.engine.passes

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.gui.containers.GUIContainer
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import org.lwjgl.opengl.GL11

class PassGUIContainer extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, w: World, arguments: AnyRef*) {
    screen.batch.setShader(null)
    screen.batch.enableBlending
    screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    (arguments(0).asInstanceOf[GUIContainer]).draw(screen)
  }
}