package com.dafttech.terra.engine.input

import java.util.{HashMap, Map}

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.{Gdx, InputProcessor}
import com.dafttech.terra.game.Events

object InputHandler extends InputProcessor {
  case class Key(id: Int) extends AnyVal {
    def isDown: Boolean = Option(keyDown.get(id)).getOrElse(false)
    def isUp: Boolean = !isDown
  }
  
  case class KeyBinding(name: String, defaultKey: Key) {
    var key: Key = defaultKey
    
    def isDown: Boolean = key.isDown
    def isUp: Boolean = key.isUp
  }
  
  private[input] var keyNames: Map[String, Integer] = new HashMap[String, Integer]
  private[input] var keyIds: Map[Integer, String] = new HashMap[Integer, String]
  private[input] var keyDown: Map[Integer, Boolean] = new HashMap[Integer, Boolean]

  def init {
    Gdx.input.setInputProcessor(this)
    registerKey(Keys.ESCAPE, "PAUSE")
    registerKey(Keys.W, "UP")
    registerKey(Keys.A, "LEFT")
    registerKey(Keys.S, "DOWN")
    registerKey(Keys.D, "RIGHT")
    registerKey(Keys.N, "WAVESLEFT")
    registerKey(Keys.M, "WAVESRIGHT")
    registerKey(Keys.E, "INVENTORY")
    registerKey(Keys.C, "CRAFTING")
    registerKey(Keys.SPACE, "JUMP")
    registerKey(Keys.ENTER, "CHAT")
  }

  def isKeyDown(name: String): Boolean = {
    if (!keyDown.containsKey(getKeyID(name))) return false
    keyDown.get(getKeyID(name))
  }

  def isKeyRegistered(id: Int): Boolean = {
    keyIds.containsKey(id)
  }

  def isKeyRegistered(name: String): Boolean = {
    keyNames.containsKey(name)
  }

  def getKeyID(name: String): Int = {
    if (!keyNames.containsKey(name)) throw new IllegalArgumentException("Key not registered: " + name)
    keyNames.get(name)
  }

  def getKeyName(id: Int): String = {
    if (!keyIds.containsKey(id)) throw new IllegalArgumentException("Key not registered: " + id)
    keyIds.get(id)
  }

  def registerKey(key: Int, name: String) {
    if (isKeyRegistered(key) || isKeyRegistered(name)) {
      Gdx.app.log("InputHandler", "Multiple Key Registration: " + key)
      return
    }
    keyNames.put(name, key)
    keyIds.put(key, name)
  }

  def keyDown(i: Int): Boolean = {
    if (FocusManager.typeFocusAssigned) {
      FocusManager.typeFocus.onKeyDown(i)
      return true
    }
    keyDown.put(i, true)
    if (isKeyRegistered(i)) Events.EVENTMANAGER.callSync(Events.EVENT_KEYDOWN, getKeyName(i))
    true
  }

  def keyTyped(c: Char): Boolean = {
    if (FocusManager.typeFocusAssigned) {
      FocusManager.typeFocus.onKeyTyped(c)
      return true
    }
    false
  }

  def keyUp(i: Int): Boolean = {
    if (FocusManager.typeFocusAssigned) return false
    keyDown.put(i, false)
    if (isKeyRegistered(i)) Events.EVENTMANAGER.callSync(Events.EVENT_KEYUP, getKeyName(i))
    true
  }

  def mouseMoved(x: Int, y: Int): Boolean = {
    Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEMOVE, (-1).asInstanceOf[AnyRef], x.asInstanceOf[AnyRef], y.asInstanceOf[AnyRef])
    true
  }

  def scrolled(arg0: Int): Boolean = {
    Events.EVENTMANAGER.callSync(Events.EVENT_SCROLL, arg0.asInstanceOf[AnyRef])
    false
  }

  def touchDown(x: Int, y: Int, p: Int, b: Int): Boolean = {
    Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEDOWN, b.asInstanceOf[AnyRef], x.asInstanceOf[AnyRef], y.asInstanceOf[AnyRef])
    true
  }

  def touchDragged(x: Int, y: Int, p: Int): Boolean = {
    false
  }

  def touchUp(x: Int, y: Int, p: Int, b: Int): Boolean = {
    Events.EVENTMANAGER.callSync(Events.EVENT_MOUSEUP, b.asInstanceOf[AnyRef], x.asInstanceOf[AnyRef], y.asInstanceOf[AnyRef])
    true
  }
}