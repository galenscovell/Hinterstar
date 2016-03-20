package galenscovell.oregontrail.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.oregontrail.util._


class TeamPanel(stage: GameStage) extends Table {
  private final val gameStage: GameStage = stage

  construct
  

  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.np_test3)
    this.add(mainTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.SECTORSIZE * 2)).padTop(Constants.SECTORSIZE * 2)
  }
}