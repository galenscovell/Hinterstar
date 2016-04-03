package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2


class ParallaxBackground(b: SpriteBatch, l: Array[ParallaxLayer], w: Float, h: Float, s: Vector2) {
  private val layers: Array[ParallaxLayer] = l
  private val camera: Camera = new OrthographicCamera(w, h)
  private val batch: SpriteBatch = b
  private var speed: Vector2 = s


  def setSpeed(speed: Vector2): Unit = {
    this.speed = speed
  }

  def modifySpeed(dxSpeed: Vector2): Unit = {
    this.speed.x += dxSpeed.x
    this.speed.y += dxSpeed.y
  }

  def render(delta: Float): Unit = {
    this.camera.position.add(speed.x * delta, speed.y * delta, 0)
    batch.setProjectionMatrix(camera.projection)
    batch.begin()
    for (layer <- layers) {
      var currentX: Float = -camera.position.x * layer.parallaxRatio.x % (layer.region.getRegionWidth + layer.padding.x)
      var currentY: Float = 0.0f
      if (speed.x < 0) {
        currentX += -(layer.region.getRegionWidth + layer.padding.x)
      }
      while (currentX < camera.viewportWidth) {
        currentY = -camera.position.y * layer.parallaxRatio.y % (layer.region.getRegionHeight + layer.padding.y)
        if (speed.y < 0) {
          currentY += -(layer.region.getRegionHeight + layer.padding.y)
        }
        while (currentY < camera.viewportHeight) {
          batch.draw(layer.region, -this.camera.viewportWidth / 2 + currentX + layer.startPosition.x, -this.camera.viewportHeight / 2 + currentY + layer.startPosition.y)
          currentY += layer.region.getRegionHeight + layer.padding.y
        }
        currentX += layer.region.getRegionWidth + layer.padding.x
      }
    }
    batch.end()
  }
}

