package com.dafttech.terra.engine.gui.modules

import com.badlogic.gdx.graphics.Color
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.anchors._
import com.dafttech.terra.engine.gui.containers.ContainerBlock
import com.dafttech.terra.engine.gui.elements.{ElementBar, ElementLabel, ElementSlot}
import com.dafttech.terra.game.Events
import org.lolhens.eventmanager.{Event, EventListener}

class ModuleHUDBottom extends GUIModule {
  var slots: Array[ElementSlot] = new Array[ElementSlot](8)
  private var activeSlot: Int = 0
  var healthBar: ElementBar = null
  var apBar: ElementBar = null

  @EventListener(value = Array("SCROLL"), priority = -1) def onScroll(event: Event) {
    val prev: Int = activeSlot
    val dir: Int = event.in.get(0, classOf[Integer])
    activeSlot += dir
    if (activeSlot < 0) activeSlot = 7
    if (activeSlot > 7) activeSlot = 0
    slots(prev).active = false
    slots(activeSlot).active = true
  }

  def getActiveSlot: ElementSlot = {
    return slots(activeSlot)
  }

  def create(): Unit = {
    Events.EVENTMANAGER.registerEventListener(this)
    container = new ContainerBlock(Vector2.Null, new Vector2(312, 80))
    val set: GUIAnchorSet = new GUIAnchorSet
    set.addAnchor(new AnchorCenterX)
    set.addAnchor(new AnchorBottom(0.01f))
    container.assignAnchorSet(set)

    var i = 0
    while (i < 8) {
      slots(i) = new ElementSlot(new Vector2(i * 40, 0))
      slots(i).assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorBottom(0.01f)))
      container.addObject(slots(i))

      i += 1
    }

    slots(0).active = true
    healthBar = new ElementBar(new Vector2(0, 16), Color.RED, 100)
    apBar = new ElementBar(new Vector2(0, 16), Color.BLUE, 100)
    healthBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)))
    apBar.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)))
    var healthLabel: ElementLabel = null
    var apLabel: ElementLabel = null
    healthLabel = new ElementLabel(Vector2.Null, "HP")
    apLabel = new ElementLabel(Vector2.Null, "AP")
    healthLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)))
    apLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorRight(0)))
    container.addObject(healthLabel)
    container.addObject(apLabel)
    container.addObject(healthBar)
    container.addObject(apBar)
  }
}