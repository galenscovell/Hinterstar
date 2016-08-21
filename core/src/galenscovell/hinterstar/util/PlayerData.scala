package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.things.parts.{Weapon, WeaponParser}
import galenscovell.hinterstar.things.ships.{Ship, ShipParser}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/**
  * PlayerData persists game data across instances of game (for continuing).
  * Ingame data usage is also maintained here.
  *
  * Windows: %UserProfile%/.prefs/My Preferences
  * Linux and OSX: ~/.prefs/My Preferences
  * Android: System SharedPreferences, survives app updates, deleted when uninstalled
  */
object PlayerData {
  private var prefs: Preferences = _
  private val crew: ArrayBuffer[Crewmate] = ArrayBuffer()
  private var ship: Ship = _
  private var hullHealth: Int = 100
  private val proficiencies: List[String] =
    List("Weapons", "Engines", "Piloting", "Shields")


  /**
    * Instantiate preferences file and add default SFX/Music settings.
    */
  def init(): Unit = {
    prefs = Gdx.app.getPreferences("hinterstar_player_data")
    prefs.putBoolean("sfx", true)
    prefs.putBoolean("music", true)
    prefs.flush()
  }

  def clear(): Unit = {
    prefs.clear()
  }

  def getPrefs: Preferences = {
    prefs
  }



  /********************
    * Crew Operations *
    ********************/
  def loadCrew(): Unit = {
    crew.clear()
    val crewString: String = prefs.getString("crew")

    if (crewString.nonEmpty) {
      val crewNames: Array[String] = crewString.split(",")

      for (name: String <- crewNames) {
        val assignment: String = prefs.getString(s"$name-assignment")
        val health: Int = prefs.getInteger(s"$name-health")
        val crewProficiencies: mutable.Map[String, Int] = mutable.Map()

        for (p: String <- proficiencies) {
          val proficiency: Int = prefs.getInteger(s"$name-$p")
          crewProficiencies += (p -> proficiency)
        }

        val crewmate: Crewmate = new Crewmate(name, crewProficiencies, assignment, health)
        crew.append(crewmate)
      }
    }
  }

  def saveCrew(cData: Array[Crewmate]): Unit = {
    val crewNames: ArrayBuffer[String] = ArrayBuffer()

    for (c: Crewmate <- cData) {
      val name: String = c.getName
      prefs.putString(s"$name-assignment", c.getAssignedSubsystemName)
      prefs.putInteger(s"$name-health", c.getHealth)

      for ((k, v) <- c.getAllProficiencies) {
        prefs.putInteger(s"$name-$k", v)
      }

      crewNames.append(name)
    }

    prefs.putString("crew", crewNames.mkString(","))
    prefs.flush()
  }

  def getCrew: Array[Crewmate] = {
    crew.toArray
  }



  /********************
    * Ship Operations *
    ********************/
  def getShip: Ship = {
    ship
  }

  def saveShip(currentShip: Ship): Unit = {
    val shipName: String = currentShip.getName
    prefs.putString("ship", shipName)
    prefs.flush()
  }

  def loadShip(): Unit = {
    val shipName: String = prefs.getString("ship")
    ship = new ShipParser().parseSingle(shipName)
    ship.setPlayerShip()
  }

  def saveWeapons(): Unit = {
    val currentWeapons: Array[Weapon] = ship.getWeapons
    val currentWeaponNames: Array[String] = Array.ofDim(currentWeapons.length)
    val activeWeapons: ArrayBuffer[String] = ArrayBuffer()

    // Save all weapons player currently has stored away
    for (i <- currentWeapons.indices) {
      currentWeaponNames(i) = currentWeapons(i).getName
    }

    // Save all active weapons and assigned crewmate, eg. "Machinegun|Tony"
    for (crewmate: Crewmate <- crew) {
      val assignedWeapon: Weapon = crewmate.getWeapon
      if (assignedWeapon != null) {
        activeWeapons.append(s"$assignedWeapon.getName|$crewmate.getName")
      }
    }

    prefs.putString("held-weapons", currentWeaponNames.mkString(","))
    prefs.putString("active-weapons", activeWeapons.mkString(","))
    prefs.flush()
  }

  def loadWeapons(): Unit = {
    // Load all player stored weapons
    val heldWeaponString: String = prefs.getString("held-weapons")

    if (heldWeaponString.nonEmpty) {
      val weaponNames: Array[String] = heldWeaponString.split(",")
      val weaponArray: ArrayBuffer[Weapon] = ArrayBuffer()
      val weaponParser: WeaponParser = new WeaponParser

      for (name: String <- weaponNames) {
        val weapon: Weapon = weaponParser.parseSingle(name)
        weaponArray.append(weapon)
      }

      ship.setWeapons(weaponArray.toArray)
    }

    // Activate saved active weapons and assign saved crewmate
    val activeWeaponString: String = prefs.getString("active-weapons")

    if (activeWeaponString.nonEmpty) {
      val activeWeaponEntries: Array[String] = activeWeaponString.split(",")

      for (weapon: Weapon <- ship.getWeapons) {
        for (entry: String <- activeWeaponEntries) {
          val components: Array[String] = entry.split("|")
          val weaponName: String = components(0)
          val crewmateName: String = components(1)

          if (weapon.getName == weaponName) {
            ship.equipWeapon(weapon)

            for (crewmate: Crewmate <- crew) {
              if (crewmate.getName == crewmateName) {
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
  def getShipStats: Array[Float] = {
    // Stats Array = (Shield, Evasion)
    val stats: Array[Float] = Array(0, 0)
    var helmManned: Boolean = false

    for (crewmate: Crewmate <- crew) {
      val assignment: String = crewmate.getAssignedSubsystemName

      assignment match {
        case "Weapon Control" => Unit
        case "Engine Room" =>
          val bonus: Float = 2.5f + (2.5f * (crewmate.getAProficiency("Engines") / 100))
          stats(1) += bonus
        case "Helm" =>
          val bonus: Float = 5 + (crewmate.getAProficiency("Piloting") / 100)
          stats(1) += bonus
          helmManned = true
        case "Shield Control" =>
          val bonus: Float = 1 + (0.5f * (crewmate.getAProficiency("Shields") / 100))
          stats(0) += bonus
        case _ => Unit
      }
    }

    if (!helmManned) {
      stats(1) = 0
    }

    stats
  }



  /********************
    * Hull Operations *
    ********************/
  def saveHullHealth(): Unit = {
    prefs.putInteger("hull-health", hullHealth)
    prefs.flush()
  }

  def loadHullHealth(): Int = {
    prefs.getInteger("hull-health")
  }

  def getHullHealth(): Int = {
    hullHealth
  }

  def updateHullHealth(health: Int): Unit = {
    hullHealth = health
  }



  /************************
    * Location Operations *
    ************************/
  // TODO: Save current Sector (and map layout) to custom file format
  // TODO: Save current System and explored Systems
  // TODO: Load saved Sector (and map layout)
  // TODO: Load saved System
}
