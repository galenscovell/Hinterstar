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
    val num: Int = (Math.random * 4).toInt
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
