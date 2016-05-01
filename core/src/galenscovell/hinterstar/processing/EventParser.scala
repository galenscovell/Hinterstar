package galenscovell.hinterstar.processing

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.Map


class EventParser {
  private val eventSource: String = "events.json"
  private val choiceJsonItems: List[String] = List("choice-text",
    "choice-requirement", "choice-chance", "success-text", "success-reward",
    "extra-chance", "failure-text", "failure-penalty")


  def parse(targetEventName: String): EventContainer = {
    val parsedEvent: EventContainer = new EventContainer()

    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(eventSource))
    val targetEvent: JsonValue = json.get(targetEventName)

    parsedEvent.name = targetEventName
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
