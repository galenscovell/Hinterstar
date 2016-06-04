package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.{ArrayBuffer, Map}


class ShipParser {
  private val source: String = "data/ships.json"
  private val stats: List[String] = List(
    "combat", "hull", "mobility", "power", "storage", "shield"
  )


  def parseAll: Array[Ship] = {
    val ships: ArrayBuffer[Ship] = ArrayBuffer()
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(source))

    for (i <- 0 until json.size) {
      val currentEntry: JsonValue = json.get(i)
      val ship: Ship = constructShip(currentEntry)
      ships.append(ship)
    }

    ships.toArray
  }

  def parseSingle(name: String): Ship = {
    val json: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val entry: JsonValue = json.get(name)
    constructShip(entry)
  }

  def constructShip(entry: JsonValue): Ship = {
    val name: String = entry.getString("name")
    val desc: String = entry.getString("description")
    val installPoints: Map[String, Int] = Map()

    val installEntry: JsonValue = entry.get("install-points")
    for (stat: String <- stats) {
      installPoints.put(stat, installEntry.getInt(stat))
    }

    new Ship(name, desc, installPoints)
  }
}
