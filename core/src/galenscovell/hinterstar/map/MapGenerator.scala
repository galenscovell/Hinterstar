package galenscovell.hinterstar.map

import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer


class MapGenerator(maxLocations: Int, padSize: Int) {
  private final val sectors: Array[Array[Sector]] = Array.ofDim[Sector](Constants.MAPHEIGHT, Constants.MAPWIDTH)
  private val locations: ArrayBuffer[Location] = ArrayBuffer()

  build()
  placeLocations()
  Repository.populateLocations(locations)


  def getSectors: Array[Array[Sector]] = {
    sectors
  }


  private def build(): Unit = {
    // Construct Sector[MAPHEIGHT][MAPWIDTH] sectors of all empty Sectors
    for (x <- 0 until Constants.MAPWIDTH) {
      for (y <- 0 until Constants.MAPHEIGHT) {
        sectors(y)(x) = new Sector(x, y)
      }
    }
  }


  private def placeLocations(): Unit = {
    // Place random Locations, ensuring that they are distanced apart
    var attempts: Int = 240

    while (attempts > 0 && locations.length < maxLocations) {
      val x: Int = getRandom(1, Constants.MAPWIDTH - padSize - 1)
      val y: Int = getRandom(1, Constants.MAPHEIGHT - padSize - 1)
      if (!(x == 0 || x == Constants.MAPWIDTH || y == 0 || y == Constants.MAPHEIGHT)) {
        val location: Location = new Location(x, y, padSize)
        if (!(doesCollide(location, -1))) {
          val centerX: Int = (location.size / 2) + location.x
          val centerY: Int = (location.size / 2) + location.y
          location.setSector(sectors(centerY)(centerX))
          locations += location
        }
      }
      attempts -= 1
    }
  }


  private def doesCollide(location: Location, ignore: Int): Boolean = {
    // Return if target location overlaps already placed location
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


  private def getRandom(lo: Int, hi: Int): Int = {
    (Math.random * (hi - lo)).toInt + lo
  }
}

