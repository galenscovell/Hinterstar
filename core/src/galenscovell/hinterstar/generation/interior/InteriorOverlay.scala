package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.ships.Ship


class InteriorOverlay(ship: Ship) extends Table {
  private val interiorParser: InteriorParser = new InteriorParser(ship)
  private val subsystems: Array[Array[Tile]] = interiorParser.getTiles

  construct()


  private def construct(): Unit = {
    // this.setDebug(true)

    for (row: Array[Tile] <- subsystems) {
      for (subsystem: Tile <- row) {
        this.add(subsystem)
          .width(interiorParser.tileSize)
          .height(interiorParser.tileSize)
      }
      this.row
    }
  }
}
