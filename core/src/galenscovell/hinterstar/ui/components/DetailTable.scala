package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.{Constants, ResourceManager}


class DetailTable(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var day: Int = 1
  private var month: Int = 1
  private var year: Int = 2500
  private var dateLabel: Label = null
  private var locationLabel: Label = null
  private var infoTable: Table = null

  construct()


  private def construct(): Unit = {
    this.setBackground(ResourceManager.npTest4)
    val mainTable: Table = new Table

    val labelTable: Table = new Table
    labelTable.setBackground(ResourceManager.npTest1)
    this.dateLabel = new Label(dateAsString, ResourceManager.labelMediumStyle)
    dateLabel.setAlignment(Align.center, Align.left)
    this.locationLabel = new Label("Sol Sector", ResourceManager.labelMediumStyle)
    locationLabel.setAlignment(Align.center, Align.right)

    labelTable.add(dateLabel).width(384).left
    labelTable.add(locationLabel).width(384).right

    this.infoTable = new Table
    infoTable.setBackground(ResourceManager.npTest1)

    mainTable.add(infoTable).expand.fill.pad(6)
    mainTable.row
    mainTable.add(labelTable).height(20).width(Constants.PROGRESS_PANEL_WIDTH).padLeft(6).padRight(6).padBottom(6)

    this.add(mainTable).expand.fill
  }

  def establishInfoPanel(): Unit = {
    infoTable.clear()
    var infoPanel: InfoPanel = new InfoPanel(this)
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
