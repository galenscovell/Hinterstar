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

  construct()


  private def construct(): Unit = {
    this.setBackground(ResourceManager.np_test4)
    val mainTable: Table = new Table

    val labelTable: Table = new Table
    labelTable.setBackground(ResourceManager.np_test1)
    this.dateLabel = new Label(dateAsString, ResourceManager.label_mediumStyle)
    dateLabel.setAlignment(Align.center, Align.left)
    this.locationLabel = new Label("Sol Sector", ResourceManager.label_mediumStyle)
    locationLabel.setAlignment(Align.center, Align.right)

    labelTable.add(dateLabel).width(390).left
    labelTable.add(locationLabel).width(390).right

    val progressTable: Table = new Table
    progressTable.setBackground(ResourceManager.np_test1)

    mainTable.add(labelTable).height(20).width(Constants.EXACT_X - 10).top.pad(5)
    mainTable.row
    mainTable.add(progressTable).expand.fill.bottom.pad(5)

    this.add(mainTable).expand.fill
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
