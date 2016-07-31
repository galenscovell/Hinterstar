package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.util._


class ShipStatsPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private val shipStatsTable: Table = new Table
  private val statLabels: Array[String] = Array("Shield", "Evasion", "Weapons")

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

    val stats: Array[Int] = PlayerData.getShipStats

    for (i <- stats.indices) {
      val statTable: Table = new Table
      statTable.setBackground(Resources.npTest4)

      val statKeyTable: Table = new Table
      val statLabel: Label = new Label(statLabels(i), Resources.labelTinyStyle)
      statKeyTable.add(statLabel).expand.fill.center.pad(4)

      val statValueTable: Table = new Table
      val statValueLabel: Label = new Label(stats(i).toString, Resources.labelTinyStyle)
      statValueTable.add(statValueLabel).expand.center.pad(4)

      statTable.add(statKeyTable).expand.left
      statTable.add(statValueTable).expand.fill.center

      shipStatsTable.add(statTable)
        .width(80)
        .height(32)
        .center
        .pad(2)
    }
  }
}
