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
  private var subsystems: mutable.Map[String, Int] = _
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



  // CREW OPERATIONS
  def loadCrew(): Unit = {
    crew.clear()
    val crewNames: Array[String] = prefs.getString("crew").split(",")

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

  def saveCrew(cData: Array[Crewmate]): Unit = {
    val crewNames: ArrayBuffer[String] = ArrayBuffer()

    for (c: Crewmate <- cData) {
      val name: String = c.getName
      prefs.putString(s"$name-assignment", c.getAssignment)
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



  // SHIP OPERATIONS
  def getShip: Ship = {
    ship
  }

  def getOccupiedSubsystems: mutable.Map[String, Int] = {
    subsystems
  }

  def updateOccupiedSubsystems(): Unit = {
    subsystems = mutable.Map()
    for (subsystem: String <- ship.getSubsystems) {
      subsystems(subsystem) = 0
    }

    for (crewmate: Crewmate <- crew) {
      val assignment: String = crewmate.getAssignment
      if (subsystems.contains(assignment)) {
        subsystems(assignment) += 1
      }
    }
  }

  def saveShip(currentShip: Ship): Unit = {
    val shipName: String = currentShip.getName
    prefs.putString("ship", shipName)
    prefs.flush()
  }

  def loadShip(): Unit = {
    val shipName: String = prefs.getString("ship")
    ship = new ShipParser().parseSingle(shipName)
  }

  def saveWeapons(): Unit = {
    val currentWeapons: Array[Weapon] = ship.getWeapons
    val currentWeaponNames: Array[String] = Array.ofDim(currentWeapons.length)

    for (i <- currentWeapons.indices) {
      currentWeaponNames(i) = currentWeapons(i).getName
    }

    prefs.putString("weapons", currentWeaponNames.mkString(","))
    prefs.flush()
  }

  def loadWeapons(): Unit = {
    val weaponsString: String = prefs.getString("weapons")
    val weaponNames: Array[String] = weaponsString.split(",")
    val weaponArray: ArrayBuffer[Weapon] = ArrayBuffer()
    val weaponParser: WeaponParser = new WeaponParser

    for (name: String <- weaponNames) {
      val weapon: Weapon = weaponParser.parseSingle(name)
      weaponArray.append(weapon)
    }

    ship.setWeapons(weaponArray.toArray)
  }

  def getShipStats: Array[Float] = {
    // Stats Array = (Shield, Evasion, Weapons)
    val stats: Array[Float] = Array(0, 0, 0)
    var helmManned: Boolean = false

    for (crewmate: Crewmate <- crew) {
      val assignment: String = crewmate.getAssignment

      assignment match {
        case "Weapon Control" =>
          stats(2) += 1
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



  // HULL HEALTH OPERATIONS
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



  // LOCATION OPERATIONS
  // TODO: Save current Sector (and map layout)
  // TODO: Save current System
  // TODO: Load saved Sector (and map layout)
  // TODO: Load saved System
}
