package com.dafttech.terra.game.world.entities.living

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.{ Body, BodyDef, PolygonShape }
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.gui.modules.{ ModuleCrafting, ModuleHUDBottom, ModuleInventory }
import com.dafttech.terra.engine.input.{ InputHandler, MousePos }
import com.dafttech.terra.engine.lighting.PointLight
import com.dafttech.terra.engine.vector.{ Vector2d, Vector2i }
import com.dafttech.terra.engine.{ AbstractScreen, TilePosition }
import com.dafttech.terra.game.Events
import com.dafttech.terra.game.world.GameWorld
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.entities.particles.ParticleDust
import com.dafttech.terra.game.world.interaction.Skill
import com.dafttech.terra.game.world.items._
import com.dafttech.terra.game.world.items.inventories.{ Inventory, Stack }
import com.dafttech.terra.game.world.tiles._
import com.dafttech.terra.resources.Options.METERS_PER_BLOCK
import com.dafttech.terra.resources.{ Options, Resources }
import monix.eval.Task

import scala.concurrent.duration._

class Player(pos: Vector2d)(implicit world: GameWorld) extends EntityLiving(pos) {
  Events.EVENTMANAGER.registerEventListener(this)

  val inventory = new Inventory()
  val equip = new Inventory()

  val hudBottom = new ModuleHUDBottom
  hudBottom.create
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

  override def modifyBodyDef(bodyDef: BodyDef): Unit = {
    bodyDef.fixedRotation = true
  }

  override def createFixture(body: Body) = {
    val shape = new PolygonShape()

    shape.setAsBox(1.1f, 1.8f)

    body.createFixture(shape, 1)
    shape.dispose()
  }

  override def update(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.update(delta)
    if (InputHandler.isKeyDown("LEFT")) walkLeft()
    if (InputHandler.isKeyDown("RIGHT")) walkRight()
    if (InputHandler.isKeyDown("JUMP") && !this.isInAir) jump()

    if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
      val mouseInWorldPos = MousePos.vector2d + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)
      if (!hudBottom.getActiveSlot.useAssignedItem(this, mouseInWorldPos, leftClick = true) && System.currentTimeMillis - left > 10) {
        left = System.currentTimeMillis
        val destroy = (MousePos.vector2d + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)).toWorldPosition
        val damagedTile = getWorld.getTile(Vector2i(destroy.x, destroy.y))
        if (damagedTile != null) damagedTile.damage(0.2f, this)
      }
    }

    if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
      val mouseInWorldPos = MousePos.vector2d + getPosition - (Gdx.graphics.getWidth / 2, Gdx.graphics.getHeight / 2)
      hudBottom.getActiveSlot.useAssignedItem(this, mouseInWorldPos, leftClick = false)
    }

    if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) right = false

    /*if (getUndergroundTile != null && !inAir) {
      for (_ <- 0 until 5)
        if (TerraInfinita.rnd.nextDouble < delta * body.linVelWorld.len() * 2f)
          new ParticleDust(
            getPosition +
              ((TerraInfinita.rnd.nextFloat - 0.5f) * Options.METERS_PER_BLOCK * 2, (TerraInfinita.rnd.nextFloat - 1f) * 4f),
            getUndergroundTile.getImage
          )
    }*/

    hudBottom.healthBar.setValue(getHealth / getMaxHealth * 100)
    guiInventory.update(delta)
  }

  override def getImage: Task[TextureRegion] = Resources.ENTITIES.getImageTask("player")

  def getInventory: Inventory = inventory

  override def draw(screen: AbstractScreen, pointOfView: Entity)(implicit tilePosition: TilePosition): Unit = {
    /*val screenVec = this.getPosition.toRenderPosition(pointOfView.getPosition)
    screen.batch.setColor(color)
    // TODO: Scheduler
    import com.dafttech.terra.utils.RenderThread._
    val image = getImage.runSyncUnsafe(5.seconds)
    screen.batch.draw(image, screenVec.x.toFloat, screenVec.y.toFloat, image.getRegionWidth, image.getRegionHeight)
    screen.batch.flush()*/

    super.draw(screen, this)
    // hudBottom.getActiveSlot().draw(screen, Entity pointOfView);
  }

  def getSkillForID(skillID: Int): Skill = null
}