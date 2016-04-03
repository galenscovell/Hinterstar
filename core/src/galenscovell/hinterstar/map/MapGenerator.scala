package galenscovell.hinterstar.map

import galenscovell.hinterstar.things.inanimate.Location
import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer


class MapGenerator {
  private final val sectors: Array[Array[Sector]] = Array.ofDim[Sector](Constants.MAPHEIGHT, Constants.MAPWIDTH)
  private val locations: ArrayBuffer[Location] = ArrayBuffer()
  build()
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
    placeDestinations()
  }

  private def placeDestinations(): Unit = {
    // Place random Locations, ensuring that they are distanced apart
    var attempts: Int = 480
    val maxLocations: Int = 12
    val padsize: Int = 4

    while (attempts > 0 && locations.length < maxLocations) {
      val x: Int = getRandom(1, Constants.MAPWIDTH - padsize - 1)
      val y: Int = getRandom(1, Constants.MAPHEIGHT - padsize - 1)
      if (!(x == 0 || x == Constants.MAPWIDTH || y == 0 || y == Constants.MAPHEIGHT)) {
        val location: Location = new Location(x, y, padsize)
        if (!(doesCollide(location, -1))) {
          val centerX: Int = (location.size / 2) + location.x
          val centerY: Int = (location.size / 2) + location.y
          location.setSector(sectors(centerY)(centerX))
          this.locations += location
        }
      }
      attempts -= 1
    }
  }

  private def doesCollide(location: Location, ignore: Int): Boolean = {
    // Return if target location overlaps already placed location
    for (i <- this.locations.indices) {
      if (i != ignore) {
        val check: Location = this.locations(i)
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

