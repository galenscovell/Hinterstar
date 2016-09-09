package galenscovell.hinterstar.generation.sector

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.util.*


class SystemMarker(x: Int, y: Int) : Actor() {
    val sx: Float = x * Constants.SYSTEMMARKER_SIZE
    val sy: Float = y * Constants.SYSTEMMARKER_SIZE

    private var frames: Int = 60
    private var markerType: MarkerType = MarkerType.EMPTY
    private var glowing: Boolean = true

    lateinit private var system: System
    lateinit private var sprite: Sprite



    /********************
     *     Getters     *
     ********************/
    fun getThisSystemMarker(): SystemMarker {
        return this
    }

    fun getSystem(): System {
        return system
    }

    fun isEmpty(): Boolean {
        return markerType == MarkerType.EMPTY
    }

    fun isCurrent(): Boolean {
        return markerType == MarkerType.CURRENT
    }

    fun isExplored(): Boolean {
        return markerType == MarkerType.EXPLORED
    }

    fun isUnexplored(): Boolean {
        return markerType == MarkerType.UNEXPLORED
    }



    /********************
     *     Setters     *
     ********************/
    fun setSystem(s: System): Unit {
        system = s
        this.addListener(object : ActorGestureListener() {
            override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit {
                SystemOperations.setSelection(s)
            }
        })
    }

    fun becomeEmpty(): Unit {
        markerType = MarkerType.EMPTY
    }

    fun becomeCurrent(): Unit {
        Resources.currentMarker.setTarget(
                sx + Constants.SYSTEM_MARKER_X - Constants.SYSTEMMARKER_SIZE,
                Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y - Constants.SYSTEMMARKER_SIZE
        )
        markerType = MarkerType.CURRENT
    }

    fun becomeExplored(): Unit {
        markerType = MarkerType.EXPLORED
    }

    fun becomeUnexplored(): Unit {
        sprite = Resources.spTest0
        markerType = MarkerType.UNEXPLORED
    }



    /**********************
     *     Rendering     *
     **********************/
    override fun draw(batch: Batch, parentAlpha: Float): Unit {
        if (!isEmpty()) {
            glow(batch)
            batch.draw(
                    sprite,
                    sx + Constants.SYSTEM_MARKER_X,
                    Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y,
                    Constants.SYSTEMMARKER_SIZE,
                    Constants.SYSTEMMARKER_SIZE
            )
            if (isCurrent()) {
                Resources.currentMarker.render(batch)
            }
        }
    }

    private fun glow(batch: Batch): Unit {
        if (glowing) {
            frames += 1
        } else {
            frames -= 2
        }

        if (frames == 120) {
            glowing = false
        } else if (frames == 40) {
            glowing = true
        }

        val frameAlpha: Float = frames / 120.0f
        if (isExplored()) {
            batch.setColor(0.2f, 0.2f, 0.75f, frameAlpha)
        } else {
            batch.setColor(0.2f, 0.75f, 0.2f, frameAlpha)
        }

        batch.draw(
                Resources.mapGlow,
                sx + Constants.SYSTEM_MARKER_X - Constants.SYSTEMMARKER_SIZE,
                Constants.EXACT_Y - sy + Constants.SYSTEM_MARKER_Y - Constants.SYSTEMMARKER_SIZE,
                Constants.SYSTEMMARKER_SIZE * 3,
                Constants.SYSTEMMARKER_SIZE * 3
        )
        batch.setColor(1f, 1f, 1f, 1f)
    }

    fun debugDraw(): String {
        when (markerType) {
            MarkerType.EMPTY -> return "~"
            MarkerType.UNEXPLORED -> return "U"
            MarkerType.EXPLORED -> return "E"
            MarkerType.CURRENT -> return "C"
            else -> return "!"
        }
    }
}
