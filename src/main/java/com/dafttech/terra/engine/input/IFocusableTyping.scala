package com.dafttech.terra.engine.input

trait IFocusableTyping {
  def onKeyTyped(c: Char)

  def onKeyDown(i: Int)
}