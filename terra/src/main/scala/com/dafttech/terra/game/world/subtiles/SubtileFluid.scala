package com.dafttech.terra.game.world.subtiles

import java.util
import java.util.Random

import com.dafttech.terra.engine.renderer.{SubtileRenderer, SubtileRendererFluid}
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.game.world.{Facing, World}

abstract class SubtileFluid() extends Subtile {
  var maxPressure: Float = 10
  var pressure: Float = maxPressure

  override def setTile(t: Tile): Unit = {
    super.setTile(t)
    if (t != null) if (isFluid(t.getWorld, Facing.None)) {
      getFluid(t.getWorld, Facing.None).addPressure(pressure)
      setPressure(0)
    }
  }

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)
    flow(world, delta)
  }

  @SuppressWarnings(Array("unused")) def flow(world: World, delta: Float): Unit =
    if (pressure < maxPressure / 1000) tile.removeSubtile(this)
    else {
      val amount = maxPressure / ((if (getViscosity < 0) 0 else getViscosity) + 1) * delta * 60
      val pressCap = getPressCap
      val amountDown = flowDown(world, amount, pressCap)
      val amountSide = flowSide(world, amountDown, pressCap)
      val amountUp = flowUp(world, amount, pressCap)
    }

  def flowDown(world: World, amount: Float, pressCap: Float): Float = {
    val fluid = getFluid(world, Facing.Bottom)
    if (fluid != null) {
      val total = pressure + fluid.pressure
      var change: Float = 0
      if (total > maxPressure * (2 + pressCap / maxPressure)) {
        val avg = total / 2
        change = avg + pressCap
      } else {
        var possAmount = total
        if (possAmount > maxPressure + (total - maxPressure) / maxPressure * pressCap) possAmount = maxPressure + (total - maxPressure) / maxPressure * pressCap
        change = possAmount
      }

      var possAmount = change - fluid.pressure
      if (possAmount > amount) possAmount = amount
      if ((possAmount > 0 && !fluid.tile.isWaterproof) ||
        (possAmount < 0 && !tile.isWaterproof) ||
        (fluid.tile.isWaterproof == tile.isWaterproof)) {
        addPressure(-possAmount)
        fluid.addPressure(possAmount)
        return if (amount - possAmount < 0) 0
        else amount - possAmount
      }
    }
    amount
  }

  def flowSide(world: World, amount: Float, pressCap: Float): Float = {
    if (amount > 0) {
      var fluid: SubtileFluid = null
      if (new Random().nextBoolean) {
        fluid = getFluid(world, Facing.Right)
        if (fluid != null) {
          var reach = getMaxReach
          while (
            reach > 0 &&
              new Random().nextInt(5) > 0 &&
              fluid.isFluid(world, Facing.Right) &&
              (fluid.getFluid(world, Facing.Right).tile.isWaterproof == fluid.tile.isWaterproof) &&
              fluid.getFluid(world, Facing.Right).pressure > fluid.maxPressure / 20
          ) {
            reach -= 1
            fluid = fluid.getFluid(world, Facing.Right)
          }
        }
      }
      else {
        fluid = getFluid(world, Facing.Left)
        if (fluid != null) {
          var reach = getMaxReach
          while (
            reach > 0 &&
              new Random().nextInt(5) > 0 &&
              fluid.isFluid(world, Facing.Left) &&
              (fluid.getFluid(world, Facing.Left).tile.isWaterproof == fluid.tile.isWaterproof) &&
              fluid.getFluid(world, Facing.Left).pressure > fluid.maxPressure / 20
          ) {
            reach -= 1
            fluid = fluid.getFluid(world, Facing.Left)
          }
        }
      }
      if (fluid != null && fluid.pressure < pressure) {
        val avg = (pressure + fluid.pressure) / 2
        var possAmount = avg - fluid.pressure
        if (possAmount > amount) possAmount = amount
        if ((possAmount > 0 && !fluid.tile.isWaterproof) ||
          (possAmount < 0 && !tile.isWaterproof) ||
          (fluid.tile.isWaterproof == tile.isWaterproof)) {
          addPressure(-possAmount)
          fluid.addPressure(possAmount)
          return if (amount - possAmount < 0) 0
          else amount - possAmount
        }
      }
    }
    amount
  }

  def flowSide_wip(world: World, amount: Float, pressCap: Float): Float = {
    if (amount > 0) {
      val facing = if (new Random().nextBoolean) Facing.Right
      else Facing.Left
      var fluid = getFluid(world, facing)
      if (fluid != null) {
        var reach = getMaxReach
        var totalAmount = pressure
        val fluids = new util.LinkedList[SubtileFluid]
        while (
          reach > 0 &&
            new Random().nextInt(5) > 0 &&
            fluid.isFluid(world, facing) &&
            (fluid.getFluid(world, facing).tile.isWaterproof == fluid.tile.isWaterproof) &&
            fluid.getFluid(world, facing).pressure > fluid.maxPressure / 20
        ) {
          reach -= 1
          totalAmount += fluid.pressure
          fluid = fluid.getFluid(world, facing)
          fluids.add(fluid)
        }

        if (fluid.pressure < pressure) {
          val avg = totalAmount / fluids.size + 1
          // (pressure + fluid.pressure) / 2;
          var possAmount = avg - fluid.pressure
          if (possAmount > amount) possAmount = amount
          if ((possAmount > 0 && !fluid.tile.isWaterproof) ||
            (possAmount < 0 && !tile.isWaterproof) ||
            (fluid.tile.isWaterproof == tile.isWaterproof)) {
            addPressure(-possAmount)
            fluid.addPressure(possAmount)
            return if (amount - possAmount < 0) 0
            else amount - possAmount
          }
        }
      }
    }
    amount
  }

  def flowUp(world: World, amount: Float, pressCap: Float): Float = {
    val fluid = getFluid(world, Facing.Top)
    if (fluid != null) {
      val total = pressure + fluid.pressure
      if (pressure > maxPressure + (total - maxPressure) / maxPressure * pressCap) {
        var change: Float = 0
        if (total > maxPressure * (2 + pressCap / maxPressure)) {
          val avg = total / 2
          change = avg + pressCap
        }
        else {
          var possAmount = total
          if (possAmount > maxPressure + (total - maxPressure) / maxPressure * pressCap) possAmount = maxPressure + (total - maxPressure) / maxPressure * pressCap
          change = possAmount
        }
        var possAmount = change - pressure
        if (possAmount > amount) possAmount = amount
        if ((possAmount > 0 && !tile.isWaterproof) || (possAmount < 0 && !fluid.tile.isWaterproof) || (fluid.tile.isWaterproof == tile.isWaterproof)) {
          fluid.addPressure(-possAmount)
          addPressure(possAmount)
          return if (amount - possAmount < 0) 0
          else amount - possAmount
        }
      }
    }
    amount
  }

  def addPressure(pressure: Float): Unit = this.pressure += pressure

  def setPressure(pressure: Float): SubtileFluid = {
    this.pressure = pressure
    this
  }

  override def getRenderer: SubtileRenderer = SubtileRendererFluid.$Instance

  def getFluid(world: World, direction: Facing): SubtileFluid = {
    val tile = world.getTile(this.tile.getPosition.$plus(direction.intVector))
    // if (tile.isWaterproof()) return null;
    var fluid = tile.getSubtile(getClass, false).asInstanceOf[SubtileFluid]
    if (fluid == null) {
      if (tile.isWaterproof) return null
      fluid = getNewFluid.setPressure(0)
      tile.addSubtile(fluid)
    }
    fluid
  }

  def isFluid(world: World, direction: Facing): Boolean = {
    val tile = world.getTile(this.tile.getPosition.$plus(direction.intVector))
    // if (tile.isWaterproof()) return false;
    tile.hasSubtile(getClass, false)
  }

  def getNewFluid: SubtileFluid

  def getViscosity: Float

  def getMaxReach: Int

  def getPressCap: Int

  override def isTileIndependent = true
}