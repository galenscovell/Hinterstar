package galenscovell.hinterstar.processing

import scala.collection.mutable.{ArrayBuffer, Map}


class EventContainer(dist: Float) {
  var distance: Float = dist
  var eventType: String = ""
  var name: String = ""
  var description: String = ""
  var choices: ArrayBuffer[Map[String, String]] = ArrayBuffer()


  def setStartEvent(): Unit = {
    name = "Starting Event"
    description = "This is the first event in the game. It describes the goal and basic controls."
  }

  def getDistance: Float = {
    distance
  }

  def getType: String = {
    eventType
  }
}
