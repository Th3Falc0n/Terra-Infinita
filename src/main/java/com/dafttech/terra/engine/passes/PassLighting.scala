package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.glutils.{FloatFrameBuffer, FrameBuffer}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import org.lolhens.eventmanager.{Event, EventListener}

class PassLighting extends RenderingPass {
  private[passes] var buffer: FrameBuffer = new FloatFrameBuffer(Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  var sunlevel: Int = BLOCK_SIZE

  def getSunlightRect(t: Tile, pointOfView: Entity): Rectangle = {
    val v: Vector2 = t.getPosition.toScreenPos(pointOfView)
    if (t.sunlightFilter == null) {
      return new Rectangle(v.x - sunlevel, 0, BLOCK_SIZE + sunlevel * 2, v.y + sunlevel)
    }
    else {
      val f: Vector2 = t.sunlightFilter.getPosition.toScreenPos(pointOfView)
      return new Rectangle(v.x - sunlevel, f.y, BLOCK_SIZE + sunlevel * 2, v.y - f.y + sunlevel)
    }
  }

  @EventListener(value = Array("WINRESIZE")) def onResize(e: Event) {
    buffer = new FloatFrameBuffer(Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  }

  @SuppressWarnings(Array("unused")) def applyPass(screen: AbstractScreen, pointOfView: Entity, world: World, arguments: AnyRef*) {
    buffer.begin
    Gdx.graphics.getGL20.glClearColor(0, 0, 0, 0)
    Gdx.graphics.getGL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    screen.batch.enableBlending
    screen.batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE)
    val sx: Int = 2 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
    val sy: Int = 2 + Gdx.graphics.getHeight / BLOCK_SIZE / 2
    val resetToWhite: Boolean = false
    var nextClr: Color = Color.WHITE
    var activeClr: Color = Color.WHITE
    screen.batch.begin
    screen.shr.begin(ShapeType.Filled)
    screen.shr.setColor(nextClr)
    var x: Int = pointOfView.getPosition.x.toInt / BLOCK_SIZE - sx
    while (x < pointOfView.getPosition.x.toInt / BLOCK_SIZE + sx) {
      var y: Int = pointOfView.getPosition.y.toInt / BLOCK_SIZE - sy
      while (y < pointOfView.getPosition.y.toInt / BLOCK_SIZE + sy) {
        if (world.getTile(x, y) != null) {
          if (world.getTile(x, y).receivesSunlight) {
            nextClr = world.getTile(x, y).getSunlightColor
            if (nextClr ne activeClr) {
              activeClr = nextClr
              screen.shr.end
              screen.shr.setColor(nextClr)
              screen.shr.begin(ShapeType.Filled)
            }
            val rect: Rectangle = getSunlightRect(world.getTile(x, y), pointOfView)
            screen.shr.rect(rect.x, rect.y, rect.width, rect.height)
          }
          if (world.getTile(x, y).isLightEmitter && world.getTile(x, y).getEmittedLight != null) {
            world.getTile(x, y).getEmittedLight.drawToLightmap(screen, pointOfView)
          }
        }
        y += 1
      }
    }
    x += 1
    screen.shr.end
    screen.batch.end
    screen.batch.begin
    import scala.collection.JavaConversions._
    for (chunk <- world.localChunks.values) {
      for (entity <- chunk.getLocalEntities) {
        if (entity.isLightEmitter && entity.getEmittedLight != null && world.isInRenderRange(entity.getPosition)) {
          entity.getEmittedLight.drawToLightmap(screen, pointOfView)
        }
      }
    }
    screen.batch.end
    screen.batch.setColor(Color.WHITE)
    buffer.end
    RenderingPass.rpGaussian.applyPass(screen, world.localPlayer, world, buffer.getColorBufferTexture, buffer)
    screen.batch.setShader(null)
    screen.batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO)
    screen.batch.enableBlending
    screen.batch.begin
    screen.batch.draw(buffer.getColorBufferTexture, 0, 0)
    screen.batch.end
  }
}