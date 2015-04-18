package com.dafttech.terra.game

import com.badlogic.gdx.Gdx
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.resources.Options.BLOCK_SIZE
import org.lolhens.eventmanager.{Event, EventManager, EventType, ListenerContainer}
import org.lolhens.storage.tuple.Tuple

object Events {
  val EVENTMANAGER: EventManager = new EventManager
  val EVENT_INITPRE: EventType = new EventType("INITPRE", EVENTMANAGER)
  val EVENT_INITPOST: EventType = new EventType("INITPOST", EVENTMANAGER)
  val EVENT_WINRESIZE: EventType = new EventType("WINRESIZE", EVENTMANAGER)
  val EVENT_WINPAUSE: EventType = new EventType("WINPAUSE", EVENTMANAGER)
  val EVENT_WINRESUME: EventType = new EventType("WINRESUME", EVENTMANAGER)
  val EVENT_WINDISPOSE: EventType = new EventType("WINDISPOSE", EVENTMANAGER)
  val EVENT_BLOCKCHANGE: EventType = new EventType("BLOCKCHANGE", EVENTMANAGER)
  val EVENT_CHATCOMMAND: EventType = new EventType("CHATCOMMAND", EVENTMANAGER)
  val EVENT_KEYDOWN: EventType = new EventType("KEYDOWN", EVENTMANAGER) {
    protected override def isFiltered(event: Event, filter: Tuple, eventListener: ListenerContainer): Boolean = {
      return filter.get(0, classOf[String]) == event.in.get(0, classOf[String])
    }
  }
  val EVENT_KEYUP: EventType = new EventType("KEYUP", EVENTMANAGER) {
    protected override def isFiltered(event: Event, filter: Tuple, eventListener: ListenerContainer): Boolean = {
      return filter.get(0, classOf[String]) == event.in.get(0, classOf[String])
    }
  }
  val EVENT_MOUSEDOWN: EventType = new EventType("MOUSEDOWN", EVENTMANAGER)
  val EVENT_MOUSEUP: EventType = new EventType("MOUSEUP", EVENTMANAGER)
  val EVENT_MOUSEMOVE: EventType = new EventType("MOUSEMOVE", EVENTMANAGER)
  val EVENT_SCROLL: EventType = new EventType("SCROLL", EVENTMANAGER)
  val EVENT_BLOCKUPDATE: EventType = new EventType("BLOCKUPDATE", EVENTMANAGER) {
    protected override def onEvent(event: Event) {
      val tile: Tile = event.in.get(0, classOf[World]).getTile(event.in.get(1, classOf[Integer]), event.in.get(2, classOf[Integer]))
      if (tile != null) tile.update(event.in.get(0, classOf[World]), 0)
    }
  }
  val EVENT_WORLDTICK: EventType = new EventType("GAMETICK", EVENTMANAGER) {
    protected override def onEvent(event: Event) {
      val world: World = event.in.get(0, classOf[World])
      val sx: Int = 25 + Gdx.graphics.getWidth / BLOCK_SIZE / 2
      val sy: Int = 25 + Gdx.graphics.getHeight / BLOCK_SIZE / 2
      var tile: Tile = null
      var x: Int = world.localPlayer.getPosition.x.toInt / BLOCK_SIZE - sx
      while (x < world.localPlayer.getPosition.x.toInt / BLOCK_SIZE + sx) {
        {
          {
            var y: Int = world.localPlayer.getPosition.y.toInt / BLOCK_SIZE - sy
            while (y < world.localPlayer.getPosition.y.toInt / BLOCK_SIZE + sy) {
              {
                tile = world.getTile(x, y)
                if (tile != null) tile.tick(world, event.in.get(1, classOf[Float]))
              }
              ({
                y += 1;
                y - 1
              })
            }
          }
        }
        ({
          x += 1;
          x - 1
        })
      }
    }
  }

  def init {
  }
}