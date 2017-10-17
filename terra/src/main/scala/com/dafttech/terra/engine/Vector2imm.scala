package com.dafttech.terra.engine

import com.badlogic.gdx.Gdx

case class Vector2imm(x: Double, y: Double) {

}

object Vector2imm {
  val empty = Vector2imm(0, 0)

  def x(value: Double) = Vector2imm(value, 0)

  def y(value: Double) = Vector2imm(0, value)

  @deprecated
  def mousePos = Vector2imm(Gdx.input.getX, Gdx.input.getY)
}
