package galenscovell.hinterstar.things.entity

import com.badlogic.gdx.graphics.g2d._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.ResourceManager


class Player(gameStage: GameStage) extends Actor {
  private var gameScreen: GameScreen = gameStage.gameScreen
  private var currentSprite: Sprite = ResourceManager.uiAtlas.createSprite("placeholder_vehicle")
  private var sprites: Array[Sprite] = null
  private var selected: Boolean = false

  this.setSize(270, 90)
  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      selected = !selected
    }
  })

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (selected) {
      batch.setColor(0.5f, 0.5f, 1.0f, 1)
    }
    batch.draw(currentSprite, getX, getY, getWidth, getHeight)
    batch.setColor(1, 1, 1, 1)
  }
}

