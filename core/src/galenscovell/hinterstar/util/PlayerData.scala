package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}
import galenscovell.hinterstar.things.ships.Ship


object PlayerData {
  // PlayerData persists game data across instances of game (for continuing)
  // Ingame data usage is maintained elsewhere (ie in the player object)

  // Windows: %UserProfile%/.prefs/My Preferences
  // Linux and OSX: ~/.prefs/My Preferences
  // Android: System SharedPreferences, survives app updates, deleted when uninstalled
  var prefs: Preferences = null


  def update(): Unit = {
    prefs.flush()
  }

  def clear(): Unit = {
    prefs.clear()
  }



  /**
    * Setup
    * Initialize player data
    */
  def init(): Unit = {
    // Instantiate preferences file and add default SFX/Music settings
    prefs = Gdx.app.getPreferences("hinterstar_player_data")
    prefs.putBoolean("sfx", true)
    prefs.putBoolean("music", true)
    update()
  }

  def establishTeam(team: Array[String]): Unit = {
    // Add each teammate to preferences along with their proficiencies
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

  def establishShip(selectedShip: Ship): Unit = {
    // Add ship chassis to preferences along with all parts and their activity status
    val shipName: String = selectedShip.getName
    prefs.putString("ship-chassis", shipName)
    update()
  }

  def establishResources(): Unit = {
    // Add resource types to preferences along with their current amounts
  }



  /**
    * Updates
    * All player data is updated after every game event
    */
  def updateTeam(): Unit = {

  }

  def updateShip(): Unit = {

  }

  def updateResources(): Unit = {

  }
}
