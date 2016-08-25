package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.{JsonReader, JsonValue}

import scala.collection.mutable.ArrayBuffer


class WeaponParser {
  private val source: String = "data/weapons.json"


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

  def parseSingle(name: String): Weapon = {
    val mainJson: JsonValue = new JsonReader().parse(Gdx.files.internal(source))
    val entry: JsonValue = mainJson.get(name)
    constructWeapon(entry)
  }

  def constructWeapon(entry: JsonValue): Weapon = {
    val name: String = entry.name
    val subsystem: String = entry.getString("subsystem")
    val desc: String = entry.getString("description")
    val damage: Int = entry.getInt("damage")
    val firerate: Int = entry.getInt("firerate")
    val effect: String = entry.getString("effect")
    val fx: String = entry.getString("fx")
    val speed: Float = entry.getFloat("speed")
    new Weapon(name, subsystem, desc, damage, firerate, effect, fx, speed)
  }
}
