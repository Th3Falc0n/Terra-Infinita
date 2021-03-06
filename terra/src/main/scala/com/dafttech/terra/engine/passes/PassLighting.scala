package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.UnsafeLwjglGraphics
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.glutils.{FloatFrameBuffer, FrameBuffer}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.engine.{AbstractScreen, TilePosition}
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK
import org.lolhens.eventmanager.{Event, EventListener}

class PassLighting extends RenderingPass {
  private[passes] var buffer: FrameBuffer = {
    UnsafeLwjglGraphics.bufferHack()
    new FloatFrameBuffer(Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  }

  var sunlevel: Float = METERS_PER_BLOCK

  def getSunlightRect(t: TilePosition, pointOfView: Entity): Rectangle = {
    val v: Vector2d = t.pos.toScreenPos(pointOfView)

    if (t.getTile.sunlightFilter == null)
      Vector2d(v.x - sunlevel, 0) rectangleTo Vector2d(METERS_PER_BLOCK + sunlevel * 2, v.y + sunlevel)
    else {
      val f: Vector2d = t.getTile.sunlightFilter.pos.toScreenPos(pointOfView)
      Vector2d(v.x - sunlevel, f.y) rectangleTo Vector2d(METERS_PER_BLOCK + sunlevel * 2, v.y - f.y + sunlevel)
    }
  }

  @EventListener(value = Array("WINRESIZE")) def onResize(e: Event): Unit = {
    buffer = new FloatFrameBuffer(Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  }

  @SuppressWarnings(Array("unused")) def applyPass(screen: AbstractScreen, pointOfView: Entity, world: GameWorld, arguments: AnyRef*): Unit = {
    screen.batch.setProjectionMatrix(screen.projectionIngame)
    screen.shr.setProjectionMatrix(screen.projectionIngame)

    buffer.begin()

    Gdx.graphics.getGL20.glClearColor(0, 0, 0, 0)
    Gdx.graphics.getGL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)

    screen.batch.enableBlending()
    screen.batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE)

    val sx: Int = (2 + AbstractScreen.getVWidth / METERS_PER_BLOCK / 2).toInt
    val sy: Int = (2 + AbstractScreen.getVHeight / METERS_PER_BLOCK / 2).toInt

    val px = (pointOfView.getPosition.x.toInt / METERS_PER_BLOCK).toInt
    val py = (pointOfView.getPosition.y.toInt / METERS_PER_BLOCK).toInt

    val resetToWhite: Boolean = false
    var nextClr: Color = Color.WHITE
    var activeClr: Color = Color.WHITE

    screen.batch.begin()
    screen.shr.begin(ShapeType.Filled)
    screen.shr.setColor(nextClr)

    var x: Int = px - sx

    while (x < px + sx) {
      var y: Int = py - sy

      while (y < pointOfView.getPosition.y.toInt / METERS_PER_BLOCK + sy) {

        if (world.getTile(Vector2i(x, y)) != null) {
          if (world.getTile(Vector2i(x, y)).receivesSunlight) {

            nextClr = world.getTile(Vector2i(x, y)).getSunlightColor

            if (nextClr ne activeClr) {
              activeClr = nextClr
              screen.shr.end()
              screen.shr.setColor(nextClr)
              screen.shr.begin(ShapeType.Filled)
            }

            val rect: Rectangle = getSunlightRect(TilePosition(world, Vector2i(x, y)), pointOfView)
            screen.shr.rect(rect.x, rect.y, rect.width, rect.height)
          }
          if (world.getTile(Vector2i(x, y)).isLightEmitter && world.getTile(Vector2i(x, y)).getEmittedLight != null) {
            world.getTile(Vector2i(x, y)).getEmittedLight.drawToLightmap(screen, pointOfView, Vector2i(x, y).toEntityPos)
          }
        }

        y += 1
      }

      x += 1
    }
    screen.shr.end()
    screen.batch.end()
    screen.batch.begin()

    for (entity <- world.getEntities) {
      if (entity.isLightEmitter && entity.getEmittedLight != null && world.isInRenderRange(entity.getPosition)) {
        entity.getEmittedLight.drawToLightmap(screen, pointOfView, entity.getPosition)
      }
    }

    screen.batch.end()
    screen.batch.setColor(Color.WHITE)
    buffer.end()
    RenderingPass.rpGaussian.applyPass(screen, world.localPlayer, world, buffer.getColorBufferTexture, buffer)
    screen.batch.setShader(null)
    screen.batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO)
    screen.batch.enableBlending()
    screen.batch.begin()
    screen.batch.draw(buffer.getColorBufferTexture, 0, 0)
    screen.batch.end()
  }
}