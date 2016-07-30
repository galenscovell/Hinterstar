package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.util._

import scala.collection.mutable


class ShipStatsPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private val shipStatsTable: Table = new Table

  construct()
  refreshStats()


  private def construct(): Unit = {
    val mainTable: Table = new Table

    val shipStatsRoot: Table = new Table
    shipStatsRoot.add(shipStatsTable).expand.fill
    mainTable.add(shipStatsRoot).expand.bottom
    this.add(mainTable).expand.fill
  }

  def refreshStats(): Unit = {
    shipStatsTable.clear()

    val stats: mutable.Map[String, Int] = PlayerData.getShipStats

    for ((k, v) <- stats) {
      val statTable: Table = new Table
      statTable.setBackground(Resources.npTest4)
      shipStatsTable.add(statTable)
          .width(Constants.SYSTEMMARKER_SIZE * 3)
          .height(Constants.SYSTEMMARKER_SIZE * 4)
        .center.pad(6)
      shipStatsTable.row
    }
  }
}
