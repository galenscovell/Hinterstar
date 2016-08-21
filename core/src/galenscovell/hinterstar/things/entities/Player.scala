package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.generation.interior.InteriorOverlay
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class Player(actionStage: ActionStage) extends Group {
  private val gameScreen: GameScreen = actionStage.getGameScreen
  private val shipActor: Image = new Image(Resources.atlas.createSprite(PlayerData.getShip.getName))
  private val overlayActor: Actor = new InteriorOverlay(PlayerData.getShip)

  construct()


  private def construct(): Unit = {
    this.setSize(480, 192)
    shipActor.setSize(480, 192)
    overlayActor.setSize(480, 192)

    this.addActor(shipActor)

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, 6, 5.0f),
        Actions.moveBy(0, -6, 5.0f)
      )
    ))
  }

  def overlayPresent(): Boolean = {
    overlayActor.hasParent
  }

  def enableOverlay(): Unit = {
    this.addActor(overlayActor)
  }

  def disableOverlay(): Unit = {
    overlayActor.remove()
  }
}
