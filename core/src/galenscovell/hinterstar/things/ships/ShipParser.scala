package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}
import galenscovell.hinterstar.things.parts.{Part, PartParser}

import scala.collection.mutable.{ArrayBuffer, Map}


/**
  * ShipParser handles the parsing of Ships from JSON.
  */
class ShipParser {
  private val partParser: PartParser = new PartParser
  private val source: String = "data/ships.json"
  private val partTypes: List[String] = List(
    "Combat", "Defense", "Mobility", "Power", "Research", "Storage"
  )


  /**
    * Parse out all Ships from ships.json as an ArrayBuffer.
    */
  def parseAll: ArrayBuffer[Ship] = {
    val ships: ArrayBuffer[Ship] = ArrayBuffer()
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(source))

    for (i <- 0 until json.size) {
      val currentEntry: JsonValue = json.get(i)
      val ship: Ship = constructShip(currentEntry)
      ships.append(ship)
    }

    ships
  }

  /**
    * Parse out a single Ship from ships.json.
    */
  def parseSingle(name: String): Ship = {
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val entry: JsonValue = json.get(name)
    constructShip(entry)
  }

  /**
    * Create new Ship with name, desc and starting parts in ships.json.
    */
  def constructShip(entry: JsonValue): Ship = {
    val name: String = entry.name
    val desc: String = entry.getString("description")
    val startingParts: Map[String, ArrayBuffer[Part]] = Map()

    // For each partType, assemble ArrayBuffer of Parts to be stored in a map
    val startingPartsEntry: JsonValue = entry.get("starting-parts")
    for (pt: String <- partTypes) {
      val foundParts: ArrayBuffer[Part] = ArrayBuffer()
      val partArray: JsonValue = startingPartsEntry.get(pt)

      // Use PartParser to create each Part using Name/Rank in ships.json
      for (i <- 0 until partArray.size) {
        val currentPart: JsonValue = partArray.get(i)
        val partName: String = currentPart.getString("Name")
        val partRank: String = currentPart.getString("Rank")
        val parsedPart: Part = partParser.parseSingle(pt, partName, partRank)
        foundParts.append(parsedPart)
      }

      startingParts.put(pt, foundParts)
    }

    new Ship(name, desc, startingParts)
  }
}
