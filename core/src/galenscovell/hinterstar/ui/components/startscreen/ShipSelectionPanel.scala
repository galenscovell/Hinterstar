package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.{Ship, ShipParser}
import galenscovell.hinterstar.util.ResourceManager


class ShipSelectionPanel extends Table {
  private val allShips: Array[Ship] = new ShipParser().parseAll
  private val selectedShip: Ship = null

  construct()


  def getShip: Ship = {
    selectedShip
  }

  private def construct(): Unit = {
    val topTable: Table = new Table
    topTable.setBackground(ResourceManager.npTest1)

    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)

    add(topTable).width(690).height(200).center
    row
    add(bottomTable).width(690).height(190).padTop(10).center

    for (s: Ship <- allShips) {
      println(s.getDescription)
    }
  }
}
