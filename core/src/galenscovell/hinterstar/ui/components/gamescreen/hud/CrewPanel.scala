package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class CrewPanel(hudStage: HudStage) extends Table {
  private val contentTable: Table = new Table

  construct()



  private def construct(): Unit = {
    refreshCrewBoxes()
    this.add(contentTable).expand.left.bottom
  }

  def refreshCrewBoxes(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      contentTable.add(crewmate.getCrewTable(true)).width(128).height(60).padRight(4)
    }
  }
}
