package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.{Constants, PlayerData, Resources}


class CrewPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private val contentTable: Table = new Table

  construct()
  update()


  private def construct(): Unit = {
    val mainTable: Table = new Table

    mainTable.add(contentTable).expand.left.padLeft(Constants.SYSTEMMARKER_SIZE * 5)
    this.add(mainTable).expand.fill
  }

  private def constructCrewBox(crewmate: Crewmate): Table = {
    val crewBox: Table = new Table
    crewBox.setBackground(Resources.npTest3)

    crewBox
  }

  def update(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val crewBox: Table = constructCrewBox(crewmate)
      contentTable.add(crewBox)
      contentTable.add(crewBox)
        .width(Constants.SYSTEMMARKER_SIZE * 5)
        .height(Constants.SYSTEMMARKER_SIZE * 3)
        .center.pad(6)
    }
  }
}
