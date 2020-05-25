package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.engine.{ AbstractScreen, TilePosition }
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import com.dafttech.terra.resources.Resources
import org.lwjgl.opengl.GL11

import scala.concurrent.duration._

class PassBackgrounds extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, world: World, arguments: AnyRef*): Unit = {
    screen.batch.setShader(null)
    //screen.batch.enableBlending()
    //screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

    screen.batch.begin()

    import com.dafttech.terra.utils.RenderThread._

    val offset1 = pointOfView.getPosition.x / 24 % 1280
    val offset2 = pointOfView.getPosition.x / 12 % 1280
    val offset3 = pointOfView.getPosition.x / 6 % 1280
    val offset4 = pointOfView.getPosition.x / 3 % 1280

    screen.batch.draw(Resources.BACKGROUND.getImage("pm0").runSyncUnsafe(5.seconds), 0, 0, 1440, 900)

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm1").runSyncUnsafe(5.seconds), -1280 + xo * 1280 - offset1.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm2").runSyncUnsafe(5.seconds), -1280 + xo * 1280 - offset2.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm3").runSyncUnsafe(5.seconds), -1280 + xo * 1280 - offset3.toFloat, 0, 1440, 900)
    }

    for (xo <- 0 to 2) {
      screen.batch.draw(Resources.BACKGROUND.getImage("pm4").runSyncUnsafe(5.seconds), -1280 + xo * 1280 - offset4.toFloat, 0, 1440, 900)
    }


    screen.batch.end()
  }
}