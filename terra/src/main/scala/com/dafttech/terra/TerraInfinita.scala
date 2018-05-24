package com.dafttech.terra

import java.util.Random

import com.badlogic.gdx.{ApplicationListener, Game, Gdx}
import com.dafttech.terra.engine.Vector2
import com.dafttech.terra.engine.gui.{MouseSlot, Tooltip}
import com.dafttech.terra.engine.input.InputHandler
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.{Events, ScreenIngame, ScreenPauseMenu}
import com.dafttech.terra.resources.Resources
import org.lwjgl.opengl.Display

object TerraInfinita extends Game with ApplicationListener {
  var rnd: Random = new Random()

  private[terra] val fpsLogger: FPSLogger = new FPSLogger()
  var screenIngame: ScreenIngame = null
  var screenPause: ScreenPauseMenu = null
  var world: World = null
  private[terra] var wasFocused: Boolean = false

  def isFocused: Boolean = {
    return Display.isActive
  }

  def create: Unit = {
    Gdx.app.log(Thread.currentThread.getName, "Creating game...")
    Events.init()
    Events.EVENTMANAGER.callSync(Events.EVENT_INITPRE, this)
    Resources.init()
    InputHandler.init
    Tooltip.init
    MouseSlot.init
    world = new World(new Vector2(2000, 1000))
    screenIngame = new ScreenIngame(world)
    screenPause = new ScreenPauseMenu(world)
    setScreen(screenPause)
    Events.EVENTMANAGER.callSync(Events.EVENT_INITPOST, this)
  }

  override def render(): Unit = {
    if (!isFocused && wasFocused) {
      setScreen(screenPause)
      wasFocused = false
    }
    if (isFocused && !wasFocused) {
      wasFocused = true
    }
    super.render()
    fpsLogger.tick
  }

  override def resize(arg0: Int, arg1: Int) {
    super.resize(arg0, arg1)
    Gdx.graphics.setDisplayMode(arg0, arg1, false)
    this.getScreen.resize(arg0, arg1)
    Events.EVENTMANAGER.callSync(Events.EVENT_WINRESIZE, this)
  }

  override def pause(): Unit = {
    Events.EVENTMANAGER.callSync(Events.EVENT_WINPAUSE, this)
  }

  override def resume(): Unit = {
    Events.EVENTMANAGER.callSync(Events.EVENT_WINRESUME, this)
  }

  override def dispose(): Unit = {
    Events.EVENTMANAGER.callSync(Events.EVENT_WINDISPOSE, this)
  }
}