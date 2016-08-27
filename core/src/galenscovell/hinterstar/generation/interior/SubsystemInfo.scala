package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import galenscovell.hinterstar.util.Resources


class SubsystemInfo(name: String, occupancy: Int, rootX: Int, rootY: Int, tileSize: Int) {
  private val infoX: Int = rootX
  private val infoY: Int = rootY

  private val subsystemType: String = name match {
    case "Weapon Control" => "weapon"
    case "Shield Control" => "shield"
    case "Engine Room" => "engine"
    case "Helm" => "helm"
    case "Medbay" => "medbay"
    case _ => ""
  }

  private val maxOccupancy: Int = occupancy
  private var currentOccupancy: Int = 0

  private var occupancySprite: Sprite = _
  private val subsystemSprite: Sprite = new Sprite(Resources.atlas.createSprite("icon_" + subsystemType))

  updateOccupancy(0)


  def updateOccupancy(amount: Int): Unit = {
    currentOccupancy += amount
    val spriteName: String = s"$currentOccupancy of $maxOccupancy"
    occupancySprite = new Sprite(Resources.atlas.createSprite(spriteName))
  }

  def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(subsystemSprite, infoX - 8, infoY + 8, 32, 32)
    batch.draw(occupancySprite, infoX + 32, infoY + 8, 32, 32)
  }
}
