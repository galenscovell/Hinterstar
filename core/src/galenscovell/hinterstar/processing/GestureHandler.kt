package galenscovell.hinterstar.processing

import com.badlogic.gdx.input.GestureDetector
import galenscovell.hinterstar.ui.screens.GameScreen


class GestureHandler(private val root: GameScreen) : GestureDetector.GestureAdapter() {



    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        root.actionPan(deltaX / 10, -deltaY / 10)
        return true
    }
}