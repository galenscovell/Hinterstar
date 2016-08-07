package galenscovell.hinterstar.generation.interior

import java.io._

import com.badlogic.gdx.Gdx

import scala.collection.mutable.ArrayBuffer


/**
  * Contains a grid of tiles representing the subsystem actors for a given ship.
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
      if (line == "END") {
        searching = false
      } else if (shipFound) {
        val tileRow: Array[Tile] = Array.ofDim(width)

        for (x <- 0 until line.length) {
          line(x) match {
            case 'W' => tileRow(x) = new Tile(x, y, tileSize, "Weapon Control")
            case 'E' => tileRow(x) = new Tile(x, y, tileSize, "Engine Room")
            case 'H' => tileRow(x) = new Tile(x, y, tileSize, "Helm")
            case 'S' => tileRow(x) = new Tile(x, y, tileSize, "Shield Control")
            case _ => tileRow(x) = new Tile(x, y, tileSize, "None")
          }
        }

        tiles.append(tileRow)
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
