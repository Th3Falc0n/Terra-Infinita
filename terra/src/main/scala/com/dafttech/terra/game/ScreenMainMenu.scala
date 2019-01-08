package com.dafttech.terra.game

import com.badlogic.gdx.Gdx
import com.dafttech.terra.engine.AbstractScreen
import com.dafttech.terra.engine.gui.Tooltip
import com.dafttech.terra.engine.gui.anchors.{AnchorRight, AnchorTop, GUIAnchorSet}
import com.dafttech.terra.engine.gui.containers.ContainerOnscreen
import com.dafttech.terra.engine.gui.elements.ElementButton
import com.dafttech.terra.engine.passes.RenderingPass
import com.dafttech.terra.engine.vector.Vector2d

class ScreenMainMenu extends AbstractScreen {
  private[game] var exitButton: ElementButton = null

  guiContainerScreen = new ContainerOnscreen
  exitButton = new ElementButton(Vector2d.Zero, "Exit") {
    def actionPerformed(button: Int): Unit = {
      Gdx.app.exit()
    }
  }
  val exitButtonSet: GUIAnchorSet = new GUIAnchorSet
  exitButtonSet.addAnchor(new AnchorRight(0.01f))
  exitButtonSet.addAnchor(new AnchorTop(0.01f))
  exitButton.assignAnchorSet(exitButtonSet)
  exitButton.setTooltip("Close the game")
  guiContainerScreen.addObject(exitButton)
  guiContainerScreen.addObject(Tooltip.getLabel)
  guiContainerScreen.applyAllAssignedAnchorSets

  override def render(delta: Float): Unit = {
    super.render(delta)
    guiContainerScreen.update(delta)
    RenderingPass.rpGUIContainer.applyPass(this, null, null, guiContainerScreen)
  }
}