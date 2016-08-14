package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import galenscovell.hinterstar.util.Resources


class SubsystemInfo(name: String, occupancy: Int, rootX: Int, rootY: Int) {
  private val infoX: Int = rootX
  private val infoY: Int = rootY
  private val subsystemName: String = name
  private val maxOccupancy: Int = occupancy
  private var currentOccupancy: Int = 0
  private var sprite: Sprite = _

  updateOccupancy(0)


  def updateOccupancy(amount: Int): Unit = {
    currentOccupancy += amount
    val spriteName: String = s"$currentOccupancy of $maxOccupancy"
    sprite = new Sprite(Resources.atlas.createSprite(spriteName))
  }

  def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(sprite, infoX, infoY, 48, 48)
  }
}
