package galenscovell.hinterstar.util

import com.badlogic.gdx.*
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.things.parts.*
import galenscovell.hinterstar.things.ships.*


/**
 * PlayerData persists game data across instances of game (for continuing).
 * Ingame data usage is also maintained here.
 *
 * Windows: %UserProfile%/.prefs/My Preferences
 * Linux and OSX: ~/.prefs/My Preferences
 * Android: System SharedPreferences, survives app updates, deleted when uninstalled
 */
object PlayerData {
    private var prefs: Preferences = Gdx.app.getPreferences("hinterstar_player_data")
    private val crew: MutableList<Crewmate> = mutableListOf()
    private val proficiencies: List<String> = listOf("Weapons", "Engines", "Piloting", "Shields")
    lateinit private var ship: Ship



    /**
     * Instantiate preferences file and add default SFX/Music settings.
     */
    fun init(): Unit {
        prefs = Gdx.app.getPreferences("hinterstar_player_data")
        prefs.putBoolean("sfx", true)
        prefs.putBoolean("music", true)
        prefs.flush()
    }

    fun clear(): Unit {
        prefs.clear()
    }

    fun getPrefs(): Preferences {
        return prefs
    }



    /********************
     * Crew Operations *
     ********************/
    fun loadCrew(): Unit {
        crew.clear()
        val crewString: String = prefs.getString("crew")

        if (crewString.isNotEmpty()) {
            val crewNames: List<String> = crewString.split(",")

            for (name: String in crewNames) {
                val assignment: String = prefs.getString("$name-assignment")
                val health: Int = prefs.getInteger("$name-health")
                val crewProficiencies: MutableMap<String, Int> = mutableMapOf()

                for (p: String in proficiencies) {
                    val proficiency: Int = prefs.getInteger("$name-$p")
                    crewProficiencies.put(p, proficiency)
                }

                val crewmate: Crewmate = Crewmate(name, crewProficiencies, assignment, health.toFloat())
                crew.add(crewmate)
            }
        }
    }

    fun saveCrew(cData: List<Crewmate>): Unit {
        val crewNames: MutableList<String> = mutableListOf()

        for (c: Crewmate in cData) {
            val name: String = c.getName()
            prefs.putString("$name-assignment", c.getAssignedSubsystemName())
            prefs.putInteger("$name-health", c.getHealth().toInt())

            for ((k, v) in c.getAllProficiencies()) {
                prefs.putInteger("$name-$k", v)
            }

            crewNames.add(name)
        }

        prefs.putString("crew", crewNames.joinToString(","))
        prefs.flush()
    }

    fun getCrew(): List<Crewmate> {
        return crew.toList()
    }



    /********************
     * Ship Operations *
     ********************/
    fun getShip(): Ship {
        return ship
    }

    fun saveShip(currentShip: Ship): Unit {
        val shipName: String = currentShip.getName()
        prefs.putString("ship", shipName)
        prefs.flush()
    }

    fun loadShip(): Unit {
        val shipName: String = prefs.getString("ship")
        ship = ShipParser().parseSingle(shipName)
        ship.setPlayerShip()
    }

    fun saveWeapons(): Unit {
        val currentWeapons: List<Weapon> = ship.getWeapons()
        val currentWeaponNames: Array<String?> = arrayOfNulls(currentWeapons.size)
        val activeWeapons: MutableList<String> = mutableListOf()

        // Save all weapons player currently has stored away
        for (i in currentWeapons.indices) {
            currentWeaponNames.set(i, currentWeapons[i].getName())
        }

        // Save all active weapons and assigned crewmate, eg. "Machinegun|Tony"
        for (crewmate: Crewmate in crew) {
            val assignedWeapon: Weapon? = crewmate.getWeapon()
            if (assignedWeapon != null) {
                activeWeapons.add(assignedWeapon.getName() + "|" + crewmate.getName())
            }
        }

        prefs.putString("held-weapons", currentWeaponNames.joinToString(","))
        prefs.putString("active-weapons", activeWeapons.joinToString(","))
        prefs.flush()
    }

    fun loadWeapons(): Unit {
        // Load all player stored weapons
        val heldWeaponString: String = prefs.getString("held-weapons")

        if (heldWeaponString.isNotEmpty()) {
            val weaponNames: List<String> = heldWeaponString.split(",")
            val weaponArray: MutableList<Weapon> = mutableListOf()
            val weaponParser: WeaponParser = WeaponParser()

            for (name: String in weaponNames) {
                val weapon: Weapon = weaponParser.parseSingle(name)
                weaponArray.add(weapon)
            }

            ship.setWeapons(weaponArray.toList())
        }

        // Activate saved active weapons and assign saved crewmate
        val activeWeaponString: String = prefs.getString("active-weapons")

        if (activeWeaponString.isNotEmpty()) {
            val activeWeaponEntries: List<String> = activeWeaponString.split(",")

            for (weapon: Weapon in ship.getWeapons()) {
                for (entry: String in activeWeaponEntries) {
                    val components: List<String> = entry.split("|")
                    val weaponName: String = components[0]
                    val crewmateName: String = components[1]

                    if (weapon.getName() == weaponName) {
                        ship!!.equipWeapon(weapon)

                        for (crewmate: Crewmate in crew) {
                            if (crewmate.getName() == crewmateName) {
                                crewmate.setWeapon(weapon)
                            }
                        }
                    }
                }
            }
        }
    }

    // Should this be called every game update to ensure stats are always fresh?
    // ie crewmate moved to turret, check will see it immediately (once updated) and set
    //    the turret into firing mode
    fun getShipStats(): FloatArray {
        // Stats Array = (Shield, Evasion)
        val stats: FloatArray = floatArrayOf(0f, 0f)
        var helmManned: Boolean = false

        for (crewmate: Crewmate in crew) {
            val assignment: String = crewmate.getAssignedSubsystemName()

            when (assignment) {
                "Weapon Control" -> Unit
                "Engine Room" -> {
                    val bonus: Float = 2.5f + (2.5f * (crewmate.getAProficiency("Engines") / 100))
                    stats[1] += bonus
                }
                "Helm" -> {
                    val bonus: Float = 5f + (crewmate.getAProficiency("Piloting") / 100)
                    stats[1] += bonus
                    helmManned = true
                }
                "Shield Control" -> {
                    val bonus: Float = 1f + (0.5f * (crewmate.getAProficiency("Shields") / 100))
                    stats[0] += bonus
                }
                else -> Unit
            }
        }

        if (!helmManned) {
            stats.set(1, 0f)
        }

        return stats
    }



    /********************
     * Hull Operations *
     ********************/
    fun saveHullHealth(): Unit {
        prefs.putInteger("hull-health", ship!!.getHealth())
        prefs.flush()
    }

    fun loadHullHealth(): Unit {
        ship.setHealth(prefs.getInteger("hull-health").toFloat())
    }



    /************************
     * Location Operations *
     ************************/
    // TODO: Save current Sector (and map layout) to custom file format
    // TODO: Save current System and explored Systems
    // TODO: Load saved Sector (and map layout)
    // TODO: Load saved System
}
