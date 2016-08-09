package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics._
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
class ParallaxBackground(b: SpriteBatch, l: Array[ParallaxLayer], w: Float, h: Float, s: Vector2) {
  private val layers: Array[ParallaxLayer] = l
  private val camera: Camera = new OrthographicCamera(w, h)
  private val batch: SpriteBatch = b
  private var speed: Vector2 = s
  private val savedSpeed: Vector2 = new Vector2(0, 0)


  /**
    * Set parallax movement speed to a specific Vector2.
    */
  def setSpeed(newSpeed: Vector2): Unit = {
    speed = newSpeed
  }

  /**
    * Get current parallax movement speed as Vector2.
    */
  def getSpeed(): Vector2 = {
    speed
  }

  /**
    * Modify the parallax movement speed by a Vector2.
    */
  def modifySpeed(dxSpeed: Vector2): Unit = {
    speed.x += dxSpeed.x
    speed.y += dxSpeed.y
  }

  /**
    * Temporarily stop the parallax movement, saving the current speed.
    */
  def pause(): Unit = {
    savedSpeed.x = speed.x
    savedSpeed.y = speed.y
    speed.x = 0
    speed.y = 0
  }

  /**
    * Resume the parallax movement using the saved speed from pause().
    */
  def unpause(): Unit = {
    speed.x = savedSpeed.x
    speed.y = savedSpeed.y
  }

  /**
    * Render the parallax background, drawing the ParallaxLayers at different speeds.
    */
  def render(delta: Float): Unit = {
    camera.position.add(speed.x * delta, speed.y * delta, 0)
    batch.setProjectionMatrix(camera.projection)
    batch.setColor(1, 1, 1, 1)
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
          batch.draw(layer.region, -camera.viewportWidth / 2 + currentX + layer.startPosition.x, -camera.viewportHeight / 2 + currentY + layer.startPosition.y)
          currentY += layer.region.getRegionHeight + layer.padding.y
        }

        currentX += layer.region.getRegionWidth + layer.padding.x
      }
    }

    batch.end()
  }
}
