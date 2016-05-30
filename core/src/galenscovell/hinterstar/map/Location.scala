package galenscovell.hinterstar.map

import java.util._

import galenscovell.hinterstar.processing.Events.{Event, Planet}
import galenscovell.hinterstar.util.{Constants, Repository}

import scala.collection.mutable.ArrayBuffer


class Location(xIn: Int, yIn: Int, sizeIn: Int) {
  final val x: Int = xIn
  final val y: Int = yIn
  final val size: Int = sizeIn
  private val events: ArrayBuffer[Event] = ArrayBuffer[Event]()
  private val details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var sector: Sector = null
  private var currentEvent: Int = 0


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


  def getEvents: ArrayBuffer[Event] = {
    events
  }


  def getCurrentEvent: Event = {
    events(currentEvent)
  }


  def nextEvent(): Unit = {
    currentEvent += 1
  }


  def getDistanceToNextEvent: Float = {
    if (currentEvent == 0) {
      events(currentEvent).getDistance
    } else {
      events(currentEvent + 1).getDistance
    }
  }


  private def generateEvents(random: Random): Unit =  {
    this.events.clear()

    // Randomly set distances between events
    val distances: ArrayBuffer[Float] = new ArrayBuffer[Float]()

    while (distances.sum < Constants.PROGRESS_PANEL_WIDTH) {
      val newDistance: Float = random.nextInt(100) + 100 + 3f
      if (distances.sum + newDistance > Constants.PROGRESS_PANEL_WIDTH) {
        distances += (Constants.PROGRESS_PANEL_WIDTH - distances.sum)
      } else {
        distances += newDistance
      }
    }

    for (x <- 0 until distances.length) {
      events += new Planet(distances(x))
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
    Repository.gameScreen.transitionSector(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
