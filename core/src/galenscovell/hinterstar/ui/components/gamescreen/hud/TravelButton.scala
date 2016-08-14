package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class TravelButton(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private var button: TextButton = _

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)

    val contentTable: Table = new Table
    this.button = new TextButton("Travel", Resources.greenButtonStyle)
    button.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.openTravelPanel()
      }
    })

    contentTable.add(button).width(96).height(40).center

    this.add(contentTable).expand.width(96).height(40).top.right.padTop(4).padRight(10)
  }
}
