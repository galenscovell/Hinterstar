package galenscovell.hinterstar.generation.sector

import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer


class SectorGenerator(maxSystems: Int, padSize: Int) {
  private val systemMarkers: Array[Array[SystemMarker]] = Array.ofDim[SystemMarker](Constants.MAP_HEIGHT, Constants.MAP_WIDTH)
  private val systems: ArrayBuffer[System] = ArrayBuffer()

  build()
  placeSystems()
  SystemRepo.populateSystems(systems)


  def getSystemMarkers: Array[Array[SystemMarker]] = {
    systemMarkers
  }



  private def build(): Unit = {
    for (x <- 0 until Constants.MAP_WIDTH) {
      for (y <- 0 until Constants.MAP_HEIGHT) {
        systemMarkers(y)(x) = new SystemMarker(x, y)
      }
    }
  }

  private def placeSystems(): Unit = {
    var attempts: Int = 240

    while (attempts > 0 && systems.length < maxSystems) {
      val x: Int = getRandom(1, Constants.MAP_WIDTH - padSize - 1)
      val y: Int = getRandom(1, Constants.MAP_HEIGHT - padSize - 1)
      if (!(x == 0 || x == Constants.MAP_WIDTH || y == 0 || y == Constants.MAP_HEIGHT)) {
        val system: System = new System(x, y, padSize)
        if (!doesCollide(system)) {
          val centerX: Int = (system.size / 2) + system.x
          val centerY: Int = (system.size / 2) + system.y
          val systemMarker: SystemMarker = systemMarkers(centerY)(centerX)
          system.setSystemMarker(systemMarker)
          systemMarker.setSystem(system)
          systems += system
        }
      }
      attempts -= 1
    }
  }

  private def doesCollide(system: System): Boolean = {
    for (i <- systems.indices) {
      val check: System = systems(i)
      if (!((system.x + system.size < check.x - 2) ||
            (system.x - 2 > check.x + check.size) ||
            (system.y + system.size < check.y - 2) ||
            (system.y - 2 > check.y + check.size))) {
        return true
      }
    }
    false
  }

  private def getRandom(low: Int, high: Int): Int = {
    (Math.random * (high - low)).toInt + low
  }

  private def debugPrint(): Unit = {
    println()
    for (row: Array[SystemMarker] <- systemMarkers) {
      for (sm: SystemMarker <- row) {
        print(sm.debugDraw())
      }
      println()
    }
    println()
  }
}

