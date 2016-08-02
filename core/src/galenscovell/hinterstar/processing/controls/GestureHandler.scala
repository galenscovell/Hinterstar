package galenscovell.hinterstar.processing.controls

import com.badlogic.gdx.input.GestureDetector
import galenscovell.hinterstar.ui.screens.GameScreen


class GestureHandler(root: GameScreen) extends GestureDetector.GestureAdapter {
  private val gameScreen: GameScreen = root

  override def pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean = {
    gameScreen.actionPan(deltaX, deltaY)
    true
  }

  override def zoom(initialDistance: Float, endDistance: Float): Boolean = {
    val distance: Float = (endDistance - initialDistance) / 10000
    gameScreen.actionZoom(distance)
    true
  }
}
