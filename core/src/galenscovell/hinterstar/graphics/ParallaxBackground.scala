package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.Batch
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
class ParallaxBackground(layers: Array[ParallaxLayer], w: Float, h: Float, var speed: Vector2) {
  private val camera: Camera = new OrthographicCamera(w, h)
  private val savedSpeed: Vector2 = new Vector2(0, 0)



  def getCamera: Camera = {
    camera
  }

  def setSpeed(newSpeed: Vector2): Unit = {
    speed = newSpeed
  }

  def getSpeed: Vector2 = {
    speed
  }

  def modifySpeed(dxSpeed: Vector2): Unit = {
    speed.x += dxSpeed.x
    speed.y += dxSpeed.y
  }

  def pause(): Unit = {
    savedSpeed.x = speed.x
    savedSpeed.y = speed.y
    speed.x = 0
    speed.y = 0
  }

  def unpause(): Unit = {
    speed.x = savedSpeed.x
    speed.y = savedSpeed.y
  }

  def render(delta: Float, batch: Batch): Unit = {
    camera.position.add(speed.x * delta, speed.y * delta, 0)
    batch.setProjectionMatrix(camera.projection)
    val parentAlpha: Float = batch.getColor.a

    batch.begin()

    for (layer <- layers) {
      val layerColor: Color = layer.getColor
      batch.setColor(layerColor.r, layerColor.g, layerColor.b, parentAlpha)

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
          batch.draw(layer.region, -camera.viewportWidth / 2 + currentX + layer.startPosition.x, -camera.viewportHeight / 2 + currentY + layer.startPosition.y)
          currentY += layer.region.getRegionHeight + layer.padding.y
        }

        currentX += layer.region.getRegionWidth + layer.padding.x
      }
    }

    batch.end()
  }
}
