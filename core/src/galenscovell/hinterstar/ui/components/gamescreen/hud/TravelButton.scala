package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class TravelButton(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private var button: TextButton = _

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)

    val contentTable: Table = new Table
    val label: Label = new Label("Travel to next system", Resources.labelTinyStyle)
    label.setAlignment(Align.center)
    this.button = new TextButton("Travel", Resources.buttonMapStyle0)
    button.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.openTravelPanel()
      }
    })

    contentTable.add(button).width(96).height(48).center
    contentTable.row
    contentTable.add(label).width(96).height(24)

    this.add(contentTable).expand.width(96).height(72).center.top.padTop(4)
  }
}
