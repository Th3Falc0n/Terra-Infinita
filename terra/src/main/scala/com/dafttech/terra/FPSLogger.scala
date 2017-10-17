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
      if (outputActive) println(f"FPSCounter: t=$frameTime%.3f F=${frameFrames.toInt}%d F/t=${frameFrames / frameTime}%.1f avgF/t=${avgFrames / avgTime}%.1f")
      frameTime = 0
      frameFrames = 0
    }
    return tDifF
  }

  def disableOutput {
    outputActive = false
  }
}