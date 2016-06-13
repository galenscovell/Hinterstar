package galenscovell.hinterstar.processing.controls

import com.badlogic.gdx.input.GestureDetector
import galenscovell.hinterstar.ui.screens.{AbstractScreen, StartScreen}


class GestureHandler(root: AbstractScreen) extends GestureDetector.GestureAdapter {
  private val screen: AbstractScreen = root

  override def fling(x: Float, y: Float, button: Int): Boolean = {
    // pos x <- left | neg x -> right
    // println(button.toString + ": " + x.toString + ", " + y.toString)
    screen.asInstanceOf[StartScreen].updateContent(x < 0)
    true
  }
}
