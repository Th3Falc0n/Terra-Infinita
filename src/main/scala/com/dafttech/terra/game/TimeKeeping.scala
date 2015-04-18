package com.dafttech.terra.game

object TimeKeeping {
  private[game] var lastTime: Long = 0L
  private[game] var currentTime: Long = 0L
  private[game] var lastMsg: String = null

  def timeKeeping(msg: String) {
    val temp: Long = currentTime
    currentTime = System.currentTimeMillis
    if (currentTime - lastTime > 25) {
      System.out.println("Time from " + lastMsg + " to " + msg + ": " + (currentTime - lastTime))
    }
    lastMsg = msg
    lastTime = temp
  }
}