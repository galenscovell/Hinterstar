package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class ShipView(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest2)
    this.add(mainTable)
      .width(Constants.EXACT_X - (Constants.SYSTEMMARKER_SIZE * 3))
      .height(Constants.EXACT_Y - (Constants.SYSTEMMARKER_SIZE * 3))
      .padTop(Constants.SYSTEMMARKER_SIZE * 2)
      .padBottom(Constants.SYSTEMMARKER_SIZE)
  }
}
