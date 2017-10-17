package com.dafttech.terra.engine.gui.modules

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.anchors.{AnchorBottom, AnchorLeft, GUIAnchorSet}
import com.dafttech.terra.engine.gui.containers.{ContainerBlock, ContainerList}
import com.dafttech.terra.engine.gui.elements.{ElementInputLabel, ElementLabel}
import com.dafttech.terra.engine.input.handlers.IStringInputHandler
import com.dafttech.terra.game.Events
import org.lolhens.eventmanager.EventListener

object ModuleChat {
  @EventListener.Filter("filterOnChatKeyUsed") var chatKey: String = "CHAT"
}

class ModuleChat extends GUIModule with IStringInputHandler {
  var messageList: ContainerList = null
  var inputLabel: ElementInputLabel = null

  def create {
    Events.EVENTMANAGER.registerEventListener(this)
    container = new ContainerBlock(Vector2.Null, new Vector2(400, 280))
    val set: GUIAnchorSet = new GUIAnchorSet
    set.addAnchor(new AnchorLeft(0.01f))
    set.addAnchor(new AnchorBottom(0.01f))
    container.assignAnchorSet(set)
    inputLabel = new ElementInputLabel(new Vector2(10, 250), this)
    messageList = new ContainerList(new Vector2(10, 10), new Vector2(380, 230))
    container.addObject(messageList)
    container.addObject(inputLabel)
  }

  def addMessage(msg: String) {
    messageList.addObject(new ElementLabel(Vector2.Null, msg))
  }

  @EventListener(value = Array("KEYDOWN"), filter = Array("filterOnChatKeyUsed")) def onChatKeyUsed {
    inputLabel.beginStringInput
  }

  def handleInput(str: String) {
    addMessage(str)
  }
}