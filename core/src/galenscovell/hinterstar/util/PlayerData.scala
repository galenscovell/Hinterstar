package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}


object PlayerData {
  // Windows: %UserProfile%/.prefs/My Preferences
  // Linux and OSX: ~/.prefs/My Preferences
  // Android: System SharedPreferences, survives app updates, deleted when uninstalled
  private var prefs: Preferences = null


  def update(): Unit = {
    prefs.flush()
  }


  /**
    * Setup
    */
  def init(): Unit = {
    prefs = Gdx.app.getPreferences("hinterstar_player_data")
    prefs.putBoolean("sfx", true)
    prefs.putBoolean("music", true)
    update()
  }

  def establishTeam(team: Array[String]): Unit = {
    for (i <- team.indices) {
      val entry: Array[String] = team(i).split("\t")
      val name: String = entry(0)
      val profession: String = entry(1)
      prefs.putString(f"teammate$i-name", name)
      prefs.putString(f"teammate$i-prof", profession)
    }
    update()
  }

  def establishShip(): Unit = {

  }

  def establishComponents(): Unit = {

  }

  def establishResources(): Unit = {

  }


  /**
    * Updates
    */
  def updateTeam(teammate1: String, teammate2: String): Unit = {

  }

  def updateShip(): Unit = {

  }

  def updateComponent(): Unit = {

  }

  def updateResource(resource: String, amount: Int): Unit = {

  }
}
