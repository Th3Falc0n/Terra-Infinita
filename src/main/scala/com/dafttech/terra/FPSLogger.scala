package com.dafttech.terra

class FPSLogger {
  private var lastTimeNano: Long = System.nanoTime
  private var avgTime: Float = 0
  private var avgFrames: Float = 0
  private var frameTime: Float = 0
  private var frameFrames: Float = 0
  private var outputActive: Boolean = true

  def tick: Float = {
    val tDif: Long = System.nanoTime - lastTimeNano
    lastTimeNano = System.nanoTime
    val tDifF: Float = tDif / 1000000000f
    var avgSPF: Float = 0
    if (avgTime > 0) {
      avgSPF = (avgTime / avgFrames)
    }
    avgTime /= 1f + avgSPF
    avgFrames /= 1f + avgSPF
    avgTime += tDifF
    avgFrames += 1
    frameTime += tDifF
    frameFrames += 1
    if (frameTime > 0.5f) {
      if (outputActive) System.out.println(String.format(
        "FPSCounter: t=%.3f F=%d F/t=%.1f avgF/t=%.1f",
        frameTime.asInstanceOf[AnyRef],
        frameFrames.toInt.asInstanceOf[AnyRef],
        (frameFrames / frameTime).asInstanceOf[AnyRef],
        (avgFrames / avgTime).asInstanceOf[AnyRef]))
      frameTime = 0
      frameFrames = 0
    }
    return tDifF
  }

  def disableOutput {
    outputActive = false
  }
}