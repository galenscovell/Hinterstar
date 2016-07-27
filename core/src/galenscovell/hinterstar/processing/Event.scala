package galenscovell.hinterstar.processing

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/**
  * Events are the primary points of interaction for the Player.
  * Events have:
  *     an eventType (eg. Base, Enemy, Planet, Ship, Space, Team)
  *     a name (displayed as the title in the interaction dialogue)
  *     a description (displayed in the interaction dialogue)
  *     multiple choices (options for Player in interaction dialogue)
  */
class Event() {
  val choices: ArrayBuffer[mutable.Map[String, String]] = ArrayBuffer()

  var eventType: String = ""
  var name: String = ""
  var description: String = ""



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
