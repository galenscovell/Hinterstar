package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager


class NavButtons(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var mapButton: TextButton = _
  private var teamButton: TextButton = _
  private var shipButton: TextButton = _

  construct()


  private def construct(): Unit = {
    this.align(Align.center)
    this.mapButton = new TextButton("Map", ResourceManager.buttonMapStyle0)
    this.teamButton = new TextButton("Team", ResourceManager.buttonMapStyle1)
    this.shipButton = new TextButton("Ship", ResourceManager.buttonMapStyle2)
    mapButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.togglePanel(0)
        teamButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    teamButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.togglePanel(1)
        mapButton.setChecked(false)
        shipButton.setChecked(false)
      }
    })
    shipButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        gameStage.togglePanel(2)
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
