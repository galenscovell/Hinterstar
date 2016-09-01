package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import galenscovell.hinterstar.util.Resources


class InfoPanel(message: String) extends Table {
  this.setFillParent(true)

  val pauseTable: Table = new Table
  pauseTable.setBackground(Resources.blueButtonNp0)
  val pauseLabel: Label = new Label(message, Resources.labelLargeStyle)

  pauseTable.add(pauseLabel).pad(6)

  this.add(pauseTable).expand.height(32).center.top.padTop(80)
}
