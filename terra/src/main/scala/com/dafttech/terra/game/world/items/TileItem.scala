package com.dafttech.terra.game.world.items

import java.lang.reflect.Field

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.vector.{Vector2d, Vector2i}
import com.dafttech.terra.game.world.entities.models.EntityLiving
import com.dafttech.terra.game.world.items.persistence.Prototype
import com.dafttech.terra.game.world.tiles.Tile
import monix.eval.Task

case class TileItem(tile: Tile) extends Item {
  override val getImage: Task[TextureRegion] = tile.getImage

  override def use(causer: EntityLiving, position: Vector2d): Boolean =
    if ((causer.getPosition - position).length < 100) {
      val pos = position.toWorldPosition
      causer.getWorld.placeTile(Vector2i(pos.x, pos.y), tile, causer)
    } else
      false

  override def update(delta: Float): Unit = ()

  override def toPrototype: Prototype = {
    val protptype = super.toPrototype
    protptype.addValue(TileItem.tileField, tile)
    protptype
  }
}

object TileItem {
  val tileField: Field = classOf[TileItem].getDeclaredField("tile")
}
