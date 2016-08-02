package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util.{PlayerData, Resources}


class HullHealthPanel(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private val hullHealthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.healthBarStyle)

  construct()
  updateHealth()


  private def construct(): Unit = {
    val mainTable: Table = new Table

    mainTable.add(hullHealthBar).expand.fill
    this.add(mainTable).expand.fill
  }

  def updateHealth(): Unit = {
    val currentHealth: Int = PlayerData.getHullHealth()
    hullHealthBar.setValue(currentHealth)
  }
}
