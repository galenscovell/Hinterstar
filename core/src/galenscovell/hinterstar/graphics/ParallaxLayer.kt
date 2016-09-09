package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2


/**
 * A ParallaxLayer is used by a ParallaxBackground as a layer of parallax.
 * ParallaxLayer's have:
 *     a TextureRegion background image
 *     a parallaxRatio (parallax amount)
 *     a startPosition (always the origin (0, 0) unless created with alternate constructor)
 *     a padding (amount of space around the layer to prevent graphics issues)
 */
class ParallaxLayer constructor(val region: TextureRegion, val parallaxRatio: Vector2, val padding: Vector2,
                    val layerColor: Color, val startPosition: Vector2) {


    fun getColor(): Color {
        return layerColor
    }
}
