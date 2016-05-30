package galenscovell.hinterstar.processing.controls

import com.badlogic.gdx.InputAdapter
import galenscovell.hinterstar.ui.screens.GameScreen


class InputHandler(gameScreen: GameScreen) extends InputAdapter {
  private final val game: GameScreen = gameScreen
  private var startX: Int = 0
  private var startY: Int = 0


  override def touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean = {
    startX = x
    startY = y
    false
  }


  override def touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean = {
    if (Math.abs(x - startX) < 10 && Math.abs(y - startY) < 10) {
      println("Input: " + x + ", " + y)
      true
    }
    else {
      false
    }
  }


  override def scrolled(amount: Int): Boolean = {
    true
  }
}