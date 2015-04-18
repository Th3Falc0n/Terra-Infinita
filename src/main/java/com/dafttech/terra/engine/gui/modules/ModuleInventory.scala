package com.dafttech.terra.engine.gui.modules

import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.MouseSlot
import com.dafttech.terra.engine.gui.anchors.{AnchorCenterX, AnchorLeft, AnchorTop, GUIAnchorSet}
import com.dafttech.terra.engine.gui.containers.{ContainerBlock, ContainerList}
import com.dafttech.terra.engine.gui.elements.{ElementLabel, ElementSlotInventory}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.items.inventories.Inventory
import org.lolhens.eventmanager.{Event, EventListener}

class ModuleInventory extends GUIModule {
  private[modules] var invList: ContainerList = null
  private[modules] var inv: Inventory = null
  private[modules] var index: Int = 0

  def this(i: Inventory) {
    this()
    inv = i
  }

  @EventListener(Array("SCROLL")) def onScroll(event: Event) {
    if (invList.mouseHover && invList.isInActiveHierarchy) {
      val i: Int = event.in.get(0, classOf[Integer])
      index += i
      if (index > inv.getList.size - 5) {
        index = inv.getList.size - 5
      }
      if (index < 0) {
        index = 0
      }
      event.cancel
    }
  }

  override def update(delta: Float) {
    super.update(delta)
    invList.clearObjects
    {
      var n: Int = 0
      while (n < 5) {
        {
          if (index + n < inv.getList.size) {
            val slot: ElementSlotInventory = new ElementSlotInventory(new Vector2, inv)
            slot.assignStack(inv.getList.get(index + n))
            invList.addObject(slot)
          }
        }
        ({
          n += 1; n - 1
        })
      }
    }
    invList.addObject(new ElementLabel(new Vector2, index + "-" + (index + 5) + " / " + inv.getList.size))
  }

  def create {
    Events.EVENTMANAGER.registerEventListener(this)
    container = new ContainerBlock(new Vector2, new Vector2(312, 200))
    val set: GUIAnchorSet = new GUIAnchorSet
    set.addAnchor(new AnchorCenterX)
    container.assignAnchorSet(set)
    var invLabel: ElementLabel = null
    invLabel = new ElementLabel(new Vector2, "Inventory:")
    invLabel.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)).addAnchor(new AnchorTop(0)))
    container.addObject(invLabel)
    invList = new ContainerList((new Vector2, new Vector2(500, 200))) {
      override def onClick(button: Int) {
        super.onClick(button)
        if (MouseSlot.getAssignedStack != null) {
          inv.add(MouseSlot.popAssignedStack)
        }
      }
    }
    invList.assignAnchorSet(new GUIAnchorSet().addAnchor(new AnchorLeft(0)).addAnchor(new AnchorTop(0)))
    container.addObject(invList)
  }
}