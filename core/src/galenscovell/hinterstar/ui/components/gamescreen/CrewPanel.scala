package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.{Constants, PlayerData, Resources}


class CrewPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private val contentTable: Table = new Table

  construct()
  update()


  private def construct(): Unit = {
    val mainTable: Table = new Table

    mainTable.add(contentTable).expand.left.bottom
    this.add(mainTable).expand.fill
  }

  private def constructCrewBox(crewmate: Crewmate): Table = {
    val crewBox: Table = new Table
    crewBox.setBackground(Resources.npTest4)

    val crewmateDetail: Table = new Table

    val iconTable: Table = new Table
    val sprite: Image = new Image(Resources.spCrewmate)
    iconTable.add(sprite).expand.fillX

    val detailTop: Table = new Table
    val nameLabel: Label = new Label(crewmate.getName, Resources.labelTinyStyle)
    nameLabel.setAlignment(Align.center)

    // Healthbar established here
    val healthBarTable: Table = new Table
    val healthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.healthBarStyle)
    healthBar.setValue(100)
    healthBarTable.add(healthBar)
      .width(64)
      .height(16)

    detailTop.add(nameLabel).expand.fill.height(Constants.SYSTEMMARKER_SIZE)
    detailTop.row
    detailTop.add(healthBarTable).expand.fill.center

    crewmateDetail.add(iconTable).expand.fill
      .width(32)
      .height(32)
      .left
    crewmateDetail.add(detailTop).expand.fill
      .width(80)
      .height(32)

    val assignmentLabel: Label = new Label(crewmate.getAssignment, Resources.labelTinyStyle)
    assignmentLabel.setAlignment(Align.center)

    crewBox.add(crewmateDetail).expand.fill.center
    crewBox.row
    crewBox.add(assignmentLabel).expand.fill.center
    crewBox
  }

  def update(): Unit = {
    contentTable.clear()

    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val crewBox: Table = constructCrewBox(crewmate)
      contentTable.add(crewBox)
        .width(112)
        .height(64)
        .center
        .padRight(4)
    }
  }
}
