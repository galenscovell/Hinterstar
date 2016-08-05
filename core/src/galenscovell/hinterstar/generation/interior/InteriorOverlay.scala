package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.scenes.scene2d.ui.Table


/**
  * The actor grid of subsystems generated from InteriorParser.
  * Displayed at player will, crewmates can be sent to the subsystem selected.
  */
class InteriorOverlay(shipName: String) extends Table {
  private val interiorParser: InteriorParser = new InteriorParser(shipName)
  private val tiles: Array[Array[Tile]] = interiorParser.getTiles

  construct()


  private def construct(): Unit = {
    // this.setDebug(true)

    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        this.add(tile)
          .width(interiorParser.tileSize)
          .height(interiorParser.tileSize)
      }
      this.row
    }
  }
}
