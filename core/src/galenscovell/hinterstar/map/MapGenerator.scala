package galenscovell.hinterstar.map

import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer


/**
  * MapGenerator constructs Sector grid and places non-overlapping Locations.
  */
class MapGenerator(maxLocations: Int, padSize: Int) {
  private final val sectors: Array[Array[Sector]] = Array.ofDim[Sector](Constants.MAPHEIGHT, Constants.MAPWIDTH)
  private final val locations: ArrayBuffer[Location] = ArrayBuffer()

  build()
  placeLocations()
  Repository.populateLocations(locations)


  /**
    * Return 2D Array of Sectors.
    */
  def getSectors: Array[Array[Sector]] = {
    sectors
  }

  /**
    * Construct Sector[MAPHEIGHT][MAPWIDTH] sectors of all empty Sectors
    */
  private def build(): Unit = {
    for (x <- 0 until Constants.MAPWIDTH) {
      for (y <- 0 until Constants.MAPHEIGHT) {
        sectors(y)(x) = new Sector(x, y)
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
        val location: Location = new Location(x, y, padSize)
        if (!doesCollide(location, -1)) {
          val centerX: Int = (location.size / 2) + location.x
          val centerY: Int = (location.size / 2) + location.y
          location.setSector(sectors(centerY)(centerX))
          locations += location
        }
      }
      attempts -= 1
    }
  }

  /**
    * Return if target Location overlaps an already placed Location.
    */
  private def doesCollide(location: Location, ignore: Int): Boolean = {
    for (i <- locations.indices) {
      if (i != ignore) {
        val check: Location = locations(i)
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

