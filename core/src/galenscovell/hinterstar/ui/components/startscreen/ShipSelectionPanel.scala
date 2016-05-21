package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util.ResourceManager


class ShipSelectionPanel extends Table {
  private val ship: String = "Default Dandy"

  construct()


  def getShip: String = {
    ship
  }

  private def construct(): Unit = {
    val topTable: Table = new Table
    topTable.setBackground(ResourceManager.npTest1)

    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)

    add(topTable).width(690).height(200).center
    row
    add(bottomTable).width(690).height(190).padTop(10).center
  }
}
