package com.dafttech.terra.game.world.entities.living

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.gui.modules.{ModuleCrafting, ModuleHUDBottom, ModuleInventory}
import com.dafttech.terra.engine.input.InputHandler
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.{AbstractScreen, Vector2}
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.World
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.entities.particles.ParticleDust
import com.dafttech.terra.game.world.interaction.Skill
import com.dafttech.terra.game.world.items._
import com.dafttech.terra.game.world.items.inventories.{Inventory, Stack}
import com.dafttech.terra.game.world.tiles._
import com.dafttech.terra.resources.{Options, Resources}

class Player(pos: Vector2, world: World) extends EntityLiving(pos, world, new Vector2(1.9f, 3.8f)) {
  Events.EVENTMANAGER.registerEventListener(this)

  val inventory = new Inventory()
  val equip = new Inventory()

  val hudBottom = new ModuleHUDBottom
  hudBottom.create()
  val guiInventory = new ModuleInventory(inventory)
  guiInventory.create()
  val guiCrafting = new ModuleCrafting(this)
  guiCrafting.create()

  hudBottom.slots(0).assignStack(Stack.apply(new TileDirt, 1000))
  hudBottom.slots(1).assignStack(Stack.apply(new ItemFlamingArrow, 10000))
  hudBottom.slots(2).assignStack(Stack.apply(new ItemGlowstick, 100))
  hudBottom.slots(3).assignStack(Stack.apply(new ItemDynamite, 100))
  hudBottom.slots(4).assignStack(Stack.apply(new ItemRainbowGun, 1))
  hudBottom.slots(5).assignStack(Stack.apply(new ItemWaterBucket, 1))
  hudBottom.slots(6).assignStack(Stack.apply(new TileTorch, 10000))
  hudBottom.slots(7).assignStack(Stack.apply(new ItemDigStaff, 1))
  inventory.add(Stack.apply(new TileSapling, 1000))
  inventory.add(Stack.apply(new TileFence, 1000))
  inventory.add(Stack.apply(new TileFire, 1000))

  private[living] var left: Long = 0L
  private[living] var right = false

  private[living] val light: PointLight = null

  override def update(world: World, delta: Float): Unit = {
    super.update(world, delta)
    if (InputHandler.isKeyDown("LEFT")) walkLeft()
    if (InputHandler.isKeyDown("RIGHT")) walkRight()
    if (InputHandler.isKeyDown("JUMP") && !this.isInAir) jump()

    if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
      val mouseInWorldPos = Vector2.mousePos + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)
      if (!hudBottom.getActiveSlot.useAssignedItem(this, mouseInWorldPos, true) && System.currentTimeMillis - left > 10) {
        left = System.currentTimeMillis
        val destroy = (Vector2.mousePos + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)).toWorldPosition
        val damagedTile = getWorld.getTile(destroy.x, destroy.y)
        if (damagedTile != null) damagedTile.damage(world, 0.2f, this)
      }
    }

    if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
      val mouseInWorldPos = Vector2.mousePos + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)
      hudBottom.getActiveSlot.useAssignedItem(this, mouseInWorldPos, false)
    }

    if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) right = false

    for (_ <- 0 until 5)
      if (TerraInfinita.rnd.nextDouble < delta * velocity.length * 2f)
        if (getUndergroundTile != null && !inAir)
          new ParticleDust(
            getPosition + (size.x * Options.BLOCK_SIZE / 2, size.y * Options.BLOCK_SIZE) +
              ((TerraInfinita.rnd.nextFloat - 0.5f) * Options.BLOCK_SIZE * 2, (TerraInfinita.rnd.nextFloat - 1f) * 4f),
            worldObj,
            getUndergroundTile.getImage
          )

    hudBottom.healthBar.setValue(getHealth / getMaxHealth * 100)
    guiInventory.update(delta)
  }

  override def getImage: TextureRegion = Resources.ENTITIES.getImage("player")

  def getInventory: Inventory = inventory

  override def draw(pos: Vector2, world: World, screen: AbstractScreen, pointOfView: Entity): Unit = {
    val screenVec = this.getPosition.toRenderPosition(pointOfView.getPosition)
    screen.batch.setColor(color)
    screen.batch.draw(this.getImage, screenVec.xFloat, screenVec.yFloat, Options.BLOCK_SIZE * size.xFloat, Options.BLOCK_SIZE * size.yFloat)
    screen.batch.flush()
    // hudBottom.getActiveSlot().draw(screen, Entity pointOfView);
  }

  def getSkillForID(skillID: Int): Skill = null
}