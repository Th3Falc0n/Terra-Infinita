package com.dafttech.terra.engine.passes

import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Resources

class PassBackgrounds extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, world: GameWorld, arguments: AnyRef*): Unit = {
    screen.batch.setShader(null)
    //screen.batch.enableBlending()
    //screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

    screen.batch.begin()

    val offset1 = pointOfView.getPosition.x / 24 % 1280
    val offset2 = pointOfView.getPosition.x / 12 % 1280
    val offset3 = pointOfView.getPosition.x / 6 % 1280
    val offset4 = pointOfView.getPosition.x / 3 % 1280

    screen.batch.draw(Resources.BACKGROUND.getImage("pm0"), 0, 0, 1440, 900)

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm1"), -1280 + xo * 1280 - offset1.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm2"), -1280 + xo * 1280 - offset2.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm3"), -1280 + xo * 1280 - offset3.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm4"), -1280 + xo * 1280 - offset4.toFloat, 0, 1440, 900)
    }


    screen.batch.end()
  }
}