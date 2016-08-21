package galenscovell.hinterstar.generation.interior

import java.io._

import com.badlogic.gdx.Gdx

import scala.collection.mutable.ArrayBuffer


/**
  * Contains a grid of Tiles representing the subsystem actors for a given ship.
  */
class InteriorParser(shipName: String) {
  private val tiles: ArrayBuffer[Array[Tile]] = ArrayBuffer()
  private var width: Int = 0
  private var height: Int = 0
  var tileSize: Int = 0

  parse(shipName)
  // debugPrint()


  def getTiles: Array[Array[Tile]] = {
    tiles.toArray
  }

  private def parse(ship: String): Unit = {
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
            case 'W' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Weapon Control", true)
            case 'E' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Engine Room", false)
            case 'H' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Helm", false)
            case 'S' => subsystemRow(x) = new Tile(x, y, tileSize, height, "Shield Control", false)
            case _ => subsystemRow(x) = new Tile(x, y, tileSize, height, "none", false)
          }
        }

        tiles.append(subsystemRow)
        y += 1
      }

      if (line == ship) {
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

  private def debugPrint(): Unit = {
    println("Parsed interior")
    for (row: Array[Tile] <- tiles) {
      println()
      for (tile: Tile <- row) {
        print(tile.debugDraw)
      }
    }
    println()
  }
}
