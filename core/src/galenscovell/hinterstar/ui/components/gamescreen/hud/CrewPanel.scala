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
    this.setFillParent(true)

    refreshCrewBoxes()

    this.add(contentTable).expand.left.bottom
  }

  def refreshCrewBoxes(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val crewBox: hud.CrewmateBox = new hud.CrewmateBox(hudStage, crewmate)
      contentTable.add(crewBox).width(112).height(64).padRight(4)
    }
  }
}
