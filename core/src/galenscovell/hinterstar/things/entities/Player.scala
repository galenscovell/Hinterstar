package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


/**
  * The Player class simply holds the Ship sprite, clickListener and movement animation.
  */
class Player(gameStage: GameStage) extends Actor {
  private val gameScreen: GameScreen = gameStage.gameScreen
  private var sprite: Sprite = _
  private var selected: Boolean = false

  initialize()


  /**
    * Set the Player Ship sprite, clickListener and ship movement animation.
    */
  private def initialize(): Unit = {
    val currentShip: String = PlayerData.getPrefs.getString("ship-chassis")
    sprite = Resources.shipAtlas.createSprite(currentShip)
    this.setSize(300, 100)

    this.addListener(new ActorGestureListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
        selected = !selected
      }
    })

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, 8, 4.0f),
        Actions.moveBy(0, -8, 4.0f)
      )
    ))
  }

  /**
    * Render the Player Ship sprite.
    */
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (selected) {
      batch.setColor(0.5f, 0.5f, 1.0f, 1)
    }
    batch.draw(sprite, getX, getY, getWidth, getHeight)
    batch.setColor(1, 1, 1, 1)
  }
}

