package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.ships

import scala.collection.mutable.Map


class InteriorOverlay(ship: Ship) extends Table {
  private val interiorParser: ships.InteriorParser = new ships.InteriorParser(ship)
  private val tiles: Array[Array[Tile]] = interiorParser.getTiles

  private val subsystems: Map[String, Tile] = {
    val subsystemMap: Map[String, Tile] = Map()
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        if (tile.isSubsystem) {
          subsystemMap.put(tile.getName, tile)
        }
      }
    }
    subsystemMap
  }

  construct()


  private def construct(): Unit = {
//    this.setDebug(true)
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        this.add(tile).width(interiorParser.tileSize).height(interiorParser.tileSize)
      }
      this.row
    }
  }

  def getSubsystemMap: Map[String, Tile] = {
    subsystems
  }
}
