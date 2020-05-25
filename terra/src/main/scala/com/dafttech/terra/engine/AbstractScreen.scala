package com.dafttech.terra.engine

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.{Gdx, Screen}
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen
import com.dafttech.terra.engine.vector.Vector2d

abstract class AbstractScreen extends Screen {
  var projection: Matrix4 = new Matrix4().setToOrtho(0, Gdx.graphics.getWidth, Gdx.graphics.getHeight, 0, 0, 1)
  protected var guiContainerScreen: ContainerOnscreen = _

  def dispose(): Unit = ()

  def resize(arg0: Int, arg1: Int): Unit = {
    projection = new Matrix4().setToOrtho(0, Gdx.graphics.getWidth, Gdx.graphics.getHeight, 0, 0, 1)
    batch.setProjectionMatrix(projection)
    shr.setProjectionMatrix(projection)
    guiContainerScreen.size = Vector2d(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    guiContainerScreen.applyAllAssignedAnchorSets
  }

  def resume(): Unit = ()

  def show(): Unit = {
    projection = new Matrix4().setToOrtho(0, Gdx.graphics.getWidth, Gdx.graphics.getHeight, 0, 0, 1)
    batch.setProjectionMatrix(projection)
    shr.setProjectionMatrix(projection)
    guiContainerScreen.size = Vector2d(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    guiContainerScreen.applyAllAssignedAnchorSets
    guiContainerScreen.setActive(true)
  }

  def hide(): Unit = guiContainerScreen.setActive(false)

  def pause(): Unit = ()

  val batch: SpriteBatch = new SpriteBatch()
  val shr: ShapeRenderer = new ShapeRenderer()

  def render(delta: Float) {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
  }
}