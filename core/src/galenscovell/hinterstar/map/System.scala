package galenscovell.hinterstar.map

import java.util._

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util.{Constants, SystemRepo}

import scala.collection.mutable.ArrayBuffer


/**
  * A System contains various planets and other events.
  * Systems have:
  *     an associated SystemMarker (aka a node on the map grid)
  *     randomized Events
  *     randomized background
  */
class System(gridX: Int, gridY: Int, gridSize: Int) {
  val x: Int = gridX
  val y: Int = gridY
  val size: Int = gridSize

  private val events: ArrayBuffer[Event] = new ArrayBuffer[Event]()
  private var details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var systemMarker: SystemMarker = _
  private var currentEvent: Int = 0


  /**
    * Return the SystemMarker for this Location.
    */
  def getSystemMarker: SystemMarker = {
    systemMarker
  }

  /**
    * Return the System title and subtitle detail strings.
    */
  def getDetails: Array[String] = {
    details
  }

  /**
    * Return array of Events for this System.
    */
  def getEvents: ArrayBuffer[Event] = {
    events
  }

  /**
    * Return the current Event for this System.
    */
  def getCurrentEvent: Event = {
    events(currentEvent)
  }

  /**
    * Return the distance to the next Event in this System.
    */
  def getDistanceToNextEvent: Float = {
    if (currentEvent == 0) {
      events(currentEvent).getDistance
    } else {
      events(currentEvent + 1).getDistance
    }
  }



  /**
    * Set the SystemMarker for this Location and initialize it is as being unexplored.
    */
  def setSystemMarker(newSystemMarker: SystemMarker): Unit = {
    this.systemMarker = newSystemMarker
    systemMarker.becomeUnexplored()
  }

  /**
    * Set the System title and subtitle detail strings.
    */
  def setDetails(details: Array[String]): Unit = {
    this.details = details
  }

  /**
    * Set this System as the starting System for the game (the tutorial System).
    */
  def setAsTutorial(): Unit = {
    this.details = Array("Sol System", "Humanities Last Stand")
    this.events.clear()
    val startingEvent: Event = new Event(0)
    startingEvent.setStartEvent()
    this.events.append(startingEvent)
  }



  /**
    * Enter this System, causing System Events and background to be generated.
    * Events and background should be consistent with the type/atmosphere of this System.
    */
  def enter(): Unit = {
    val random = new Random()
    createBackground(random)
    createEvents(random)
  }

  /**
    * Increment the current Event for this System.
    */
  def nextEvent(): Unit = {
    currentEvent += 1
  }

  /**
    * Create random Events for this System.
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
    * Create a random background for this System.
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

    SystemRepo.gameScreen.transitionSector(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
