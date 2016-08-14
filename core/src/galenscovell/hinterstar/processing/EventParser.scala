package galenscovell.hinterstar.processing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable
import scala.util.Random


class EventParser {
  private val choiceJsonItems: List[String] = List(
    "choice-text",
    "choice-requirement",
    "choice-chance",
    "success-text",
    "success-reward",
    "extra-chance",
    "failure-text",
    "failure-penalty"
  )
  private val random: Random = new Random


  /**
    * Parses out an Event from JSON.
    * Randomly select event within targetEventType (6 unique events within each type)
    * TODO: Encountered events are saved in player data
    * TODO: Ensure no single event is repeated within two events of itself
    *   ie 1 -> 2 -> 3 -> 1 can be selected again -> 2 can be selected again, etc.
    */
  def parse(targetEventType: String): Event = {
    val randomChoice: Int = random.nextInt(6)
    val parsedEvent: Event = new Event()

    val eventSource: String = "data/events/" + targetEventType + "_events.json"
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(eventSource))
    val targetEvent: JsonValue = json.get(targetEventType + randomChoice.toString)

    parsedEvent.name = targetEventType + randomChoice.toString
    parsedEvent.description = targetEvent.getString("description")

    val choices: JsonValue = targetEvent.get("choices")
    for (i <- 0 until choices.size) {
      val currentChoice: JsonValue = choices.get(i)
      val choiceDetails: mutable.Map[String, String] = mutable.Map()
      for (item: String <- choiceJsonItems) {
        choiceDetails.put(item, currentChoice.getString(item))
      }
      parsedEvent.choices.append(choiceDetails)
    }

    parsedEvent
  }
}
