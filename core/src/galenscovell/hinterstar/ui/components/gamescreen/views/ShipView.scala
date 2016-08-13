package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


// TODO: Display ship name, description, subsystems with explanation of their usage
// TODO: Display every weapon in inventory with their details
class ShipView(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest2)
    this.add(mainTable)
      .width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - 32)
      .padTop(32)
  }
}
