package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util._


class DetailTable(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var day: Int = 1
  private var month: Int = 1
  private var year: Int = 2500
  private var dateLabel: Label = _
  private var locationLabel: Label = _
  private var infoTable: Table = _

  construct()


  private def construct(): Unit = {
    this.setBackground(Resources.npTest4)
    val mainTable: Table = new Table

    val labelTable: Table = new Table
    labelTable.setBackground(Resources.npTest1)
    this.dateLabel = new Label(dateAsString, Resources.labelMediumStyle)
    dateLabel.setAlignment(Align.center, Align.left)
    this.locationLabel = new Label("Sol System", Resources.labelMediumStyle)
    locationLabel.setAlignment(Align.center, Align.right)

    labelTable.add(dateLabel).width(Constants.EXACT_X / 2.2f).left
    labelTable.add(locationLabel).width(Constants.EXACT_X / 2.2f).right

    this.infoTable = new Table
    infoTable.setBackground(Resources.npTest1)

    mainTable.add(infoTable).expand.fill
    mainTable.row
    mainTable.add(labelTable)
      .height(Constants.SYSTEMMARKER_SIZE * 2)
      .width(Constants.EXACT_X)

    this.add(mainTable).expand.fill
  }

  def establishInfoPanel(): Unit = {
    infoTable.clear()
    val infoPanel: InfoPanel = new InfoPanel(this)
    infoTable.add(infoPanel).expand.fill
  }

  def updateDate(): Unit = {
    if (day < 12) {
      day += 1
    } else {
      day = 1
      if (month < 12) {
        month += 1
      } else {
        month = 1
        year += 1
      }
    }
    dateLabel.setText(dateAsString)
  }

  def updateLocation(loc: String): Unit = {
    locationLabel.setText(loc)
  }

  private def dateAsString: String = {
    String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year)
  }
}
