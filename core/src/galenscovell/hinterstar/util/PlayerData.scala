package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}


object PlayerData {
  // PlayerData persists game data across instances of game (for continuing)
  // Ingame data usage is maintained elsewhere (ie in the player object)

  // Windows: %UserProfile%/.prefs/My Preferences
  // Linux and OSX: ~/.prefs/My Preferences
  // Android: System SharedPreferences, survives app updates, deleted when uninstalled
  private var prefs: Preferences = null


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
    // Add each teammate to preferences along with their profession
    for (i <- team.indices) {
      val entry: Array[String] = team(i).split("\t")
      val name: String = entry(0)
      val profession: String = entry(1)
      prefs.putString(s"teammate$i-name", name)
      prefs.putString(s"teammate$i-prof", profession)
    }
    update()
  }


  def establishShip(): Unit = {
    // Add ship chassis to preferences along with all parts and their activity status

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
