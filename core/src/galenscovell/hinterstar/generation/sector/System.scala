package galenscovell.hinterstar.generation.sector

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util._



class System(gridX: Int, gridY: Int, gridSize: Int) {
  val x: Int = gridX
  val y: Int = gridY
  val size: Int = gridSize

  private var event: Event = new Event()
  private var systemMarker: SystemMarker = _


  def getSystemMarker: SystemMarker = {
    systemMarker
  }

  def getEvent: Event = {
    event
  }



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
    SystemRepo.gameScreen.transitionSector(num0.toString, num1.toString, "stars0", "stars1", "stars0_blur", "stars1_blur")
  }
}
