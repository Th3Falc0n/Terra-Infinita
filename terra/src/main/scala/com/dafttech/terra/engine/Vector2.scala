package com.dafttech.terra.engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle

case class Vector2(x: Double, y: Double) {
  self =>

  object Vector2 extends Vector2Obj {
    override def apply(x: Double, y: Double): Vector2 =
      if (x == self.x && y == self.y) self else super.apply(x, y)
  }

  def +(vec: Vector2): Vector2 = if (isNull) vec else Vector2(x + vec.x, y + vec.y)

  def -(vec: Vector2): Vector2 = Vector2(x - vec.x, y - vec.y)

  def *(vec: Vector2): Vector2 = if (isOne) vec else Vector2(x * vec.x, y * vec.y)

  def /(vec: Vector2): Vector2 = Vector2(x / vec.x, y / vec.y)

  def +(value: Double): Vector2 = Vector2(x + value, y + value)

  def -(value: Double): Vector2 = Vector2(x - value, y - value)

  def *(value: Double): Vector2 = Vector2(x * value, y * value)

  def /(value: Double): Vector2 = Vector2(x / value, y / value)

  lazy val unary_- : Vector2 = Vector2(-x, -y)

  def withX(x: Double): Vector2 = Vector2(x, y)

  def withY(y: Double): Vector2 = Vector2(x, y)

  def mapX(f: Double => Double): Vector2 = Vector2(f(x), y)

  def mapY(f: Double => Double): Vector2 = Vector2(x, f(y))

  def isNull: Boolean = x == 0 && y == 0

  def isOne: Boolean = x == 1 && y == 1


  lazy val rotation: Double = {
    val angle: Double = Math.atan2(y, x) * 57.295776
    if (angle < 0.0F) angle + 360.0F else angle
  }

  def rotate(angle: Double): Vector2 = ???

  def withRotation(angle: Double): Vector2 = ???

  lazy val `length²`: Double = x * x + y * y

  lazy val length: Double = Math.sqrt(`length²`)

  lazy val normalized: Vector2 = self / length


  def rectangleTo(vec: Vector2): Rectangle = new Rectangle(x.toFloat, y.toFloat, vec.x.toFloat, vec.y.toFloat)
}

trait Vector2Obj {
  def apply(x: Double, y: Double): Vector2 =
    if (x == 0 && y == 0) Null
    else if (x == 1 && y == 1) One
    else new Vector2(x, y)

  def x(x: Double) = Vector2(x, 0)

  def y(y: Double) = Vector2(0, y)


  val Null = Vector2(0, 0)

  val One = Vector2(1, 1)


  @deprecated
  def mousePos = Vector2(Gdx.input.getX, Gdx.input.getY)
}

object Vector2 extends Vector2Obj
