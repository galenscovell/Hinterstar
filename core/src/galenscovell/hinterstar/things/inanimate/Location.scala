package galenscovell.hinterstar.things.inanimate

import java.util._

import galenscovell.hinterstar.map.Sector
import galenscovell.hinterstar.things.inanimate.Events.Event
import galenscovell.hinterstar.util.Repository

import scala.collection.mutable.ArrayBuffer


class Location(xIn: Int, yIn: Int, sizeIn: Int) {
  final val x: Int = xIn
  final val y: Int = yIn
  final val size: Int = sizeIn
  private val events: ArrayBuffer[Event] = ArrayBuffer()
  private val details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var sector: Sector = null


  def setSector(sectorIn: Sector): Unit = {
    this.sector = sectorIn
    sector.becomeUnexplored()
  }

  def getSector: Sector = {
    sector
  }

  def enter(): Unit = {
    generateEvents(new Random)
  }

  def getDetails: Array[String] = {
    details
  }

  private def generateEvents(random: Random): Unit =  {
    // Each Location has between 1 and 4 events
    this.events.clear()
    val numberOfEvents: Int = random.nextInt(4) + 1

    for (event <- 0 until numberOfEvents) {

    }
    // Eventually background should be consistent and not created here
    createBackground(random)
  }

  private def createBackground(random: Random): Unit = {
    // Background depends on number and type of events generated
    // eg many planet events = background has planets
    // eg no planet events = background has no planets
    val num: Int = random.nextInt(4)
    var layerName: String = null

    if (num == 0) {
      layerName = "blue_bg"
    }
    else if (num == 1) {
      layerName = "purple_bg"
    }
    else if (num == 2) {
      layerName = "green_bg"
    }
    else {
      layerName = ""
    }
    Repository.gameScreen.setBackground(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
