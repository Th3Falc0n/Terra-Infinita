package com.dafttech.terra.game.world.tiles

import java.util.Random

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.TerraInfinita
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.renderer.{TileRenderer, TileRendererMultiblock}
import com.dafttech.terra.game.world.World
import com.dafttech.terra.resources.Resources

class TileLog() extends Tile {
  private var living: Boolean = false
  private var height: Int = 0
  private var width: Int = 0
  private var maxHeight: Int = TerraInfinita.rnd.nextInt(40) + 5
  private var maxWidth: Int = TerraInfinita.rnd.nextInt(20) + 5
  private var grothDelay: Float = 0.1f

  override def getImage: TextureRegion = Resources.TILES.getImage("log")

  override def onTick(world: World, delta: Float): Unit = {
    super.onTick(world, delta)
    if (grothDelay <= 0) growTree(world)
    else grothDelay -= delta
  }

  def growTree(world: World): Unit = {
    val rnd = new Random

    if (living) {
      if (height <= maxHeight && (width == 0 || rnd.nextInt(5) == 0) &&
        world.getTile(getPosition - (0, 1)).isReplacable) world.setTile(
        getPosition - (0, 1),
        (if (height > maxHeight - getSmallLayerSize) getLeaf
        else getLog)
          .setLiving(living)
          .setSize(height + 1, width, maxHeight, maxWidth),
        notify = true
      )

      if (height > maxHeight / 2.5f) {
        if (rnd.nextInt((Math.abs(width).toFloat * (if (rnd.nextBoolean) 1 else height) / maxWidth).toInt + 1) == 0 &&
          world.getTile(getPosition + (1, 0)).isReplacable) world.setTile(
          getPosition + (1, 0),
          (if (rnd.nextBoolean) getLog
          else getLeaf)
            .setLiving(living).setSize(height + 1, width + 1, maxHeight, maxWidth),
          notify = true
        )

        if (rnd.nextInt((Math.abs(width).toFloat * (if (rnd.nextBoolean) 1 else height) / maxWidth).toInt + 1) == 0 &&
          world.getTile(getPosition - (1, 0)).isReplacable)
          if (world.getTile(getPosition - (1, 0)).isReplacable) world.setTile(
            getPosition - (1, 0),
            (if (height > maxHeight / 2 && rnd.nextBoolean) getLog
            else getLeaf)
              .setLiving(living)
              .setSize(height + 1, width - 1, maxHeight, maxWidth),
            notify = true
          )
      }

      living = false
    }
  }

  private def setSize(height: Int, width: Int, maxHeight: Int, maxWidth: Int) = {
    this.height = height
    this.width = width
    this.maxHeight = maxHeight
    this.maxWidth = maxWidth
    this
  }

  private def getSmallLayerSize: Int = maxHeight / 5

  def getLog: TileLog = new TileLog()

  def getLeaf: TileLeaf = new TileLeaf()

  def setLiving(living: Boolean): TileLog = {
    this.living = living
    this
  }

  override def isFlammable: Boolean = true

  def isFlatTo(world: World, pos: Vector2i): Boolean =
    world.getTile(pos).isInstanceOf[TileLog] || world.getTile(pos).isOpaque

  override def getRenderer: TileRenderer = new TileRendererMultiblock(new Vector2i(4, 4))
}