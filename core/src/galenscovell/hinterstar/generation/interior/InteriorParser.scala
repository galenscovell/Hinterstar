package galenscovell.hinterstar.generation.interior

import java.io._

import com.badlogic.gdx.Gdx

import scala.collection.mutable.ArrayBuffer


/**
  * Contains a grid of subsystems representing the subsystem actors for a given ship.
  */
class InteriorParser(shipName: String) {
  private val subsystems: ArrayBuffer[Array[Subsystem]] = ArrayBuffer()
  private var width: Int = 0
  private var height: Int = 0
  var subsystemSize: Int = 0

  parse(shipName)
  // debugPrint()


  def getSubsystems: Array[Array[Subsystem]] = {
    subsystems.toArray
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
        val subsystemRow: Array[Subsystem] = Array.ofDim(width)

        for (x <- 0 until line.length) {
          line(x) match {
            case 'W' => subsystemRow(x) = new Subsystem(x, y, subsystemSize, "Weapon Control")
            case 'E' => subsystemRow(x) = new Subsystem(x, y, subsystemSize, "Engine Room")
            case 'H' => subsystemRow(x) = new Subsystem(x, y, subsystemSize, "Helm")
            case 'S' => subsystemRow(x) = new Subsystem(x, y, subsystemSize, "Shield Control")
            case _ => subsystemRow(x) = new Subsystem(x, y, subsystemSize, "None")
          }
        }

        subsystems.append(subsystemRow)
        y += 1
      }

      if (line == ship) {
        shipFound = true
        val dimLine: String = reader.readLine()
        val splitLine: Array[String] = dimLine.split(",")
        width = Integer.valueOf(splitLine(0))
        height = Integer.valueOf(splitLine(1))
        subsystemSize = Integer.valueOf(splitLine(2))
      }

      line = reader.readLine()
    }

    reader.close()
  }

  private def debugPrint(): Unit = {
    println("Parsed interior")
    for (row: Array[Subsystem] <- subsystems) {
      println()
      for (tile: Subsystem <- row) {
        print(tile.debugDraw)
      }
    }
    println()
  }
}
