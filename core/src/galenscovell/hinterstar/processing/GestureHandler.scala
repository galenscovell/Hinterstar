package galenscovell.hinterstar.processing

import com.badlogic.gdx.input.GestureDetector
import galenscovell.hinterstar.ui.screens.GameScreen


class GestureHandler(root: GameScreen) extends GestureDetector.GestureAdapter {
  private val gameScreen: GameScreen = root


  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = {
    gameScreen.actionPan(deltaX / 10, -deltaY / 10)
    true
  }
}