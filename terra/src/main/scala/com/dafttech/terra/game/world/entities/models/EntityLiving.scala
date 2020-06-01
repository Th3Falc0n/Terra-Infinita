package com.dafttech.terra.game.world.entities.models

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{ Body, BodyDef, Fixture, PolygonShape, WorldManifold }
import com.dafttech.terra.engine.vector.{ Vector2d, Vector2f }
import com.dafttech.terra.game.world.{ CollisionReceiver, GameWorld }
import com.dafttech.terra.game.world.entities.Entity
import com.dafttech.terra.game.world.tiles.Tile
import monix.eval.Task

abstract class EntityLiving(pos: Vector2d)(implicit world: GameWorld) extends Entity(pos) with CollisionReceiver {
  private var maxHealth: Float = 10
  private var health: Float = maxHealth

  override def getImage: Task[TextureRegion] = null

  private var inAir = true

  def getSize: Vector2f

  override def modifyBodyDef(bodyDef: BodyDef): Unit = {
    bodyDef.fixedRotation = true
  }

  override def createFixture(body: Body) = {
    val shape = new PolygonShape()

    val botSens = new PolygonShape()

    botSens.setAsBox(getSize.x - 0.3f, 0.1f, new Vector2(0, getSize.y), 0)

    shape.setAsBox(getSize.x, getSize.y)

    val fix = body.createFixture(shape, 0.4f)
    fix.setFriction(0.01f)

    val sens = body.createFixture(botSens, 0)
    sens.setSensor(true)
    sens.setUserData(this)

    shape.dispose()
  }

  override def beginContact(other: Fixture, worldManifold: WorldManifold): Unit = {
    other.getUserData match {
      case t: Tile if !t.isAir =>
        inAir = false
      case _ =>
    }
  }

  override def endContact(other: Fixture, worldManifold: WorldManifold): Unit = {

  }

  def damage(damage: Float): EntityLiving = heal(-damage)

  def heal(health: Float): EntityLiving = {
    this.health += health
    if (this.health < 0) this.health = 0
    if (this.health > maxHealth) this.health = maxHealth
    this
  }

  def jump(): Unit = {
    if(!this.inAir) {
      body.applyLinearImpulse(0, -50, 0, 0, true)
      inAir = true
    }
  }


  def walkLeft(): Unit = body.applyForceToCenter(-50, 0f, true)

  def walkRight(): Unit =  body.applyForceToCenter(50, 0f, true)

  protected def setMaxHealth(maxHealth: Float): EntityLiving = {
    this.maxHealth = maxHealth
    this
  }

  def getHealth: Float = health

  def getMaxHealth: Float = maxHealth
}