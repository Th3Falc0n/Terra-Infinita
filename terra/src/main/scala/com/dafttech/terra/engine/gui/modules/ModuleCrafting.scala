package com.dafttech.terra.engine.gui.modules

import com.dafttech.terra.engine.gui.anchors._
import com.dafttech.terra.engine.gui.containers.ContainerBlock
import com.dafttech.terra.engine.gui.elements.{ElementButton, ElementLabel}
import com.dafttech.terra.engine.vector.Vector2d
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.entities.living.Player

class ModuleCrafting extends GUIModule {
  private[modules] var recipeList: ContainerBlock = null
  private[modules] var researchList: ContainerBlock = null
  private[modules] var shown: ContainerBlock = null
  private[modules] var player: Player = null

  def this(p: Player) {
    this()
    player = p
  }

  def create(): Unit = {
    Events.EVENTMANAGER.registerEventListener(this)
    container = new ContainerBlock(Vector2d.Zero, Vector2d(312, 200))
    val set: GUIAnchorSet = new GUIAnchorSet
    set.addAnchor(new AnchorCenterX)
    container.assignAnchorSet(set)
    var btnRecipe: ElementButton = null
    var btnResearch: ElementButton = null
    btnRecipe = new ElementButton(Vector2d.Zero, "Recipes") {
      def actionPerformed(button: Int) {
        shown.clearObjects
        shown.addObject(recipeList)
      }
    }
    btnResearch = new ElementButton(Vector2d.Zero, "Research") {
      def actionPerformed(button: Int) {
        shown.clearObjects
        shown.addObject(researchList)
      }
    }
    btnRecipe.assignAnchorSet(new GUIAnchorSet(new AnchorLeft(0), new AnchorTop(0)))
    btnResearch.assignAnchorSet(new GUIAnchorSet(new AnchorRightNextTo(btnRecipe, 10)))
    container.addObject(btnRecipe)
    container.addObject(btnResearch)
    shown = new ContainerBlock(Vector2d(0, 20), Vector2d(312, 150))
    recipeList = new ContainerBlock(Vector2d(0, 0), Vector2d(312, 150))
    researchList = new ContainerBlock(Vector2d(0, 0), Vector2d(312, 150))
    shown.addObject(researchList)
    container.addObject(shown)
    recipeList.addObject(new ElementLabel(Vector2d(0, 0), "Learned Recipes:"))
    researchList.addObject(new ElementLabel(Vector2d(0, 0), "Research new Recipes:"))
  }
}