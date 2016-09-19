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
  private var ship: Ship = _



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
        val crewProficiencies: scala.collection.mutable.Map[String, Int] = mutable.Map()

//        for (p: String <- proficiencies) {
//          val proficiency: Int = prefs.getInteger(s"$name-$p")
//          crewProficiencies += (p -> proficiency)
//        }

        val crewmate: Crewmate = new Crewmate(name, crewProficiencies, assignment, health)
        crew.append(crewmate)
      }
    }
  }

  def saveCrew(cData: Array[Crewmate]): Unit = {
    val crewNames: ArrayBuffer[String] = ArrayBuffer()

    for (c: Crewmate <- cData) {
      val name: String = c.getName
      prefs.putString(s"$name-assignment", c.getAssignmentName)
      prefs.putInteger(s"$name-health", c.getHealth)

      for ((k, v) <- c.getAllProficiencies) {
        prefs.putInteger(s"$name-$k", v)
      }

      crewNames.append(name)
    }

    prefs.putString("crew", crewNames.mkString(","))
    prefs.flush()
  }

  def getCrew(): Array[Crewmate] = {
    crew.toArray
  }



  /********************
    * Ship Operations *
    ********************/
  def getShip(): Unit = {

  }

  def saveShip(currentShip: Ship): Unit = {

  }

  def loadShip(): Unit = {

  }

  def getShipStats(): Unit = {

  }



  /********************
    * Hull Operations *
    ********************/
  def saveHullHealth(): Unit = {

  }

  def loadHullHealth(): Unit = {

  }



  /************************
    * Location Operations *
    ************************/
}
