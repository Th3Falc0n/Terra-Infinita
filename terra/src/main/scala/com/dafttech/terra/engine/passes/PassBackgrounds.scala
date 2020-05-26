package com.dafttech.terra.engine.passes

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.{ Options, Resources }

class PassBackgrounds extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, world: GameWorld, arguments: AnyRef*): Unit = {
    screen.batch.setProjectionMatrix(screen.projectionIngame)
    screen.shr.setProjectionMatrix(screen.projectionIngame)

    screen.batch.setShader(null)
    //screen.batch.enableBlending()
    //screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

    screen.batch.begin()

    val offset1 = pointOfView.getPosition.x / 8f % AbstractScreen.getVWidth
    val offset2 = pointOfView.getPosition.x / 5f % AbstractScreen.getVWidth
    val offset3 = pointOfView.getPosition.x / 3f % AbstractScreen.getVWidth
    val offset4 = pointOfView.getPosition.x / 2f % AbstractScreen.getVWidth

    screen.batch.draw(Resources.BACKGROUND.getImage("pm0"), 0, 0, 1440, 900)

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm1"), -AbstractScreen.getVWidth + xo * AbstractScreen.getVWidth - offset1.toFloat, 0, AbstractScreen.getVWidth, AbstractScreen.getVHeight)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm2"), -AbstractScreen.getVWidth + xo * AbstractScreen.getVWidth - offset2.toFloat, 0, AbstractScreen.getVWidth, AbstractScreen.getVHeight)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm3"), -AbstractScreen.getVWidth + xo * AbstractScreen.getVWidth - offset3.toFloat, 0, AbstractScreen.getVWidth, AbstractScreen.getVHeight)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm4"), -AbstractScreen.getVWidth + xo * AbstractScreen.getVWidth - offset4.toFloat, 0, AbstractScreen.getVWidth, AbstractScreen.getVHeight)
    }


    screen.batch.end()
  }
}