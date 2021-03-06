package galenscovell.hinterstar.generation.sector

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util._



class System(val x: Int, val y: Int, val size: Int) {
  private var event: Event = new Event()
  private var systemMarker: SystemMarker = _



  /********************
    *     Getters     *
    ********************/
  def getSystemMarker: SystemMarker = {
    systemMarker
  }

  def getEvent: Event = {
    event
  }



  /********************
    *     Setters     *
    ********************/
  def setSystemMarker(newSystemMarker: SystemMarker): Unit = {
    systemMarker = newSystemMarker
    systemMarker.becomeUnexplored()
  }

  def setAsTutorial(): Unit = {
    val startingEvent: Event = new Event()
    startingEvent.setStartEvent()
    event = startingEvent
  }



  def enter(): Unit = {
    createBackground()
  }

  private def createBackground(): Unit = {
    val num0: Int = (Math.random * 8).toInt  // Value between 0-7
    val num1: Int = (Math.random * 4).toInt + 8 // Value between 8-12
    val bgStrings: Array[String] = Array(num0.toString, num1.toString, "stars0", "stars1", "stars0_blur", "stars1_blur")
    SystemOperations.gameScreen.transitionSector(bgStrings)
  }
}
