package galenscovell.hinterstar.util

import com.badlogic.gdx.{Gdx, Preferences}


object PlayerData {
  // Windows: %UserProfile%/.prefs/My Preferences
  // Linux and OSX: ~/.prefs/My Preferences
  // Android: System SharedPreferences, survives app updates, deleted when uninstalled
  private var prefs: Preferences = null


  def init(): Unit = {
    prefs = Gdx.app.getPreferences("hinterstar_player_data")
    prefs.putBoolean("sfx", true)
    prefs.putBoolean("music", true)
    update()
  }

  def update(): Unit = {
    prefs.flush()
  }

  def establishTeam(teamMates: Array[String]): Unit = {
    for (i <- teamMates.indices) {
      val entry: Array[String] = teamMates(i).split("\t")
      val name: String = entry(0)
      val profession: String = entry(1)
      val teamMate: String = f"team$i"
      prefs.putString(f"$teamMate-name", name)
      prefs.putString(f"$teamMate-prof", profession)
    }
    update()
  }
}
