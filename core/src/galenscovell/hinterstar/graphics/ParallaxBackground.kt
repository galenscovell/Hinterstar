package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2


/**
 * A ParallaxBackground utilizes multiple ParallaxLayers to create a moving background with depth.
 * ParallaxBackground's have:
 *     multiple ParallaxLayers
 *     an OrthographicCamera created using a given w and h
 *     a SpriteBatch (the root spriteBatch is passed in)
 *     a speed (Vector2, can be modified)
 *     a savedSpeed (initially (0, 0), used for pause() and unpause())
 */
class ParallaxBackground(private val batch: SpriteBatch, private val layers: Array<ParallaxLayer?>,
                         w: Float, h: Float, private var speed: Vector2) {
    private val camera: Camera = OrthographicCamera(w, h)
    private val savedSpeed: Vector2 = Vector2(0f, 0f)



    fun setSpeed(newSpeed: Vector2): Unit {
        speed = newSpeed
    }

    fun getSpeed(): Vector2 {
        return speed
    }

    fun modifySpeed(dxSpeed: Vector2): Unit {
        speed.x += dxSpeed.x
        speed.y += dxSpeed.y
    }

    fun pause(): Unit {
        savedSpeed.x = speed.x
        savedSpeed.y = speed.y
        speed.x = 0f
        speed.y = 0f
    }

    fun unpause(): Unit {
        speed.x = savedSpeed.x
        speed.y = savedSpeed.y
    }

    fun render(delta: Float): Unit {
        camera.position.add(speed.x * delta, speed.y * delta, 0f)
        batch.projectionMatrix = camera.projection
        val parentAlpha: Float = batch.color.a
        batch.begin()

        for (layer in layers) {
            val layerColor: Color = layer!!.getColor()
            batch.setColor(layerColor.r, layerColor.g, layerColor.b, parentAlpha)

            var currentX: Float = -camera.position.x * layer.parallaxRatio.x % (layer.region.regionWidth + layer.padding.x)
            var currentY: Float

            if (speed.x < 0) {
                currentX += -(layer.region.regionWidth + layer.padding.x)
            }

            while (currentX < camera.viewportWidth) {
                currentY = -camera.position.y * layer.parallaxRatio.y % (layer.region.regionHeight + layer.padding.y)

                if (speed.y < 0) {
                    currentY += -(layer.region.regionHeight + layer.padding.y)
                }

                while (currentY < camera.viewportHeight) {
                    batch.draw(layer.region, -camera.viewportWidth / 2 + currentX + layer.startPosition.x, -camera.viewportHeight / 2 + currentY + layer.startPosition.y)
                    currentY += layer.region.regionHeight + layer.padding.y
                }

                currentX += layer.region.regionWidth + layer.padding.x
            }
        }

        batch.end()
    }
}
