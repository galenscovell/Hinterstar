package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.{hud, stages}
import galenscovell.hinterstar.util._


class CrewPanel(stage: stages.ActionStage) extends Table {
  private val gameStage: stages.ActionStage = stage

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    val contentTable: Table = new Table

    createCrewBoxes(contentTable)

    mainTable.add(contentTable).expand.left.bottom
    this.add(mainTable).expand.fill
  }

  private def createCrewBoxes(container: Table): Unit = {
    container.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val crewBox: hud.CrewmateBox = new hud.CrewmateBox(gameStage, crewmate)
      container.add(crewBox).width(112).height(64).padRight(4)
    }
  }
}
