package com.dafttech.terra.engine.gui.elements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.math.Matrix4
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.resources.Resources
import scala.concurrent.duration._

object ElementBar {
  private var ciBuffer: FrameBuffer = new FrameBuffer(Format.RGBA8888, 128, 16, false)
}

class ElementBar(p: Vector2, clr: Color, val maxValue: Float) extends GUIElement(p, Vector2(128, 16)) {
  private[elements] var bufferMatrix: Matrix4 = new Matrix4().setToOrtho(0, 128, 16, 0, 0, 1)
  var imageMask: TextureRegion = _
  var value: Float = .0f

  clr.a = 1
  import monix.execution.Scheduler.Implicits.global
  image = Resources.GUI.getImage("bar").runSyncUnsafe(5.seconds)
  imageMask = Resources.GUI.getImage("bar_mask").runSyncUnsafe(5.seconds)

  def setValue(v: Float) {
    value = v
  }

  override def draw(screen: AbstractScreen) {
    val p: Vector2 = getScreenPosition
    screen.batch.setShader(null)
    ElementBar.ciBuffer.begin()
    screen.batch.setProjectionMatrix(bufferMatrix)
    screen.shr.setProjectionMatrix(bufferMatrix)
    Gdx.graphics.getGL20.glClearColor(1, 1, 1, 0)
    Gdx.graphics.getGL20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT)
    screen.batch.disableBlending()
    screen.batch.begin()
    screen.batch.setColor(clr)
    screen.batch.draw(imageMask, 0, 0)
    screen.batch.end()
    screen.shr.setColor(0, 0, 0, 0)
    screen.shr.begin(ShapeType.Filled)
    val w: Float = 124f - (124f * (value / maxValue))
    screen.shr.rect(126 - w, 0, w, 16)
    screen.shr.end()
    ElementBar.ciBuffer.end()
    val tr: TextureRegion = new TextureRegion(ElementBar.ciBuffer.getColorBufferTexture)
    screen.batch.setProjectionMatrix(screen.projection)
    screen.shr.setProjectionMatrix(screen.projection)
    screen.batch.setColor(Color.WHITE)
    screen.batch.enableBlending()
    screen.batch.begin()
    screen.batch.draw(tr, p.x.toFloat, p.y.toFloat)
    screen.batch.draw(image, p.x.toFloat, p.y.toFloat)
    screen.batch.end()
  }
}