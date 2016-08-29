package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class CrewPanel(hudStage: HudStage) extends Table {
  private val contentTable: Table = new Table

  construct()



  private def construct(): Unit = {
    refresh()
    this.add(contentTable).expand.left.bottom
  }

  def refresh(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      contentTable.add(crewmate.getCrewBox).width(120).height(60).left.padRight(12)
    }
  }
}
