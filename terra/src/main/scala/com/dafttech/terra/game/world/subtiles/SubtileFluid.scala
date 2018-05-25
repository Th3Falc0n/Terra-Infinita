package com.dafttech.terra.game.world.subtiles

import java.util
import java.util.Random

import com.dafttech.terra.engine.TilePosition
import com.dafttech.terra.engine.renderer.{SubtileRenderer, SubtileRendererFluid}
import com.dafttech.terra.game.world.tiles.Tile
import com.dafttech.terra.game.world.{Facing, World}

abstract class SubtileFluid extends Subtile {
  var maxPressure: Float = 10
  var pressure: Float = maxPressure

  /*override def setTile(t: Tile): Unit = {
    super.setTile(t)
    if (t != null) if (isFluid(t.getWorld, Facing.None)) {
      getFluid(t.getWorld, Facing.None).addPressure(pressure)
      setPressure(0)
    }
  }*/

  override def onTick(delta: Float)(implicit tilePosition: TilePosition): Unit = {
    super.onTick(delta)
    flow(tilePosition, delta)
  }

  def flow(tilePosition: TilePosition, delta: Float): Unit =
    if (pressure < maxPressure / 1000) tilePosition.tile.removeSubtile(this)
    else {
      val amount = maxPressure / ((if (getViscosity < 0) 0 else getViscosity) + 1) * delta * 60
      val pressCap = getPressCap
      val amountDown = flowDown(tilePosition, amount, pressCap)
      val amountSide = flowSide(tilePosition, amountDown, pressCap)
      val amountUp = flowUp(tilePosition, amount, pressCap)
    }

  def flowDown(tilePosition: TilePosition, amount: Float, pressCap: Float): Float = {
    val fluid = getFluid(tilePosition, Facing.Bottom)
    if (fluid != null) {
      val total = pressure + fluid._1.pressure
      var change: Float = 0
      if (total > maxPressure * (2 + pressCap / maxPressure)) {
        val avg = total / 2
        change = avg + pressCap
      } else {
        var possAmount = total
        if (possAmount > maxPressure + (total - maxPressure) / maxPressure * pressCap) possAmount = maxPressure + (total - maxPressure) / maxPressure * pressCap
        change = possAmount
      }

      var possAmount = change - fluid._1.pressure
      if (possAmount > amount) possAmount = amount

      if ((possAmount > 0 && !fluid._2.isWaterproof) ||
        (possAmount < 0 && !tilePosition.tile.isWaterproof) ||
        (fluid._2.isWaterproof == tilePosition.tile.isWaterproof)) {
        addPressure(-possAmount)
        fluid._1.addPressure(possAmount)
        return if (amount - possAmount < 0) 0
        else amount - possAmount
      }
    }
    amount
  }

  def flowSide(tilePosition: TilePosition, amount: Float, pressCap: Float): Float = {
    if (amount > 0) {
      var fluid: (SubtileFluid, Tile) = null
      if (new Random().nextBoolean) {
        fluid = getFluid(tilePosition, Facing.Right)
        if (fluid != null) {
          var reach = getMaxReach
          while (
            reach > 0 &&
              new Random().nextInt(5) > 0 &&
              fluid._1.isFluid(tilePosition, Facing.Right) &&
              (fluid._1.getFluid(tilePosition, Facing.Right)._2.isWaterproof == fluid._2.isWaterproof) &&
              fluid._1.getFluid(tilePosition, Facing.Right)._1.pressure > fluid._1.maxPressure / 20
          ) {
            reach -= 1
            fluid = fluid._1.getFluid(tilePosition, Facing.Right)
          }
        }
      }
      else {
        fluid = getFluid(tilePosition, Facing.Left)
        if (fluid != null) {
          var reach = getMaxReach
          while (
            reach > 0 &&
              new Random().nextInt(5) > 0 &&
              fluid._1.isFluid(tilePosition, Facing.Left) &&
              (fluid._1.getFluid(tilePosition, Facing.Left)._2.isWaterproof == fluid._2.isWaterproof) &&
              fluid._1.getFluid(tilePosition, Facing.Left)._1.pressure > fluid._1.maxPressure / 20
          ) {
            reach -= 1
            fluid = fluid._1.getFluid(tilePosition, Facing.Left)
          }
        }
      }
      if (fluid != null && fluid._1.pressure < pressure) {
        val avg = (pressure + fluid._1.pressure) / 2
        var possAmount = avg - fluid._1.pressure
        if (possAmount > amount) possAmount = amount
        if ((possAmount > 0 && !fluid._2.isWaterproof) ||
          (possAmount < 0 && !tilePosition.tile.isWaterproof) ||
          (fluid._2.isWaterproof == tilePosition.tile.isWaterproof)) {
          addPressure(-possAmount)
          fluid._1.addPressure(possAmount)
          return if (amount - possAmount < 0) 0
          else amount - possAmount
        }
      }
    }
    amount
  }

