package galenscovell.hinterstar.map

import java.util._

import galenscovell.hinterstar.processing.EventContainer
import galenscovell.hinterstar.util.{Constants, Repository}

import scala.collection.mutable.ArrayBuffer


class Location(xIn: Int, yIn: Int, sizeIn: Int) {
  final val x: Int = xIn
  final val y: Int = yIn
  final val size: Int = sizeIn

  private var events: ArrayBuffer[EventContainer] = new ArrayBuffer[EventContainer]()
  private var details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var sector: Sector = null
  private var currentEvent: Int = 0


  /*
   * Set the Sector for this Location and initialize it is as being unexplored.
   */
  def setSector(sectorIn: Sector): Unit = {
    this.sector = sectorIn
    sector.becomeUnexplored()
  }

  /*
   * Return the Sector for this Location.
   */
  def getSector: Sector = {
    sector
  }

  /*
   * Enter this Location, causing Location Events and background to be generated.
   * Events and background should be consistent with the type/atmosphere of this Location.
   */
  def enter(): Unit = {
    val random = new Random()
    createBackground(random)
    createEvents(random)
  }

  /*
   * Set the Location title and subtitle detail strings.
   */
  def setDetails(details: Array[String]): Unit = {
    this.details = details
  }

  /*
   * Set this Location as the starting location for the game (it's special!).
   */
  def setStartLocation(): Unit = {
    this.details = Array("Sol Sector", "Humanities Last Stand")
    this.events.clear()
    val startingEvent: EventContainer = new EventContainer(0)
    startingEvent.setStartEvent()
    this.events.append(startingEvent)
  }

  /*
   * Return the Location title and subtitle detail strings.
   */
  def getDetails: Array[String] = {
    details
  }

  /*
   * Return array of Events for this Location.
   */
  def getEvents: ArrayBuffer[EventContainer] = {
    events
  }

  /*
   * Return the current Event for this Location.
   */
  def getCurrentEvent: EventContainer = {
    events(currentEvent)
  }

  /*
   * Increment the current Event for this Location.
   */
  def nextEvent(): Unit = {
    currentEvent += 1
  }

  /*
   * Return the distance to the next Event.
   */
  def getDistanceToNextEvent: Float = {
    if (currentEvent == 0) {
      events(currentEvent).getDistance
    } else {
      events(currentEvent + 1).getDistance
    }
  }

  /*
   * Create random Events for this Location.
   */
  private def createEvents(random: Random): Unit =  {
    val distances: ArrayBuffer[Float] = new ArrayBuffer[Float]()

    while (distances.sum < Constants.PROGRESS_PANEL_WIDTH) {
      val newDistance: Float = random.nextInt(100) + 100 + 3f
      if (distances.sum + newDistance > Constants.PROGRESS_PANEL_WIDTH) {
        distances += (Constants.PROGRESS_PANEL_WIDTH - distances.sum)
      } else {
        distances += newDistance
      }
    }

    for (x <- distances.indices) {
      events += new EventContainer(distances(x))
    }
  }

  /*
   * Create a random background for this Location.
   */
  private def createBackground(random: Random): Unit = {
    // Background depends on number and type of events generated
    // eg many planet events = background has planets
    // eg no planet events = background has no planets
    val num: Int = random.nextInt(4)
    var layerName: String = null

    num match {
      case 0 => layerName = "blue_bg"
      case 1 => layerName = "purple_bg"
      case 2 => layerName = "green_bg"
      case _ => layerName = ""
    }

    Repository.gameScreen.transitionSector(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
