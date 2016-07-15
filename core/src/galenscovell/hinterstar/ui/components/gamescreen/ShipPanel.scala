package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util._


class ShipPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.npTest2)
    this.add(mainTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.SECTORSIZE * 2)).padTop(Constants.SECTORSIZE * 2)
  }
}
