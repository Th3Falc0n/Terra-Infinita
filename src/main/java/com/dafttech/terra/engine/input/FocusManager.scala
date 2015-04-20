package com.dafttech.terra.engine.input

object FocusManager {
  private[input] var typeFocus: IFocusableTyping = null

  def acquireTypeFocus(listener: IFocusableTyping): Boolean = {
    if (typeFocus != null) return false
    typeFocus = listener
    return true
  }

  def releaseTypeFocus(listener: IFocusableTyping): Boolean = {
    if (typeFocus ne listener) return false
    typeFocus = null
    return true
  }

  def hasTypeFocus(listener: IFocusableTyping): Boolean = {
    return typeFocus eq listener
  }

  def typeFocusAssigned: Boolean = {
    return typeFocus != null
  }
}