package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.things.ships.Ship

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
  private var hullHealth: Int = 100

  private val proficiencies: List[String] =
    List("Artillery", "Engines", "Piloting", "Melee", "Repair", "Shields")
  private val subsystems: List[String] =
    List("Artillery", "Clinic", "Engines", "Helm", "Shields")


  /**
    * Flush in the new data to Player prefs.
    */
  def update(): Unit = {
    prefs.flush()
  }

  /**
    * Completely clear the current data saved in Player prefs.
    */
  def clear(): Unit = {
    prefs.clear()
    crew.clear()
  }

  /**
    * Instantiate preferences file and add default SFX/Music settings.
    */
  def init(): Unit = {
    prefs = Gdx.app.getPreferences("hinterstar_player_data")
    prefs.putBoolean("sfx", true)
    prefs.putBoolean("music", true)
    update()
  }

  def getPrefs: Preferences = {
    prefs
  }



  // CREW OPERATIONS
  /*
   * Load crew data from prefs, iterate over values for each name and
   * populate crew data with constructed Crew objects
   */
  def loadCrew(): Unit = {
    crew.clear()

    val crewNamesStr: String = prefs.getString("crew")
    val crewNameList: List[String] = crewNamesStr.split(',').toList

    for (name: String <- crewNameList) {
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

  /*
   * Update crew data in prefs pulling current crewData info.
   * Saves name, assignment, health and proficiency ranks from each Crew in crewData.
   */
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
    update()
  }

  /*
   * Get current crew data.
   */
  def getCrew: Array[Crewmate] = {
    crew.toArray
  }



  // SHIP OPERATIONS
  /**
    * Add ship chassis to preferences along with all parts and their activity status.
    */
  def saveShip(selectedShip: Ship): Unit = {
    val shipName: String = selectedShip.getName
    prefs.putString("ship-chassis", shipName)
    update()
  }

  def getShipStats: mutable.Map[String, Int] = {
    val stats: mutable.Map[String, Int] = mutable.Map(
      "Evasion" -> 0,
      "Shield" -> 0,
      "Weapons" -> 0
    )
    var helmManned: Boolean = false

    for (crewmate: Crewmate <- crew) {
      val assignment: String = crewmate.getAssignment

      assignment match {
        case "Artillery" =>
          val bonus: Float = 1 + (0.5f * (crewmate.getAProficiency(assignment) / 100))
          stats("Weapons") = stats("Weapons") + bonus.toInt
        case "Clinic" =>
          println("Crewmate in clinic")
        case "Engines" =>
          val bonus: Float = 1 + (0.5f * (crewmate.getAProficiency(assignment) / 100))
          stats("Evasion") = stats("Evasion") + bonus.toInt
        case "Helm" =>
          helmManned = true
        case "Shields" =>
          val bonus: Float = 1 + (0.5f * (crewmate.getAProficiency(assignment) / 100))
          stats("Shield") = stats("Shield") + bonus.toInt
        case _ =>
          println("Nothin' here!")
      }
    }

    if (!helmManned) {
      stats("Evasion") = 0
    }

    stats
  }



  // HULL HEALTH OPERATIONS
  def saveHullHealth(health: Int): Unit = {
    prefs.putInteger("hull-health", health)
    update()
  }

  def getHullHealth(): Int = {
    prefs.getInteger("hull-health")
  }
}
