package galenscovell.hinterstar.processing.controls

import com.badlogic.gdx.input.GestureDetector
import galenscovell.hinterstar.ui.screens.GameScreen


class GestureHandler(root: GameScreen) extends GestureDetector.GestureAdapter {
  private val gameScreen: GameScreen = root


  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = {
    gameScreen.actionPan(deltaX / 5)
    true
  }

  override def panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    true
  }

  override def zoom(initialDistance: Float, endDistance: Float): Boolean = {
    val distance: Float = (initialDistance - endDistance) / 30000
    gameScreen.actionZoom(distance)
    true
  }
}
