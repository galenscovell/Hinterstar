package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.things.ships.Ship

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

  }

  def saveCrew(cData: Array[Crewmate]): Unit = {

  }

  def getCrew(): Unit = {

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
