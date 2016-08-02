
package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.util._


class CrewView(stage: ActionStage) extends Table {
  private val gameStage: ActionStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest3)
    this.add(mainTable)
      .width(Constants.EXACT_X - (Constants.SYSTEMMARKER_SIZE * 3))
      .height(Constants.EXACT_Y - (Constants.SYSTEMMARKER_SIZE * 3))
      .padTop(Constants.SYSTEMMARKER_SIZE * 2)
      .padBottom(Constants.SYSTEMMARKER_SIZE)
  }
}
