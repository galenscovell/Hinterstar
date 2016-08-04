package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.generation.interior.InteriorOverlay
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class Player(actionStage: ActionStage) extends Group {
  private val gameScreen: GameScreen = actionStage.getGameScreen
  private val shipActor: Image = new Image(Resources.shipAtlas.createSprite(PlayerData.getShip.getName))
  private val overlayActor: Actor = new InteriorOverlay(PlayerData.getShip.getName)
  private var selected: Boolean = false

  initialize()


  /**
    * Set the Player Ship sprite, clickListener and ship movement animation.
    */
  private def initialize(): Unit = {
    this.setSize(320, 120)
    shipActor.setSize(320, 120)
    overlayActor.setSize(320, 120)

    shipActor.addListener(new ActorGestureListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
        selected = !selected
      }
    })

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, 6, 5.0f),
        Actions.moveBy(0, -6, 5.0f)
      )
    ))
  }

  this.addActor(shipActor)
  this.addActor(overlayActor)
}

