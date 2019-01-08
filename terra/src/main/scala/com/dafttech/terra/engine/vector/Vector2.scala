package com.dafttech.terra.engine.vector

abstract class Vector2[@specialized(Int, Long, Float, Double) T] protected(val x: T,
                                                                           val y: T) {
  type Self <: Vector2[T]

  def Vector2(x: T, y: T): Self

  def withX(x: T): Self = Vector2(x, y)
  def withY(y: T): Self = Vector2(x, y)

  def mapX(f: T => T): Self = withX(f(x))
  def mapY(f: T => T): Self = withY(f(y))

  def isZero: Boolean
  def isOne: Boolean

  def unary_- : Self

  def +(x: T, y: T): Self
  def -(x: T, y: T): Self
  def *(x: T, y: T): Self
  def /(x: T, y: T): Self

  def +(vec: Self): Self = if (isZero) vec else this + (vec.x, vec.y)
  def -(vec: Self): Self = this - (vec.x, vec.y)
  def *(vec: Self): Self = if (isOne) vec else this * (vec.x, vec.y)
  def /(vec: Self): Self = this / (vec.x, vec.y)

  def +(value: T): Self = this + (value, value)
  def -(value: T): Self = this - (value, value)
  def *(value: T): Self = this * (value, value)
  def /(value: T): Self = this / (value, value)

  def `length²`: T
  def length: T
  def normalized: Self = this / length
}
