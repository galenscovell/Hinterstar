package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table


class ShipSelectionPanel extends Table {
  private val ship: String = "Default Dandy"

  construct()


  private def construct(): Unit = {

  }

  def getShip: String = {
    ship
  }
}
