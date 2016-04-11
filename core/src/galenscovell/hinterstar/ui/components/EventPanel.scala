package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util.ResourceManager


class EventPanel(gameStage: GameStage) extends Table {
  private final val root: GameStage = gameStage

  construct()


  def construct(): Unit = {
    this.setFillParent(true)

    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.np_test4)

    this.add(mainTable).width(660).height(380).expand.fill.center
  }
}
