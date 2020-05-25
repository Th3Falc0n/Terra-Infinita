package com.dafttech.terra.engine.input

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}

object MousePos {
  def vector2i: Vector2i = Vector2i(Gdx.input.getX, Gdx.input.getY)

  def vector2d: Vector2d = Vector2d(Gdx.input.getX, Gdx.input.getY)
}
