package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import org.lwjgl.opengl.GL11

class PassObjects extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, world: World, arguments: AnyRef*): Unit = {
    screen.batch.setShader(null)
    screen.batch.enableBlending()
    screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    val sx: Int = 2 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
    val sy: Int = 2 + Gdx.graphics.getHeight / BLOCK_SIZE / 2
    screen.batch.begin()
    var tile: Tile = null

    for {
      x <- (pointOfView.getPosition.x.toInt / BLOCK_SIZE - sx) until (pointOfView.getPosition.x.toInt / BLOCK_SIZE + sx)
      y <- (pointOfView.getPosition.y.toInt / BLOCK_SIZE - sy) until (pointOfView.getPosition.y.toInt / BLOCK_SIZE + sy)
    } {
      tile = world.getTile(x, y)
      if (tile != null) tile.draw(tile.getPosition.toVector2, world, screen, pointOfView)
    }

    /*var x: Int = pointOfView.getPosition.x.toInt / BLOCK_SIZE - sx
    while (x < pointOfView.getPosition.x.toInt / BLOCK_SIZE + sx) {
      var y: Int = pointOfView.getPosition.y.toInt / BLOCK_SIZE - sy
      while (y < pointOfView.getPosition.y.toInt / BLOCK_SIZE + sy) {
        tile = world.getTile(x, y)
        if (tile != null) tile.draw(tile.getPosition.toVector2, world, screen, pointOfView)
        y += 1
      }
      x += 1
    }*/

    for {
      chunk <- world.getChunks.values
      entity <- chunk.getLocalEntities
    } {
      entity.draw(entity.getPosition, world, screen, pointOfView)
    }
    screen.batch.end()
  }
}