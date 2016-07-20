package galenscovell.hinterstar.ui.components.gamescreen.nav

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.util._


class TeamNav(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage

  construct()
  

  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest3)
    this.add(mainTable).width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - (Constants.SYSTEMMARKER_SIZE * 4))
      .padTop(Constants.SYSTEMMARKER_SIZE * 2)
      .padBottom(Constants.SYSTEMMARKER_SIZE * 2)
  }
}
