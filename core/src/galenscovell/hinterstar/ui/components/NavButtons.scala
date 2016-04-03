package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager


class NavButtons(stage: GameStage) extends Table {
  private final val gameStage: GameStage = stage
  private var mapButton: TextButton = null
  private var teamButton: TextButton = null
  private var shipButton: TextButton = null
  construct()


  private def construct(): Unit = {
    this.align(Align.center)
    this.mapButton = new TextButton("Map", ResourceManager.button_mapStyle0)
    this.teamButton = new TextButton("Team", ResourceManager.button_mapStyle1)
    this.shipButton = new TextButton("Ship", ResourceManager.button_mapStyle2)
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
    this.add(mapButton).width(130).expand.fill
    this.add(teamButton).width(130).expand.fill
    this.add(shipButton).width(130).expand.fill
  }

  def getMapButton: TextButton = {
    mapButton
  }
}
