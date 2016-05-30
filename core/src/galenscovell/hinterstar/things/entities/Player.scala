package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.{PlayerData, ResourceManager}


class Player(gameStage: GameStage) extends Actor {
  private val gameScreen: GameScreen = gameStage.gameScreen
  private var sprite: Sprite = null
  private var selected: Boolean = false

  initialize()


  private def initialize(): Unit = {
    // Pull current player ship from prefs and establish proper sprite
    val currentShip: String = PlayerData.prefs.getString("ship-chassis")
    this.sprite = ResourceManager.shipAtlas.createSprite(currentShip)
    this.setSize(270, 90)

    // Ship selection event listener
    this.addListener(new ActorGestureListener() {
      override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
        selected = !selected
      }
    })

    // Initialize default player ship movement animation
    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, 8, 4.0f),
        Actions.moveBy(0, -8, 4.0f)
      )
    ))
  }


  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (selected) {
      batch.setColor(0.5f, 0.5f, 1.0f, 1)
    }
    batch.draw(sprite, getX, getY, getWidth, getHeight)
    batch.setColor(1, 1, 1, 1)
  }
}

