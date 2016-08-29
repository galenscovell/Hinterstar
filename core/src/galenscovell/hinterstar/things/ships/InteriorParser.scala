package galenscovell.hinterstar.things.ships

import java.io._

import com.badlogic.gdx.Gdx
import galenscovell.hinterstar.generation.interior.Tile

import scala.collection.mutable.ArrayBuffer


class InteriorParser(ship: Ship) {
  private val rootShip: Ship = ship
  private val targetShipName: String = {
    if (ship.isPlayerShip) {
      ship.getName
    } else {
      ship.getName + "-reversed"
    }
  }
  private val tiles: ArrayBuffer[Array[Tile]] = ArrayBuffer()
  private var width: Int = 0
  private var height: Int = 0
  var tileSize: Int = 0

  parse()
  setTileNeighbors()



  def getTiles: Array[Array[Tile]] = {
    tiles.toArray
  }

  private def parse(): Unit = {
    val reader: BufferedReader = Gdx.files.internal("data/ship_interiors.txt").reader(1024)

    var shipFound: Boolean = false
    var searching: Boolean = true
    var y = 0

    var line: String = reader.readLine()
    while (line != null && searching) {
      if (shipFound && line == "END") {
        searching = false
      } else if (shipFound) {
        val subsystemRow: Array[Tile] = Array.ofDim(width)

        for (x <- 0 until line.length) {
          line(x) match {
            case 'W' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Weapon Control", true, rootShip.isPlayerShip, true)
            case 'E' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Engine Room", false, rootShip.isPlayerShip, true)
            case 'H' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Helm", false, rootShip.isPlayerShip, true)
            case 'S' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Shield Control", false, rootShip.isPlayerShip, true)
            case 'M' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Medbay", false, rootShip.isPlayerShip, true)
            case '-' => subsystemRow(x) = new Tile(x, y, tileSize, height, "none", false, rootShip.isPlayerShip, true)
            case '.' => subsystemRow(x) = new Tile(x, y, tileSize, height, "none", false, rootShip.isPlayerShip, false)
          }
        }

        tiles.append(subsystemRow)
        y += 1
      }

      if (line == targetShipName) {
        shipFound = true
        val dimLine: String = reader.readLine()
        val splitLine: Array[String] = dimLine.split(",")
        width = Integer.valueOf(splitLine(0))
        height = Integer.valueOf(splitLine(1))
        tileSize = Integer.valueOf(splitLine(2))
      }

      line = reader.readLine()
    }

    reader.close()
  }

  private def setTileNeighbors(): Unit = {
    for (row: Array[Tile] <- getTiles) {
      for (tile: Tile <- row) {
        val neighbors: ArrayBuffer[Tile] = ArrayBuffer()

        for (dx: Int <- -1 to 1) {
          val newX: Int = tile.getTx + dx
          if (!isOutOfBounds(newX, tile.getTy)) {
            val neighbor: Tile = tiles(tile.getTy)(newX)
            neighbors.append(neighbor)
          }
        }

        for (dy: Int <- -1 to 1) {
          val newY: Int = tile.getTy + dy
          if (!isOutOfBounds(tile.getTx, newY)) {
            val neighbor: Tile = tiles(newY)(tile.getTx)
            neighbors.append(neighbor)
          }
        }

        tile.setNeighbors(neighbors.toArray)
      }
    }
  }

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || y < 0 || x >= width || y >= height
  }
}
