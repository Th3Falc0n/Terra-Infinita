package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.engine.vector.Vector2i
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK
import org.lwjgl.opengl.GL11

class PassObjects extends RenderingPass {
  def applyPass(screen: AbstractScreen, pointOfView: Entity, world: GameWorld, arguments: AnyRef*): Unit = {
    screen.batch.setProjectionMatrix(screen.projectionIngame)
    screen.shr.setProjectionMatrix(screen.projectionIngame)

    screen.batch.setShader(null)
    screen.batch.enableBlending()
    screen.batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    val sx: Int = (2 + AbstractScreen.getVWidth / METERS_PER_BLOCK / 2).toInt
    val sy: Int = (2 + AbstractScreen.getVHeight / METERS_PER_BLOCK / 2).toInt

    val px: Int = (pointOfView.getPosition.x / METERS_PER_BLOCK).toInt
    val py: Int = (pointOfView.getPosition.y / METERS_PER_BLOCK).toInt


    screen.batch.begin()
    var tile: Tile = null

    for {
      x <- (px - sx) until (px + sx)
      y <- (py - sy) until (py + sy)
    } {
      tile = world.getTile(Vector2i(x, y))
      if (tile != null) tile.draw(screen, pointOfView)(TilePosition(world, Vector2i(x, y)))
      world.renderer.draw(screen, pointOfView)(TilePosition(world, Vector2i(x, y)))
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
      entity <- world.getEntities
    } {
      entity.draw(screen, pointOfView)(TilePosition(world, entity.getPosition.toWorldPosition))
    }
    screen.batch.end()
  }
}