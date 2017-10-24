package com.dafttech.terra.game.world.gen.calc

class PerlinNoise(val seed: Int,
                  val octaves: Float = 4,
                  val persistence: Float = 0.25f) {
  val smoothing = 4.0f

  def rawNoise(x: Float): Float = {
    val n = (x.toInt << 13) ^ x.toInt
    1.0f - ((n * (n * n * 15731 * seed + 789221 * seed) + 1376312589 * seed) & 0x7fffffff) / 1073741824.0f
  }

  def rawNoise2D(x: Float, y: Float): Float = rawNoise(x + y * 57)

  def smoothNoise(x: Float): Float = {
    val left = rawNoise(x - 1.0f)
    val right = rawNoise(x + 1.0f)
    (rawNoise(x) / 2.0f) + (left / smoothing) + (right / smoothing)
  }

  def smoothNoise2D(x: Float, y: Float): Float = {
    val corners = rawNoise2D(x - 1, y - 1) + rawNoise2D(x - 1, y + 1) + rawNoise2D(x + 1, y - 1) + rawNoise2D(x + 1, y + 1)
    val sides = rawNoise2D(x, y - 1) + rawNoise2D(x, y + 1) + rawNoise2D(x - 1, y) + rawNoise2D(x + 1, y)
    val center = rawNoise2D(x, y)
    (center / 4.0f) + (sides / 8.0f) + (corners / 16.0f)
  }

  def linearInterpolate(a: Float, b: Float, x: Float): Float = a * (1 - x) + b * x

  def cosineInterpolate(a: Float, b: Float, x: Float): Float = {
    val f = (1.0f - Math.cos(x * Math.PI).toFloat) / 2.0f
    a * (1 - f) + b * f
  }

  def interpolateNoise(x: Float): Float = cosineInterpolate(
    smoothNoise(Math.floor(x).toFloat),
    smoothNoise(Math.floor(x).toFloat + 1.0f),
    (x - Math.floor(x)).toFloat
  )

  def interpolateNoise2D(x: Float, y: Float): Float = {
    val a = cosineInterpolate(
      smoothNoise2D(Math.floor(x).toFloat, Math.floor(y).toFloat),
      smoothNoise2D(Math.floor(x).toFloat + 1.0f, Math.floor(y).toFloat),
      x - Math.floor(x).toFloat
    )

    val b = cosineInterpolate(
      smoothNoise2D(Math.floor(x).toFloat, Math.floor(y).toFloat + 1),
      smoothNoise2D(Math.floor(x).toFloat + 1, Math.floor(y).toFloat + 1),
      x - Math.floor(x).toFloat
    )

    cosineInterpolate(a, b, y - Math.floor(y).toFloat)
  }

  def perlinNoise(x: Float): Float = {
    for {
      i <- 0 until octaves.toInt
      frequency = Math.pow(2.0, i).toFloat
      amplitude = Math.pow(persistence, i).toFloat
    } yield
      interpolateNoise(x * frequency) * amplitude
  }.sum

  def perlinNoise2D(x: Float, y: Float): Float = {
    for {
      i <- 0 until octaves.toInt
      frequency = Math.pow(2.0, i).toFloat
      amplitude = Math.pow(persistence, i).toFloat
    } yield interpolateNoise2D(x * frequency, y * frequency) * amplitude
  }.sum
}