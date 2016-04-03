package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util._


class ShipPanel(stage: GameStage) extends Table {
  private final val gameStage: GameStage = stage
  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.np_test2)
    this.add(mainTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.SECTORSIZE * 2)).padTop(Constants.SECTORSIZE * 2)
  }
}
