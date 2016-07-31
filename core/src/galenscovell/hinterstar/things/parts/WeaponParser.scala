package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.ArrayBuffer


/**
  * WeaponParser handles parsing of Weapons from JSON.
  */
class WeaponParser {
  private val source: String = "data/weapons.json"


  /**
    * Parse out all Weapons from weapons.json to an Array.
    */
  def parseAll(): Array[Weapon] = {
    val weapons: ArrayBuffer[Weapon] = ArrayBuffer()
    val mainJson: JsonValue = new JsonReader().parse(Gdx.files.internal(source))

    for (i <- 0 until mainJson.size) {
      val currentEntry: JsonValue = mainJson.get(i)
      val weapon: Weapon = constructWeapon(currentEntry)
      weapons.append(weapon)
    }

    weapons.toArray
  }

  /**
    * Parse out single Weapon from weapons.json.
    */
  def parseSingle(name: String): Weapon = {
    val mainJson: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val entry: JsonValue = mainJson.get(name)
    constructWeapon(entry)
  }

  /**
    * Create new Weapon with name, desc, and stats.
    */
  def constructWeapon(entry: JsonValue): Weapon = {
    val name: String = entry.name
    val desc: String = entry.getString("description")
    val damage: Int = entry.getInt("damage")
    val firerate: Int = entry.getInt("firerate")
    val effect: String = entry.getString("effect")
    new Weapon(name, desc, damage, firerate, effect)
  }
}
