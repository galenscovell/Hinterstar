package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import galenscovell.hinterstar.util.Resources


class PausePanel extends Table {
  construct()



  private def construct(): Unit = {
    this.setFillParent(true)

    val pauseTable: Table = new Table
    pauseTable.setBackground(Resources.npTest3)
    val pauseLabel: Label = new Label("Paused", Resources.labelLargeStyle)

    pauseTable.add(pauseLabel)

    this.add(pauseTable).expand.width(160).height(32).center.bottom.padBottom(92)
  }
}
