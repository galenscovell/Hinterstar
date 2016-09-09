package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.*


class WeaponParser {
    private val source: String = "data/weapons.json"



    fun parseAll(): List<Weapon> {
        val weapons: MutableList<Weapon> = mutableListOf()
        val mainJson: JsonValue = JsonReader().parse(Gdx.files.internal(source))

        for (i in 0 until mainJson.size) {
            val currentEntry: JsonValue = mainJson.get(i)
            val weapon: Weapon = constructWeapon(currentEntry)
            weapons.add(weapon)
        }

        return weapons.toList()
    }

    fun parseSingle(name: String): Weapon {
        val mainJson: JsonValue = JsonReader().parse(Gdx.files.internal(source))
        val entry: JsonValue = mainJson.get(name)
        return constructWeapon(entry)
    }

    fun constructWeapon(entry: JsonValue): Weapon {
        val name: String = entry.name
        val subsystem: String = entry.getString("subsystem")
        val desc: String = entry.getString("description")
        val damage: Int = entry.getInt("damage")
        val firerate: Float = entry.getFloat("firerate")
        val accuracy: Int = entry.getInt("accuracy")
        val shots: Int = entry.getInt("shots")
        val effect: String = entry.getString("effect")
        val fx: String = entry.getString("fx")
        val animationType: String = entry.getString("animation-type")
        val speed: Float = entry.getFloat("speed")
        return Weapon(name, subsystem, desc, damage, firerate, accuracy, shots, effect, fx, animationType, speed)
    }
}
