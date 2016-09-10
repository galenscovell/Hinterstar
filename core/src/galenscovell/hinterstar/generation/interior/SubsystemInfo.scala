package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import galenscovell.hinterstar.util.Resources


class SubsystemInfo(name: String, subsystemIcon: Sprite, occupancy: Int, rootX: Int, rootY: Int, tileSize: Int) {
  private val maxOccupancy: Int = occupancy
  private var currentOccupancy: Int = 0
  private var occupancySprite: Sprite = _

  updateOccupancy(0)



  def updateOccupancy(amount: Int): Unit = {
    currentOccupancy += amount
    val spriteName: String = s"$currentOccupancy of $maxOccupancy"
    occupancySprite = new Sprite(Resources.atlas.createSprite(spriteName))
  }

  def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(subsystemIcon, rootX - 8, rootY + 8, 32, 32)
    batch.draw(occupancySprite, rootX + 32, rootY + 8, 32, 32)
  }
}
