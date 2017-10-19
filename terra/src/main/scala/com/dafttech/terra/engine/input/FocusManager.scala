package com.dafttech.terra.engine.input

object FocusManager {
  private[input] var typeFocus: IFocusableTyping = null

  def acquireTypeFocus(listener: IFocusableTyping): Boolean = {
    if (typeFocus != null) return false
    typeFocus = listener
    true
  }

  def releaseTypeFocus(listener: IFocusableTyping): Boolean = {
    if (typeFocus ne listener) return false
    typeFocus = null
    true
  }

  def hasTypeFocus(listener: IFocusableTyping): Boolean = typeFocus eq listener

  def typeFocusAssigned: Boolean = typeFocus != null
}