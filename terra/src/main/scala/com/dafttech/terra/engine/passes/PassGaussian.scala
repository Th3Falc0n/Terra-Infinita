package com.dafttech.terra.engine.passes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.shaders.ShaderLibrary
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import org.lolhens.eventmanager.{Event, EventListener}

class PassGaussian extends RenderingPass {
  private[passes] var bfPass1: FrameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  private[passes] var bfPass2: FrameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  private[passes] var pass: Texture = null

  @EventListener(value = Array("WINRESIZE")) def onResize(e: Event) {
    bfPass1 = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
    bfPass2 = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth, Gdx.graphics.getHeight, false)
  }

  def applyPass(screen: AbstractScreen, pointOfView: Entity, w: World, arguments: AnyRef*) {
    if (!(arguments(0).isInstanceOf[Texture])) throw new IllegalArgumentException("Need a texture to draw")
    screen.batch.disableBlending
    var size: Float = 0.010f
    if (arguments.length > 2 && arguments(2) != null) {
      size = arguments(2).asInstanceOf[Float]
    }
    pass = arguments(0).asInstanceOf[Texture]
    bfPass1.begin
    Gdx.graphics.getGL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    screen.batch.setShader(ShaderLibrary.getShader("GaussV"))
    screen.batch.begin
    ShaderLibrary.getShader("GaussV").setUniformf("u_size", size)
    screen.batch.draw(pass, 0, 0)
    screen.batch.end
    bfPass1.end
    pass = bfPass1.getColorBufferTexture
    bfPass2.begin
    Gdx.graphics.getGL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    screen.batch.setShader(ShaderLibrary.getShader("GaussH"))
    screen.batch.begin
    ShaderLibrary.getShader("GaussH").setUniformf("u_size", size)
    screen.batch.draw(pass, 0, 0)
    screen.batch.end
    bfPass2.end
    pass = bfPass2.getColorBufferTexture
    val reg: TextureRegion = new TextureRegion(pass)
    if (arguments.length > 1 && arguments(1).isInstanceOf[FrameBuffer]) ((arguments(1)).asInstanceOf[FrameBuffer]).begin
    screen.batch.setShader(null)
    screen.batch.begin
    screen.batch.draw(reg, 0, 0)
    screen.batch.end
    if (arguments.length > 1 && arguments(1).isInstanceOf[FrameBuffer]) ((arguments(1)).asInstanceOf[FrameBuffer]).end
    screen.batch.enableBlending
  }
}