  def flowSide_wip(tilePosition: TilePosition, amount: Float, pressCap: Float): Float = {
    if (amount > 0) {
      val facing = if (new Random().nextBoolean) Facing.Right
      else Facing.Left
      var fluid = getFluid(tilePosition, facing)
      if (fluid != null) {
        var reach = getMaxReach
        var totalAmount = pressure
        val fluids = new util.LinkedList[SubtileFluid]
        while (
          reach > 0 &&
            new Random().nextInt(5) > 0 &&
            fluid._1.isFluid(tilePosition, facing) &&
            (fluid._1.getFluid(tilePosition, facing)._2.isWaterproof == fluid._2.isWaterproof) &&
            fluid._1.getFluid(tilePosition, facing)._1.pressure > fluid._1.maxPressure / 20
        ) {
          reach -= 1
          totalAmount += fluid._1.pressure
          fluid = fluid._1.getFluid(tilePosition, facing)
          fluids.add(fluid._1)
        }

        if (fluid._1.pressure < pressure) {
          val avg = totalAmount / fluids.size + 1
          // (pressure + fluid.pressure) / 2;
          var possAmount = avg - fluid._1.pressure
          if (possAmount > amount) possAmount = amount
          if ((possAmount > 0 && !fluid._2.isWaterproof) ||
            (possAmount < 0 && !tilePosition.tile.isWaterproof) ||
            (fluid._2.isWaterproof == tilePosition.tile.isWaterproof)) {
            addPressure(-possAmount)
            fluid._1.addPressure(possAmount)
            return if (amount - possAmount < 0) 0
            else amount - possAmount
          }
        }
      }
    }
    amount
  }

  def flowUp(tilePosition: TilePosition, amount: Float, pressCap: Float): Float = {
    val fluid = getFluid(tilePosition, Facing.Top)
    if (fluid != null) {
      val total = pressure + fluid._1.pressure
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
        if ((possAmount > 0 && !tilePosition.tile.isWaterproof) || (possAmount < 0 && !fluid._2.isWaterproof) || (fluid._2.isWaterproof == tilePosition.tile.isWaterproof)) {
          fluid._1.addPressure(-possAmount)
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

  def getFluid(tilePosition: TilePosition, direction: Facing): (SubtileFluid, Tile) = {
    val tile = tilePosition.world.getTile(tilePosition.pos + direction.intVector)
    // if (tile.isWaterproof()) return null;
    var fluid = tilePosition.tile.getSubtile(getClass, inherited = false).asInstanceOf[SubtileFluid]
    if (fluid == null) {
      if (tile.isWaterproof) return null
      fluid = getNewFluid.setPressure(0)
      tile.addSubtile(fluid)
    }
    (fluid, tile)
  }

  def isFluid(tilePosition: TilePosition, direction: Facing): Boolean = {
    val tile = tilePosition.world.getTile(tilePosition.pos + direction.intVector)
    // if (tile.isWaterproof()) return false;
    tile.hasSubtile(getClass, inherited = false)
  }

  def getNewFluid: SubtileFluid

  def getViscosity: Float

  def getMaxReach: Int

  def getPressCap: Int

  override def isTileIndependent = true
}