package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class ViewButtons(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private var sectorButton, crewButton, shipButton: TextButton = _

  construct()


  private def construct(): Unit = {
    this.align(Align.center)

    this.sectorButton = new TextButton("Sector", Resources.buttonMapStyle0)
    this.crewButton = new TextButton("Crew", Resources.buttonMapStyle1)
    this.shipButton = new TextButton("Ship", Resources.buttonMapStyle2)
    sectorButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.toggleView(0)
        crewButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    crewButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.toggleView(1)
        sectorButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    shipButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.toggleView(2)
        sectorButton.setChecked(false)
        crewButton.setChecked(false)
      }
    })
    this.add(sectorButton).width(134).expand.fill
    this.add(crewButton).width(134).expand.fill
    this.add(shipButton).width(134).expand.fill
  }

  def getSectorButton: TextButton = {
    sectorButton
  }
}
