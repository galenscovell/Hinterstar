package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util._


class HullHealthPanel(interfaceStage: InterfaceStage) extends Table {
  private val hullHealthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.hullHealthBarStyle)

  construct()
  updateHealth()



  private def construct(): Unit = {
    this.add(hullHealthBar).expand.fill
  }

  def updateHealth(): Unit = {
    val currentHealth: Int = PlayerData.getHullHealth()
    hullHealthBar.setValue(currentHealth)
  }
}
