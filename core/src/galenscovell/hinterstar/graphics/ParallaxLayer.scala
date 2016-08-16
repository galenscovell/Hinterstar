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
class ParallaxLayer(r: TextureRegion, pRatio: Vector2, pad: Vector2, color: Color) {
  val region: TextureRegion = r
  val parallaxRatio: Vector2 = pRatio
  var startPosition: Vector2 = new Vector2(0, 0)
  val padding: Vector2 = pad
  val layerColor: Color = color

  /**
    * Alternate constructor for ParallaxLayer involving a starting position Vector2.
    */
  def this(r: TextureRegion, pRatio: Vector2, startP: Vector2, pad: Vector2, color: Color) {
    this(r, pRatio, pad, color)
    this.startPosition = startP
  }

  def getColor: Color = {
    layerColor
  }
}
