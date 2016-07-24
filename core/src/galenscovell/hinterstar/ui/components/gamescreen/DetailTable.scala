package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util._


class DetailTable(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var locationLabel: Label = _
  private var infoTable: Table = _

  construct()


  private def construct(): Unit = {
    this.setBackground(Resources.npTest4)
    val mainTable: Table = new Table

    val labelTable: Table = new Table
    labelTable.setBackground(Resources.npTest1)
    this.locationLabel = new Label("Tutorial System", Resources.labelMediumStyle)
    locationLabel.setAlignment(Align.center, Align.right)

    labelTable.add(locationLabel).expand.fill.right.padRight(10)

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

  def updateLocation(loc: String): Unit = {
    locationLabel.setText(loc)
  }
}
