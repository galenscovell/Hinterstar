package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.util.Resources


class WeaponFx(fxType: String, private val animationType: String, private val speed: Float) {
    private val sprite: Sprite = Sprite(Resources.atlas!!.createSprite(fxType))

    private var start: Vector2? = null
    private var pos: Vector2? = null
    private var destination: Vector2? = null

    private var frames: Int = 60
    private var degrees: Float = 0f



    fun fire(src: Vector2, dest: Vector2): Unit {
        start = src
        pos = start!!.cpy()
        destination = dest
        degrees = Math.toDegrees(
                Math.atan2(destination!!.y.toDouble() - start!!.y, destination!!.x.toDouble() - start!!.x)
        ).toFloat()
        frames = 60
    }

    fun done(): Boolean {
        // Return true if current position surpasses destination or frames == 0
        when (animationType) {
            "shot" -> {
                val diffX: Float = Math.abs(destination!!.x - pos!!.x)
                val diffY: Float = Math.abs(destination!!.y - pos!!.y)
                return diffX <= sprite.width && diffY <= sprite.height
            }
            "stream" -> {
                frames -= 1
                return frames <= 0
            }
            else -> return true
        }
    }

    fun draw(delta: Float, spriteBatch: Batch): Unit {
//    spriteBatch.draw(Resources.spTest1, destination.x, destination.y, 8, 8)

        when (animationType) {
            "shot" -> {
                pos!!.x += (destination!!.x - start!!.x) * speed * delta
                pos!!.y += (destination!!.y - start!!.y) * speed * delta
                spriteBatch.draw(
                        sprite, pos!!.x, pos!!.y,
                        sprite.width / 2, sprite.height / 2,
                        sprite.width, sprite.height,
                        1f, 1f, degrees
                )
            }
            "stream" -> {
                val distance: Float = start!!.dst(destination)
                spriteBatch.draw(sprite, start!!.x, start!!.y, 0f, 0f, distance, 48f, 1f, 1f, degrees)
            }
        }
    }
}
