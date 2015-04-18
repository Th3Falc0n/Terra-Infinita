package com.dafttech.terra.engine.gui.containers

import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.engine.gui.GUIObject
import org.lolhens.eventmanager.{Event, EventListener}

import scala.collection.mutable

abstract class GUIContainer(p: Vector2, s: Vector2) extends GUIObject(p, s) {
  private var objects: mutable.ArrayBuffer[GUIObject] = new mutable.ArrayBuffer[GUIObject]

  def draw(screen: AbstractScreen) {
    for (o <- objects) {
      o.draw(screen)
    }
  }

  def clearObjects = objects.clear
  def containsObject(`object`: GUIObject): Boolean = objects.contains(`object`)

  def addObject(gObj: GUIObject) {
    objects += gObj
    gObj.setContainer(this)
    gObj.applyAssignedAnchorSet
  }

  def removeObject(gObj: GUIObject) {
    objects -= gObj
  }

  override def update(delta: Float) {
    super.update(delta)
    for (e <- objects) {
      e.update(delta)
    }
  }

  def applyAllAssignedAnchorSets {
    applyAssignedAnchorSet
    for (o <- objects) {
      if (o.isInstanceOf[GUIContainer]) {
        (o.asInstanceOf[GUIContainer]).applyAllAssignedAnchorSets
      }
      o.applyAssignedAnchorSet
    }
  }

  @EventListener(Array("MOUSEMOVE")) def onEventMouseMove(event: Event) {
    if (isInActiveHierarchy || this.providesActiveHierarchy) {
      for (e <- objects) {
        val x: Int = event.in.get(1, classOf[Integer])
        val y: Int = event.in.get(2, classOf[Integer])
        val p: Vector2 = e.getScreenPosition
        if (e.size != null) {
          if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y && !e.mouseHover) {
            e.onMouseIn
            e.mouseHover = true
          }
          else if (!(x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) && e.mouseHover) {
            e.onMouseOut
            e.mouseHover = false
          }
        }
        else {
          System.out.println("Size null in " + e)
        }
      }
    }
  }

  @EventListener(Array("MOUSEDOWN")) def onEventMouseDown(event: Event) {
    if (isInActiveHierarchy || this.providesActiveHierarchy) {
      for (e <- objects) {
        val button: Int = event.in.get(0, classOf[Integer])
        val x: Int = event.in.get(1, classOf[Integer])
        val y: Int = event.in.get(2, classOf[Integer])
        val p: Vector2 = e.getScreenPosition
        if (e.size != null) {
          if (x > p.x && x < p.x + e.size.x && y > p.y && y < p.y + e.size.y) {
            e.onClick(button)
          }
        }
        else {
          System.out.println("Size null in " + e)
        }
      }
    }
  }
}