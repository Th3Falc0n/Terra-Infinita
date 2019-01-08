package com.dafttech.terra.engine.vector

final case class Vector2l private(override val x: Long,
                                  override val y: Long) extends Vector2[Long](x, y) {
  override type Self = Vector2l

  override def Vector2(x: Long, y: Long): Vector2l =
    if (x == this.x && y == this.y) this
    else Vector2l(x, y)

  override def isZero: Boolean = x == 0 && y == 0
  override def isOne: Boolean = x == 1 && y == 1

  override def unary_- : Vector2l = Vector2l(-x, -y)

  override def +(x: Long, y: Long): Vector2l = if (x == 0 && y == 0) this else Vector2l(this.x + x, this.y + y)
  override def -(x: Long, y: Long): Vector2l = if (x == 0 && y == 0) this else Vector2l(this.x - x, this.y - y)
  override def *(x: Long, y: Long): Vector2l = if (x == 1 && y == 1) this else Vector2l(this.x * x, this.y * y)
  override def /(x: Long, y: Long): Vector2l = if (x == 1 && y == 1) this else Vector2l(this.x / x, this.y / y)

  override def `length²`: Long = x * x + y * y
  override def length: Long = Math.sqrt(`length²`).toLong
}

object Vector2l {
  val Zero = new Vector2l(0, 0)
  val One = new Vector2l(1, 1)

  val X = new Vector2l(1, 0)
  val Y = new Vector2l(0, 1)

  def apply(x: Long, y: Long): Vector2l =
    if (x == 0 && y == 0) Zero
    else if (x == 1 && y == 1) One
    else new Vector2l(x, y)
}
