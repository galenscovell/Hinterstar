
package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class CrewView(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest3)
    this.add(mainTable)
      .width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - 32)
      .padTop(32)
  }
}
