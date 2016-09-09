package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.Resources


class CrewmateFlag(val name: String) {
    private val sprite: Sprite = Sprite(Resources.atlas.createSprite("icon_crewmate_flag"))
    private var active: Boolean = false
    private var frames: Int = 0
    private var index: Int = 0

    private val path: MutableList<Tile> = mutableListOf()

    private var position: Vector2? = null
    private var destination: Vector2? = null



    /********************
     *     Getters     *
     ********************/
    fun isActive(): Boolean {
        return active
    }

    fun getPreviousTile(): Tile {
        if (index > 1) {
            return path[index - 2]
        } else {
            return path[0]
        }
    }

    fun getCurrentTile(): Tile {
        return path[index - 1]
    }

    fun getCurrentPosition(): Vector2 {
        return position!!
    }

    fun hasPath(): Boolean {
        return (path.size > 0) && (index < path.size)
    }



    /********************
     *     Setters     *
     ********************/
    fun setPath(startVector: Vector2, p: List<Tile>?): Unit {
        path.clear()
        for (t: Tile in p!!) {
            path.add(t)
        }

        index = 0
        position = startVector
        destination = startVector
    }

    fun setPath(p: List<Tile>?): Unit {
        setPath(p!![0].getActorCoordinates(), p)
    }

    fun setNextDestination(): Unit {
        index += 1

        if (index < path.size) {
            destination = path[index].getActorCoordinates().cpy()
        }
    }



    /*******************
     *    Updating    *
     *******************/
    fun start(f: Int): Unit {
        frames = f
        active = true
    }

    fun step(): Boolean {
        frames -= 1
        return frames <= 0
    }

    fun stop(): Unit {
        active = false
    }



    /********************
     *    Rendering    *
     ********************/
    fun drawing(): Boolean {
        if (position != null && destination != null) {
            val diffX: Float = Math.abs(destination!!.x - position!!.x)
            val diffY: Float = Math.abs(destination!!.y - position!!.y)
            return diffX > 12 && diffY > 12
        } else {
            return false
        }
    }

    fun draw(delta: Float, spriteBatch: Batch): Unit {
        position!!.x += (destination!!.x - position!!.x) * 0.5f * delta
        position!!.y += (destination!!.y - position!!.y) * 0.5f * delta

        spriteBatch.draw(sprite, position!!.x - 6, position!!.y - 6, 24f, 24f)
    }
}
