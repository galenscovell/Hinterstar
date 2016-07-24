package galenscovell.hinterstar.generation.sector

import java.util._

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util._


/**
  * A System contains is a container for an event.
  * Systems have:
  *     an associated SystemMarker (aka a node on the sector grid)
  *     random Event
  *     random background
  */
class System(gridX: Int, gridY: Int, gridSize: Int) {
  val x: Int = gridX
  val y: Int = gridY
  val size: Int = gridSize

  private var event: Event = new Event()
  private var details: Array[String] = Array[String]("Location Title", "Location Detail")
  private var systemMarker: SystemMarker = _


  /**
    * Return the SystemMarker for this System.
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
    * Return the Event for this System.
    */
  def getEvent: Event = {
    event
  }



  /**
    * Set the SystemMarker for this System and initialize it is as being unexplored.
    */
  def setSystemMarker(newSystemMarker: SystemMarker): Unit = {
    systemMarker = newSystemMarker
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
    details = Array("Tutorial System", "Learn You a Thing")
    val startingEvent: Event = new Event()
    startingEvent.setStartEvent()
    event = startingEvent
  }



  /**
    * Enter this System, causing System Events and background to be generated.
    * Events and background should be consistent with the type/atmosphere of this System.
    */
  def enter(): Unit = {
    val random = new Random()
    createBackground(random)
  }

  /**
    * Create a random background for this System.
    * Background depends on number and type of events generated
    * eg many planet events = background has planets
    * eg no planet events = background has no planets
    */
  private def createBackground(random: Random): Unit = {
    val num: Int = random.nextInt(4)
    var layerName: String = ""

    num match {
      case 0 => layerName = "blue_bg"
      case 1 => layerName = "purple_bg"
      case 2 => layerName = "green_bg"
      case _ => layerName = ""
    }

    SystemRepo.gameScreen.transitionSector(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur")
  }
}
