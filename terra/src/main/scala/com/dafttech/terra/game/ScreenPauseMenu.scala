package com.dafttech.terra.game

import com.badlogic.gdx.Gdx
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.gui.Tooltip
import com.dafttech.terra.engine.gui.anchors.{AnchorCenterX, AnchorRight, AnchorTop, GUIAnchorSet}
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen
import com.dafttech.terra.engine.gui.elements.ElementButton
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.world.World

class ScreenPauseMenu extends AbstractScreen {
  private[game] var localWorld: World = _
  private[game] var exitButton: ElementButton = _
  private[game] var resumeButton: ElementButton = _

  def this(w: World) {
    this()
    localWorld = w
    guiContainerScreen = new ContainerOnscreen
    exitButton = new ElementButton(Vector2.Null, "Exit") {
      def actionPerformed(button: Int) {
        Gdx.app.exit()
      }
    }

    val exitButtonSet: GUIAnchorSet = new GUIAnchorSet(new AnchorRight(0.01f), new AnchorTop(0.01f))

    exitButton.assignAnchorSet(exitButtonSet)
    exitButton.setTooltip("Close the game")
    resumeButton = new ElementButton(Vector2.Null, "Resume") {
      def actionPerformed(button: Int) {
        TerraInfinita.setScreen(TerraInfinita.screenIngame)
      }
    }
    val resumeButtonSet: GUIAnchorSet = new GUIAnchorSet(new AnchorCenterX, new AnchorTop(0.2f))

    resumeButton.assignAnchorSet(resumeButtonSet)

    guiContainerScreen.addObject(exitButton)
    guiContainerScreen.addObject(resumeButton)
    guiContainerScreen.addObject(Tooltip.getLabel)
    guiContainerScreen.applyAllAssignedAnchorSets
  }

  override def render(delta: Float): Unit = {
    super.render(delta)
    localWorld.draw(null, null, this, localWorld.localPlayer)
    guiContainerScreen.update(delta)
    RenderingPass.rpGUIContainer.applyPass(this, null, null, guiContainerScreen)
  }
}