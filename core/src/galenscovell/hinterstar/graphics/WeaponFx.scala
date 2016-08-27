package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.util.Resources


/**
  * Weapon animation
  * Houses sprite, source coordinates, target coordinates, velocity, etc.
  */
class WeaponFx(fxType: String, speed: Float) {
  private val fxClass: String = fxType
  private val sprite: Sprite = new Sprite(Resources.atlas.createSprite(fxType))
  private var start: Vector2 = _
  private var pos: Vector2 = _
  private var destination: Vector2 = _
  private val velocity: Float = speed
  private var frames: Int = 60



  def fire(src: Vector2, dest: Vector2): Unit = {
    start = src
    pos = start.cpy()
    destination = dest
    frames = 60
  }

  def done: Boolean = {
    // Return true if current position surpasses destination or frames == 0
    fxClass match {
      case "fx_railgun" =>
        val diffX: Float = Math.abs(destination.x - pos.x)
        val diffY: Float = Math.abs(destination.y - pos.y)
        diffX <= 4 && diffY <= 4
      case "fx_basic_laser" =>
        frames -= 1
        frames <= 0
    }
  }

  def draw(delta: Float, spriteBatch: Batch): Unit = {
    fxClass match {
      case "fx_railgun" =>
        pos.x += (destination.x - start.x) * velocity * delta
        pos.y += (destination.y - start.y) * velocity * delta
        spriteBatch.draw(sprite, pos.x, pos.y, 4, 4)

      case "fx_basic_laser" =>
        val distance: Float = start.dst(destination)
        val degrees: Float = Math.toDegrees(Math.atan2(destination.y - start.y, destination.x - start.x)).toFloat
        spriteBatch.draw(sprite, start.x, start.y, 0, 0, distance, 4, 1, 1, degrees)
    }
  }
}
