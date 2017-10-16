package com.dafttech.terra.game

import com.badlogic.gdx.Gdx
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.gui.anchors._
import com.dafttech.terra.engine.gui.containers.{ContainerList, ContainerOnscreen}
import com.dafttech.terra.engine.gui.elements.ElementButton
import com.dafttech.terra.engine.gui.modules.ModuleChat
import com.dafttech.terra.engine.gui.{MouseSlot, Tooltip}
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World
import org.lolhens.eventmanager.{Event, EventListener}

class ScreenIngame extends AbstractScreen {
  private[game] var localWorld: World = null
  private[game] var exitButton: ElementButton = null
  private[game] var chat: ModuleChat = null
  private[game] var midContainer: ContainerList = null

  def this(w: World) {
    this()
    Events.EVENTMANAGER.registerEventListener(this)
    localWorld = w
    guiContainerScreen = new ContainerOnscreen
    exitButton = new ElementButton(new Vector2, "Exit") {
      def actionPerformed(button: Int) {
        Gdx.app.exit
      }
    }
    val exitButtonSet: GUIAnchorSet = new GUIAnchorSet
    exitButtonSet.addAnchor(new AnchorRight(0.01f))
    exitButtonSet.addAnchor(new AnchorTop(0.01f))
    exitButton.assignAnchorSet(exitButtonSet)
    exitButton.setTooltip("Close the game")
    //chat = new ModuleChat
    //chat.create
    midContainer = new ContainerList(new Vector2, new Vector2(320, 800), 45)
    midContainer.assignAnchorSet(new GUIAnchorSet(new AnchorBottom(0.15f), new AnchorCenterX))
    //guiContainerScreen.addObject(chat.getContainer) TODO
    guiContainerScreen.addObject(localWorld.localPlayer.hudBottom.getContainer)
    guiContainerScreen.addObject(exitButton)
    guiContainerScreen.addObject(Tooltip.getLabel)
    guiContainerScreen.addObject(MouseSlot.getRenderSlot)
    guiContainerScreen.addObject(midContainer)
    guiContainerScreen.applyAllAssignedAnchorSets
  }

  @EventListener(value = Array("KEYDOWN")) def onKeyDown(e: Event) {
    if (e.in.get(0, classOf[String]) eq "INVENTORY") {
      if (midContainer.containsObject(localWorld.localPlayer.guiInventory.getContainer)) {
        midContainer.removeObject(localWorld.localPlayer.guiInventory.getContainer)
      }
      else {
        midContainer.addObject(localWorld.localPlayer.guiInventory.getContainer)
      }
    }
    if (e.in.get(0, classOf[String]) eq "CRAFTING") {
      if (midContainer.containsObject(localWorld.localPlayer.guiCrafting.getContainer)) {
        midContainer.removeObject(localWorld.localPlayer.guiCrafting.getContainer)
      }
      else {
        midContainer.addObject(localWorld.localPlayer.guiCrafting.getContainer)
      }
    }
    if (e.in.get(0, classOf[String]) eq "PAUSE") {
      TerraInfinita.setScreen(TerraInfinita.screenPause)
    }
  }

  override def show {
    super.show
  }

  override def hide {
    super.hide
  }

  def getWorld: World = {
    return localWorld
  }

  override def render(delta: Float) {
    super.render(delta)
    localWorld.update(null, delta)
    TimeKeeping.timeKeeping("screen after update")
    update(delta)
    TimeKeeping.timeKeeping("Screen update")
    localWorld.draw(null, null, this, localWorld.localPlayer)
    TimeKeeping.timeKeeping("screen after draw")
    guiContainerScreen.update(delta)
    TimeKeeping.timeKeeping("GUI update")
    RenderingPass.rpGUIContainer.applyPass(this, null, localWorld, guiContainerScreen)
    TimeKeeping.timeKeeping("GUI draw")
  }

  def update(delta: Float) {
  }
}