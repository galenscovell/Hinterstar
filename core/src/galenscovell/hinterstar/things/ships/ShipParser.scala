package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}
import galenscovell.hinterstar.things.parts.{Weapon, WeaponParser}

import scala.collection.mutable.ArrayBuffer


/**
  * ShipParser handles the parsing of Ships from JSON.
  */
class ShipParser {
  private val weaponParser: WeaponParser = new WeaponParser
  private val source: String = "data/ships.json"


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
    * Create new Ship with name, desc, weapons and subsystems from ships.json.
    */
  def constructShip(entry: JsonValue): Ship = {
    val name: String = entry.name
    val desc: String = entry.getString("description")
    val weapons: ArrayBuffer[Weapon] = ArrayBuffer()
    val subsystems: ArrayBuffer[String] = ArrayBuffer()

    val weaponsEntry: JsonValue = entry.get("weapons")
    for (i <- 0 until weaponsEntry.size) {
      val weapon: String = weaponsEntry.getString(i)
      val parsedWeapon: Weapon = weaponParser.parseSingle(weapon)
      weapons.append(parsedWeapon)
    }

    val subsystemsEntry: JsonValue = entry.get("subsystems")
    for (i <- 0 until subsystemsEntry.size) {
      val subsystemString: String = subsystemsEntry.getString(i)
      subsystems.append(subsystemString)
    }

    new Ship(name, desc, weapons.toArray, subsystems.toArray)
  }
}
