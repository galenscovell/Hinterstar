package galenscovell.hinterstar.processing

import scala.collection.mutable.{ArrayBuffer, Map}


/**
  * Events are the primary points of interaction for the Player.
  * Events have:
  *     a distance (Player must travel this far in the Location to reach it)
  *     an eventType (eg. Base, Enemy, Planet, Ship, Space, Team)
  *     a name (displayed as the title in the interaction dialogue)
  *     a description (displayed in the interaction dialogue)
  *     multiple choices (options for Player in interaction dialogue)
  */
class Event(d: Float) {
  var distance: Float = d
  var eventType: String = ""
  var name: String = ""
  var description: String = ""
  var choices: ArrayBuffer[Map[String, String]] = ArrayBuffer()


  /**
    * Return the distance required to reach this Event.
    */
  def getDistance: Float = {
    distance
  }

  /**
    * Return this Event's eventType.
    */
  def getType: String = {
    eventType
  }



  /**
    * Set this Event as the very first Event in the game (the tutorial Event).
    */
  def setStartEvent(): Unit = {
    name = "Starting Event"
    description = "This is the first event in the game. It describes the goal and basic controls."
  }
}
