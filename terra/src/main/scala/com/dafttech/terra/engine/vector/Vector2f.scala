package com.dafttech.terra.engine.vector

final case class Vector2f private(override val x: Float,
                                  override val y: Float) extends Vector2[Float](x, y) {
  override type Self = Vector2f

  override def Vector2(x: Float, y: Float): Vector2f =
    if (x == this.x && y == this.y) this
    else Vector2f(x, y)

  override def isZero: Boolean = x == 0 && y == 0
  override def isOne: Boolean = x == 1 && y == 1

  override def unary_- : Vector2f = Vector2f(-x, -y)

  override def +(x: Float, y: Float): Vector2f = if (x == 0 && y == 0) this else Vector2f(this.x + x, this.y + y)
  override def -(x: Float, y: Float): Vector2f = if (x == 0 && y == 0) this else Vector2f(this.x - x, this.y - y)
  override def *(x: Float, y: Float): Vector2f = if (x == 1 && y == 1) this else Vector2f(this.x * x, this.y * y)
  override def /(x: Float, y: Float): Vector2f = if (x == 1 && y == 1) this else Vector2f(this.x / x, this.y / y)

  override def `length²`: Float = x * x + y * y
  override def length: Float = Math.sqrt(`length²`).toFloat
}

object Vector2f {
  val Zero = new Vector2f(0, 0)
  val One = new Vector2f(1, 1)

  val X = new Vector2f(1, 0)
  val Y = new Vector2f(0, 1)

  def apply(x: Float, y: Float): Vector2f =
    if (x == 0 && y == 0) Zero
    else if (x == 1 && y == 1) One
    else new Vector2f(x, y)
}
