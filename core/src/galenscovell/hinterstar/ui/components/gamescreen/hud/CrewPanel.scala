package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.hud
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class CrewPanel(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private val contentTable: Table = new Table

  construct()


  private def construct(): Unit = {
    refreshCrewBoxes()
    this.add(contentTable).expand.left.bottom
  }

  def refreshCrewBoxes(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val crewBox: hud.CrewmateBox = new hud.CrewmateBox(hudStage, crewmate)
      contentTable.add(crewBox).width(128).height(60).padRight(4)
    }
  }
}
