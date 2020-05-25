package com.dafttech.terra.engine.vector

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.dafttech.terra.resources.Options

final case class Vector2d private(override val x: Double,
                                  override val y: Double) extends Vector2[Double](x, y) {
  override type Self = Vector2d

  override def Vector2(x: Double, y: Double): Vector2d =
    if (x == this.x && y == this.y) this
    else Vector2d(x, y)

  override def isZero: Boolean = x == 0 && y == 0

  override def isOne: Boolean = x == 1 && y == 1

  override def unary_- : Vector2d = Vector2d(-x, -y)

  override def +(x: Double, y: Double): Vector2d = if (x == 0 && y == 0) this else Vector2d(this.x + x, this.y + y)

  override def -(x: Double, y: Double): Vector2d = if (x == 0 && y == 0) this else Vector2d(this.x - x, this.y - y)

  override def *(x: Double, y: Double): Vector2d = if (x == 1 && y == 1) this else Vector2d(this.x * x, this.y * y)

  override def /(x: Double, y: Double): Vector2d = if (x == 1 && y == 1) this else Vector2d(this.x / x, this.y / y)

  override def `length²`: Double = x * x + y * y

  override def length: Double = Math.sqrt(`length²`)

  def rotation: Double = {
    val angle: Double = Math.atan2(y, x) * 57.295776
    if (angle < 0.0F) angle + 360.0F else angle
  }

  def rotate(angle: Double): Vector2d = ???

  def withRotation(angle: Double): Vector2d = ???

  def rectangleTo(vec: Vector2d): Rectangle = new Rectangle(x.toFloat, y.toFloat, vec.x.toFloat, vec.y.toFloat)


  def toWorldPosition: Vector2i = {
    val ox: Int = if (x.toInt >= 0) x.toInt / Options.BLOCK_SIZE else (x.toInt + 1) / Options.BLOCK_SIZE - 1
    val oy: Int = if (y.toInt >= 0) y.toInt / Options.BLOCK_SIZE else (y.toInt + 1) / Options.BLOCK_SIZE - 1
    Vector2i(ox, oy)
  }

  def toRenderPosition(relateTo: Vector2d): Vector2d =
    new Vector2d(x - relateTo.x + Gdx.graphics.getWidth / 2.0f, y - relateTo.y + Gdx.graphics.getHeight / 2.0f)

  def toVector2i: Vector2i =
    Vector2i(if (x.toInt >= 0) x.toInt else x.toInt - 1, if (y.toInt >= 0) y.toInt else y.toInt - 1)
}

object Vector2d {
  val Zero = new Vector2d(0, 0)
  val One = new Vector2d(1, 1)

  val X = new Vector2d(1, 0)
  val Y = new Vector2d(0, 1)

  def apply(x: Double, y: Double): Vector2d =
    if (x == 0 && y == 0) Zero
    else if (x == 1 && y == 1) One
    else new Vector2d(x, y)
}
