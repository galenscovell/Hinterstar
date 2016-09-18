package galenscovell.hinterstar.things.ships

import java.io.BufferedReader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.Constants

import scala.collection.mutable.ArrayBuffer


class Interior() extends Table {
  private val tiles: ArrayBuffer[Array[Tile]] = ArrayBuffer()

  parse()
  construct()
  setTileNeighbors()


  private def construct(): Unit = {
//    this.setDebug(true)
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        this.add(tile).width(Constants.TILE_WIDTH).height(Constants.TILE_HEIGHT)
      }
      this.row
    }
  }

  private def parse(): Unit = {
    val reader: BufferedReader = Gdx.files.internal("data/interiors.txt").reader(1024)

    var shipFound: Boolean = false
    var searching: Boolean = true
    var y = 0

    var line: String = reader.readLine()
    while (line != null && searching) {
      if (shipFound && line == "END") {
        searching = false
      } else if (shipFound) {
        val tileRow: Array[Tile] = Array.ofDim(Constants.ROOM_COLUMNS)

        for (x <- 0 until line.length) {
          line(x) match {
            case 'T' => tileRow(x) = new Tile(x, y, "Turret")
            case 'E' => tileRow(x) = new Tile(x, y, "Engine")
            case 'H' => tileRow(x) = new Tile(x, y, "Helm")
            case 'S' => tileRow(x) = new Tile(x, y, "Shield")
            case 'M' => tileRow(x) = new Tile(x, y, "Medbay")
            case 'L' => tileRow(x) = new Tile(x, y, "Life Support")
            case 'G' => tileRow(x) = new Tile(x, y, "Generator")
            case 'R' => tileRow(x) = new Tile(x, y, "Sensor")
            case 'P' => tileRow(x) = new Tile(x, y, "Sleep Pod")
            case '-' => tileRow(x) = new Tile(x, y, "floor")
            case '^' => tileRow(x) = new Tile(x, y, "ladder-up")
            case 'v' => tileRow(x) = new Tile(x, y, "ladder-down")
            case '.' => tileRow(x) = new Tile(x, y, "empty")
          }
        }

        tiles.append(tileRow)
        y += 1
      }

      if (line == "placeholder_vehicle") {
        shipFound = true
      }

      line = reader.readLine()
    }

    reader.close()
  }

  private def setTileNeighbors(): Unit = {
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        val neighbors: ArrayBuffer[Tile] = ArrayBuffer()

        for (dx: Int <- -1 to 1) {
          val newX: Int = tile.tx + dx
          if (!isOutOfBounds(newX, tile.ty)) {
            val neighbor: Tile = tiles(tile.ty)(newX)
            neighbors.append(neighbor)
          }
        }

        for (dy: Int <- -1 to 1) {
          val newY: Int = tile.ty + dy
          if (!isOutOfBounds(tile.tx, newY)) {
            val neighbor: Tile = tiles(newY)(tile.tx)
            neighbors.append(neighbor)
          }
        }

        tile.setNeighbors(neighbors.toArray)
      }
    }
  }

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || y < 0 || x >= Constants.ROOM_COLUMNS || y >= Constants.ROOM_ROWS
  }
}
