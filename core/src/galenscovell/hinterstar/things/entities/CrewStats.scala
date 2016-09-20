package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.{ProgressBar, Table}
import galenscovell.hinterstar.util.Resources


class CrewStats(crewmate: Crewmate) extends Table {
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.horizontalBarStyle)

  construct()



  private def construct(): Unit = {
    healthBar.setValue(100)

    this.add(healthBar).expand.fill.width(48).height(8).center
  }



  def setHealthValue(v: Int): Unit = {
    healthBar.setValue(v)
  }



  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    val crewmateCoords: Vector2 = crewmate.getActorCoordinates
    this.setPosition(crewmateCoords.x + 16, crewmateCoords.y + 36)
    super.draw(batch, parentAlpha)
  }
}
