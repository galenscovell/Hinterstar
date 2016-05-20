package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table


class ShipSelectionPanel extends Table {
  private val ship: String = "Default Dandy"

  construct()


  def getShip: String = {
    ship
  }

  private def construct(): Unit = {

  }
}
