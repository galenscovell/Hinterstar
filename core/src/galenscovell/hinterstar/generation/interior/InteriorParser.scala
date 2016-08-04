package galenscovell.hinterstar.generation.interior

import scala.collection.mutable.ArrayBuffer
import scala.io.{BufferedSource, Source}


/**
  * Contains a grid of tiles representing the subsystem actors for a given ship.
  */
class InteriorParser(shipName: String) {
  private val source: String = "data/ship_interiors.txt"
  private val tiles: ArrayBuffer[Array[Tile]] = ArrayBuffer()
  private var width: Int = 0
  private var height: Int = 0
  var tileSize: Int = 0

  parse(shipName)
  debugPrint()


  def getTiles: Array[Array[Tile]] = {
    tiles.toArray
  }

  private def parse(ship: String): Unit = {
    val bufferedSource: BufferedSource = Source.fromFile(source)

    var shipFound: Boolean = false
    var searching: Boolean = true
    val lines = bufferedSource.getLines.toArray
    var i = 0
    var y = 0

    while (i < lines.length && searching) {
      val line = lines(i)

      if (line == "END") {
        searching = false
      } else if (shipFound) {
        val tileRow: Array[Tile] = Array.ofDim(width)

        for (x <- 0 until line.length) {
          line(x) match {
            case 'W' => tileRow(x) = new Tile(x, y, "Weapon Control")
            case 'E' => tileRow(x) = new Tile(x, y, "Engine Room")
            case 'H' => tileRow(x) = new Tile(x, y, "Helm")
            case 'S' => tileRow(x) = new Tile(x, y, "Shield Control")
            case _ => tileRow(x) = new Tile(x, y, "None")
          }
        }

        tiles.append(tileRow)
        y += 1
      }

      if (line == ship) {
        shipFound = true
        i += 1
        val dimLine: String = lines(i)
        val splitLine: Array[String] = dimLine.split(",")
        width = splitLine(0).toInt
        height = splitLine(1).toInt
        tileSize = splitLine(2).toInt
      }

      i += 1
    }

    bufferedSource.close()
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
