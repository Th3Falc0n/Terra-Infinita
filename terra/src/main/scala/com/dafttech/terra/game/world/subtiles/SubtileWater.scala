package com.dafttech.terra.game.world.subtiles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.input.InputHandler
import com.dafttech.terra.game.world.{Facing, World}
import com.dafttech.terra.resources.Resources

class SubtileWater() extends SubtileFluid {
  private[subtiles] var img: Float = 0
  private[subtiles] var wavephase = false

  override def getImage: TextureRegion = Resources.TILES.getImage("wateranim", img.toInt)

  override def getNewFluid = new SubtileWater

  override def onTick(world: World, delta: Float): Unit = {
    img += delta
    if (img.toInt > 3) img = 0
    var windSpeed = world.weather.getWindSpeed(world)
    var facing = if (windSpeed > 0) Facing.Right
    else if (windSpeed < 0) Facing.Left
    else null
    if (InputHandler.isKeyDown("WAVESLEFT")) {
      facing = Facing.Left
      windSpeed = 5
    }
    else if (InputHandler.isKeyDown("WAVESRIGHT")) {
      facing = Facing.Right
      windSpeed = 5
    }
    if (facing != null && !wavephase) {
      wavephase = true
      var fluid = getFluid(world, facing)
      var maxReach = 5
      val amount = maxPressure * delta * Math.abs(windSpeed)
      while ( {
        maxReach > 0 && fluid != null && pressure > amount
      }) {
        maxReach -= 1
        if (fluid.pressure + amount < fluid.maxPressure * 2) {
          addPressure(-amount)
          fluid.addPressure(amount)
        }
        fluid = fluid.getFluid(world, facing)
      }
    }
    else {
      wavephase = false
      super.onTick(world, delta)
    }
  }

  override def getViscosity: Float = if (InputHandler.isKeyDown("WAVESRIGHT") || InputHandler.isKeyDown("WAVESLEFT")) 4 else 0

  override def getMaxReach: Int = if (InputHandler.isKeyDown("WAVESRIGHT") || InputHandler.isKeyDown("WAVESLEFT")) 4 else 10

  override def getPressCap: Int = 2

  override def providesSunlightFilter: Boolean = true

  override def getFilterColor = new Color(1 - pressure / maxPressure, 1 - pressure / maxPressure, 1, 1)
}