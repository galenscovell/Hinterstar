package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.*
import galenscovell.hinterstar.util.*


class CurrentSystemAnimation {
    private val sprite: TextureRegion = Resources.atlas!!.createSprite("current_marker")
    private val size: Float = Constants.SYSTEMMARKER_SIZE * 3
    private var frame: Float = 0f
    private var lx: Float = 0f
    private var ly: Float = 0f


    fun setTarget(x: Float, y: Float): Unit {
        lx = x
        ly = y
    }

    fun render(batch: Batch): Unit {
        batch.draw(sprite, lx, ly, size / 2, size / 2, size, size, 1f, 1f, -frame)
        if (frame < 360) {
            frame += 0.5f
        } else {
            frame = 0f
        }
    }
}