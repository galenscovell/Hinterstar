package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.util._


class ViewButtons(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var mapButton, teamButton, shipButton: TextButton = _

  construct()


  private def construct(): Unit = {
    this.align(Align.center)
    this.mapButton = new TextButton("Sector", Resources.buttonMapStyle0)
    this.teamButton = new TextButton("Crew", Resources.buttonMapStyle1)
    this.shipButton = new TextButton("Ship", Resources.buttonMapStyle2)
    mapButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.toggleView(0)
        teamButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    teamButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.toggleView(1)
        mapButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    shipButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.toggleView(2)
        mapButton.setChecked(false)
        teamButton.setChecked(false)
      }
    })
    this.add(mapButton).width(134).expand.fill
    this.add(teamButton).width(134).expand.fill
    this.add(shipButton).width(134).expand.fill
  }

  def getMapButton: TextButton = {
    mapButton
  }
}
