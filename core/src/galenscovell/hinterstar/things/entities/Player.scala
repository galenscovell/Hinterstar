package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.ResourceManager


class Player(gameStage: GameStage) extends Actor {
  private var gameScreen: GameScreen = gameStage.gameScreen
  private val currentSprite: Sprite = ResourceManager.shipAtlas.createSprite("Ship-2")
  private var sprites: Array[Sprite] = null
  private var selected: Boolean = false

  this.setSize(270, 90)
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


  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (selected) {
      batch.setColor(0.5f, 0.5f, 1.0f, 1)
    }
    batch.draw(currentSprite, getX, getY, getWidth, getHeight)
    batch.setColor(1, 1, 1, 1)
  }
}

