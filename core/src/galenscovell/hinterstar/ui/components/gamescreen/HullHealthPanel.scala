package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.util.{PlayerData, Resources}


class HullHealthPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
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
