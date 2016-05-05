package galenscovell.hinterstar.processing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.Map


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


  def parse(targetEventType: String): EventContainer = {
    // Randomly select event within targetEventType (6 unique events within each type)
    // Encountered events are saved in player pref data
    // Ensure no single event is repeated within two events of itself
    // ie 1 -> 2 -> 3 -> 1 can be selected again -> 2 can be selected again, etc.
    val parsedEvent: EventContainer = new EventContainer()

    val eventSource: String = "data/" + targetEventType + ".json"
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(eventSource))
    val targetEvent: JsonValue = json.get(targetEventType)

    parsedEvent.name = targetEventType
    parsedEvent.description = targetEvent.getString("description")

    val choices: JsonValue = targetEvent.get("choices")
    for (i <- 0 until choices.size) {
      val currentChoice: JsonValue = choices.get(i)
      val choiceDetails: Map[String, String] = Map()
      for (item: String <- choiceJsonItems) {
        choiceDetails.put(item, currentChoice.getString(item))
      }
      parsedEvent.choices.append(choiceDetails)
    }

    parsedEvent
  }
}
