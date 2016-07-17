package galenscovell.hinterstar.map

import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer


/**
  * MapGenerator constructs Sector grid and places non-overlapping Locations.
  */
class MapGenerator(maxLocations: Int, padSize: Int) {
  private val sectors: Array[Array[SystemMarker]] = Array.ofDim[SystemMarker](Constants.MAPHEIGHT, Constants.MAPWIDTH)
  private val locations: ArrayBuffer[System] = ArrayBuffer()

  build()
  placeLocations()
  SystemRepo.populateLocations(locations)


  /**
    * Return 2D Array of Sectors.
    */
  def getSectors: Array[Array[SystemMarker]] = {
    sectors
  }

  /**
    * Construct Sector[MAPHEIGHT][MAPWIDTH] sectors of all empty Sectors
    */
  private def build(): Unit = {
    for (x <- 0 until Constants.MAPWIDTH) {
      for (y <- 0 until Constants.MAPHEIGHT) {
        sectors(y)(x) = new SystemMarker(x, y)
      }
    }
  }

  /**
    * Place random Locations, ensuring that they are distanced apart.
    */
  private def placeLocations(): Unit = {
    var attempts: Int = 240

    while (attempts > 0 && locations.length < maxLocations) {
      val x: Int = getRandom(1, Constants.MAPWIDTH - padSize - 1)
      val y: Int = getRandom(1, Constants.MAPHEIGHT - padSize - 1)
      if (!(x == 0 || x == Constants.MAPWIDTH || y == 0 || y == Constants.MAPHEIGHT)) {
        val location: System = new System(x, y, padSize)
        if (!doesCollide(location, -1)) {
          val centerX: Int = (location.size / 2) + location.x
          val centerY: Int = (location.size / 2) + location.y
          location.setSystemMarker(sectors(centerY)(centerX))
          locations += location
        }
      }
      attempts -= 1
    }
  }

  /**
    * Return if target Location overlaps an already placed Location.
    */
  private def doesCollide(location: System, ignore: Int): Boolean = {
    for (i <- locations.indices) {
      if (i != ignore) {
        val check: System = locations(i)
        if (!((location.x + location.size < check.x - 2) || (location.x - 2 > check.x + check.size) || (location.y + location.size < check.y - 2) || (location.y - 2 > check.y + check.size))) {
          return true
        }
      }
    }
    false
  }

  /**
    * Return a random integer between low and high.
    */
  private def getRandom(low: Int, high: Int): Int = {
    (Math.random * (high - low)).toInt + low
  }
}

