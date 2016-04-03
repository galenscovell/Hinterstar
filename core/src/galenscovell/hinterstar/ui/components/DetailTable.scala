package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.util.ResourceManager


class DetailTable(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var day: Int = 0
  private var month: Int = 0
  private var year: Int = 0
  private var dateLabel: Label = null
  construct()


  private def construct(): Unit = {
    this.setBackground(ResourceManager.np_test4)
    this.day = 1
    this.month = 1
    this.year = 2500
    val labelTable: Table = new Table
    this.dateLabel = new Label(dateAsString, ResourceManager.label_mediumStyle)
    labelTable.add(dateLabel).expand.fill
    this.add(labelTable).expand.fill.padTop(10).padLeft(10)
  }

  def updateDate(): Unit = {
    if (day < 12) {
      day += 1
    }
    else {
      day = 1
      if (month < 12) {
        month += 1
      }
      else {
        month = 1
        year += 1
      }
    }
    dateLabel.setText(dateAsString)
  }

  private def dateAsString: String = {
    return String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year)
  }
}
