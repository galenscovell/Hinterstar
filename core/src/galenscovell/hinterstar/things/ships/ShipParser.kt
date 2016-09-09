package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.parts.WeaponParser



class ShipParser {
    private val weaponParser: WeaponParser = WeaponParser()
    private val source: String = "data/ships.json"



    fun parseAll(): List<Ship> {
        val ships: MutableList<Ship> = mutableListOf()
        val json: JsonValue = JsonReader().parse(Gdx.files.internal(source))

        for (i in 0 until json.size) {
            val currentEntry: JsonValue = json.get(i)
            val ship: Ship = constructShip(currentEntry)
            ships.add(ship)
        }

        return ships.toList()
    }

    fun parseSingle(name: String): Ship {
        val json: JsonValue = JsonReader().parse(Gdx.files.internal(source))
        val entry: JsonValue = json.get(name)
        return constructShip(entry)
    }

    fun constructShip(entry: JsonValue): Ship {
        val name: String = entry.name
        val desc: String = entry.getString("description")
        val weapons: MutableList<Weapon> = mutableListOf()
        val subsystems: MutableList<String> = mutableListOf()

        val weaponsEntry: JsonValue = entry.get("weapons")
        for (i in 0 until weaponsEntry.size) {
            val weapon: String = weaponsEntry.getString(i)
            val parsedWeapon: Weapon = weaponParser.parseSingle(weapon)
            weapons.add(parsedWeapon)
        }

        val subsystemsEntry: JsonValue = entry.get("subsystems")
        for (i in 0 until subsystemsEntry.size) {
            val subsystemString: String = subsystemsEntry.getString(i)
            subsystems.add(subsystemString)
        }

        return Ship(name, desc, weapons.toList(), subsystems.toList())
    }
}
