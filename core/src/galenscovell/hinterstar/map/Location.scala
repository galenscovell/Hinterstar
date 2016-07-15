package galenscovell.hinterstar.map

import java.util._

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util.{Constants, LocationRepo}

import scala.collection.mutable.ArrayBuffer


/**
  * A Location is an abstraction the Player explores.
  * Locations have:
  *     an associated Sector (aka a node on the map grid)
  *     randomized Events
  *     randomized background
  */
class Location(gridX: Int, gridY: Int, gridSize: Int) {
  val x: Int = gridX
  val y: Int = gridY
  val size: Int = gridSize

  private val events: ArrayBuffer[Event] = new ArrayBuffer[Event]()
  private var details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var sector: Sector = _
  private var currentEvent: Int = 0


  /**
    * Return the Sector for this Location.
    */
  def getSector: Sector = {
    sector
  }

  /**
    * Return the Location title and subtitle detail strings.
    */
  def getDetails: Array[String] = {
    details
  }

  /**
    * Return array of Events for this Location.
    */
  def getEvents: ArrayBuffer[Event] = {
    events
  }

  /**
    * Return the current Event for this Location.
    */
  def getCurrentEvent: Event = {
    events(currentEvent)
  }

  /**
    * Return the distance to the next Event.
    */
  def getDistanceToNextEvent: Float = {
    if (currentEvent == 0) {
      events(currentEvent).getDistance
    } else {
      events(currentEvent + 1).getDistance
    }
  }



  /**
    * Set the Sector for this Location and initialize it is as being unexplored.
    */
  def setSector(sectorIn: Sector): Unit = {
    this.sector = sectorIn
    sector.becomeUnexplored()
  }

  /**
    * Set the Location title and subtitle detail strings.
    */
  def setDetails(details: Array[String]): Unit = {
    this.details = details
  }

  /**
    * Set this Location as the starting location for the game (it's special!).
    */
  def setTutorialLocation(): Unit = {
    this.details = Array("Sol Sector", "Humanities Last Stand")
    this.events.clear()
    val startingEvent: Event = new Event(0)
    startingEvent.setStartEvent()
    this.events.append(startingEvent)
  }



  /**
    * Enter this Location, causing Location Events and background to be generated.
    * Events and background should be consistent with the type/atmosphere of this Location.
    */
  def enter(): Unit = {
    val random = new Random()
    createBackground(random)
    createEvents(random)
  }

  /**
    * Increment the current Event for this Location.
    */
  def nextEvent(): Unit = {
    currentEvent += 1
  }

  /**
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
      events += new Event(distances(x))
    }
  }

  /**
    * Create a random background for this Location.
    * Background depends on number and type of events generated
    * eg many planet events = background has planets
    * eg no planet events = background has no planets
    */
  private def createBackground(random: Random): Unit = {
    val num: Int = random.nextInt(4)
    var layerName: String = null

    num match {
      case 0 => layerName = "blue_bg"
      case 1 => layerName = "purple_bg"
      case 2 => layerName = "green_bg"
      case _ => layerName = ""
    }

    LocationRepo.gameScreen.transitionSector(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
