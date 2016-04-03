package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2


class ParallaxLayer(r: TextureRegion, pRatio: Vector2, pad: Vector2) {
  var region: TextureRegion = r
  var parallaxRatio: Vector2 = pRatio
  var startPosition: Vector2 = new Vector2(0, 0)
  var padding: Vector2 = pad


  def this(r: TextureRegion, pRatio: Vector2, startP: Vector2, pad: Vector2) {
    this(r, pRatio, pad)
    this.startPosition = startP
  }
}
