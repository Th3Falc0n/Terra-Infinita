package com.dafttech.terra.engine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.resources.Options

case class Vector2(x: Double, y: Double) {
  self =>

  protected object Vector2 extends Vector2Obj {
    override def apply(x: Double, y: Double): Vector2 =
      if (x == self.x && y == self.y) self else super.apply(x, y)
  }

  def +(x: Double, y: Double): Vector2 = Vector2(this.x + x, this.y + y)

  def -(x: Double, y: Double): Vector2 = Vector2(this.x - x, this.y - y)

  def *(x: Double, y: Double): Vector2 = Vector2(this.x * x, this.y * y)

  def /(x: Double, y: Double): Vector2 = Vector2(this.x / x, this.y / y)

  def +(vec: Vector2): Vector2 = if (isNull) vec else self + (vec.x, vec.y)

  def -(vec: Vector2): Vector2 = self - (vec.x, vec.y)

  def *(vec: Vector2): Vector2 = if (isOne) vec else self * (vec.x, vec.y)

  def /(vec: Vector2): Vector2 = self / (vec.x, vec.y)

  def +(value: Double): Vector2 = self + (value, value)

  def -(value: Double): Vector2 = self - (value, value)

  def *(value: Double): Vector2 = self * (value, value)

  def /(value: Double): Vector2 = self / (value, value)

  lazy val unary_- : Vector2 = Vector2(-x, -y)

  def xFloat: Float = x.toFloat

  def yFloat: Float = y.toFloat

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


  def toWorldPosition: Vector2i = {
    val ox: Int = if (x.toInt >= 0) x.toInt / Options.BLOCK_SIZE else (x.toInt + 1) / Options.BLOCK_SIZE - 1
    val oy: Int = if (y.toInt >= 0) y.toInt / Options.BLOCK_SIZE else (y.toInt + 1) / Options.BLOCK_SIZE - 1
    new Vector2i(ox, oy)
  }

  def toRenderPosition(relateTo: Vector2): Vector2 =
    new Vector2(x - relateTo.x + Gdx.graphics.getWidth / 2.0f, y - relateTo.y + Gdx.graphics.getHeight / 2.0f)

  def toVector2i: Vector2i =
    new Vector2i(if (x.toInt >= 0) x.toInt else x.toInt - 1, if (y.toInt >= 0) y.toInt else y.toInt - 1)
}

trait Vector2Obj {
  def apply(x: Double, y: Double): Vector2 =
    if (x == 0 && y == 0) Null
    else if (x == 1 && y == 1) One
    else new Vector2(x, y)

  lazy val Null = new Vector2(0, 0)

  lazy val One = new Vector2(1, 1)


  @deprecated
  def mousePos = Vector2(Gdx.input.getX, Gdx.input.getY)
}

object Vector2 extends Vector2Obj
