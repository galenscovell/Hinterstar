package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.util.Resources


class WeaponFx(fxType: String, animationType: String, speed: Float) {
  private val sprite: Sprite = new Sprite(Resources.atlas.createSprite(fxType))

  private var start: Vector2 = _
  private var pos: Vector2 = _
  private var destination: Vector2 = _

  private var frames: Int = 60
  private var degrees: Float = _



  def fire(src: Vector2, dest: Vector2): Unit = {
    start = src
    pos = start.cpy()
    destination = dest
    degrees = Math.toDegrees(Math.atan2(destination.y - start.y, destination.x - start.x)).toFloat
    frames = 60
  }

  def done: Boolean = {
    // Return true if current position surpasses destination or frames == 0
    animationType match {
      case "shot" =>
        val diffX: Float = Math.abs(destination.x - pos.x)
        val diffY: Float = Math.abs(destination.y - pos.y)
        diffX <= sprite.getWidth && diffY <= sprite.getHeight

      case "stream" =>
        frames -= 1
        frames <= 0
    }
  }

  def draw(delta: Float, spriteBatch: Batch): Unit = {
//    spriteBatch.draw(Resources.spTest1, destination.x, destination.y, 8, 8)

    animationType match {
      case "shot" =>
        pos.x += (destination.x - start.x) * speed * delta
        pos.y += (destination.y - start.y) * speed * delta
        spriteBatch.draw(
          sprite, pos.x, pos.y,
          sprite.getWidth / 2, sprite.getHeight / 2,
          sprite.getWidth, sprite.getHeight,
          1, 1, degrees
        )

      case "stream" =>
        val distance: Float = start.dst(destination)
        spriteBatch.draw(sprite, start.x, start.y, 0, 0, distance, 48, 1, 1, degrees)
    }
  }
}
