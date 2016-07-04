package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.hinterstar.things.ships.Ship


/**
  * PlayerData persists game data across instances of game (for continuing).
  * Ingame data usage is maintained elsewhere (ie in the player object).
  *
  * Windows: %UserProfile%/.prefs/My Preferences
  * Linux and OSX: ~/.prefs/My Preferences
  * Android: System SharedPreferences, survives app updates, deleted when uninstalled
  */
object PlayerData {
  var prefs: Preferences = null


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

  /**
    * Add each teammate to preferences along with their proficiencies.
    */
  def establishTeam(team: Array[String]): Unit = {
    val proficiencies: List[String] = List("Engineer", "Pilot", "Physician", "Scientist", "Soldier")
    // 5 core proficiencies with important roles
    // Each proficiency has 5 ranks
    // Rank increases as teammate spends time assigned to each area
    // When current rank reaches 100%, next rank is attained
    // Higher rank decreases time required for Active ability, increases Passive bonus
    //   Engineer --
    //           Active: fixes broken parts
    //          Passive: ++Power part effectiveness
    //  Physician --
    //           Active: heals injured teammates
    //          Passive: ++Defense part effectiveness
    //    Soldier --
    //           Active: fends off boarding invasions
    //          Passive: ++Combat part effectiveness
    //  Scientist --
    //           Active: creates new parts
    //          Passive: ++Research part effectiveness
    //      Pilot --
    //           Active:
    //          Passive: ++Mobility part effectiveness

    for (teammate: String <- team) {
      for (proficiency: String <- proficiencies) {
        prefs.putString(s"$teammate-$proficiency", "0, 0")
      }
    }
    update()
  }

  /**
    * Add ship chassis to preferences along with all parts and their activity status.
    */
  def establishShip(selectedShip: Ship): Unit = {
    val shipName: String = selectedShip.getName
    prefs.putString("ship-chassis", shipName)
    update()
  }

  /**
    * Add resource types to preferences along with their current amounts.
    */
  def establishResources(): Unit = {
    // TODO: Not yet implemented
  }



  /**
    *
    */
  def updateTeam(): Unit = {

  }

  /**
    *
    */
  def updateShip(): Unit = {

  }

  /**
    *
    */
  def updateResources(): Unit = {

  }
}
