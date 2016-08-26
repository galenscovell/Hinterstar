package galenscovell.hinterstar.graphics

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.util.Resources


/**
  * Weapon animation
  * Houses sprite, source coordinates, target coordinates, velocity, etc.
  */
class WeaponFx(fxType: String, speed: Float) {
  private val sprite: Sprite = new Sprite(Resources.atlas.createSprite(fxType))
  private var start: Vector2 = _
  private var pos: Vector2 = _
  private var destination: Vector2 = _
  private val velocity: Float = speed



  def fire(src: Vector2, dest: Vector2): Unit = {
    start = src
    pos = start.cpy()
    destination = dest
  }

  def done: Boolean = {
    // Return true if current position surpasses destination
    val diffX: Float = Math.abs(destination.x - pos.x)
    val diffY: Float = Math.abs(destination.y - pos.y)
    diffX <= 4 && diffY <= 8
  }

  def draw(delta: Float, spriteBatch: Batch): Unit = {
    pos.x += (destination.x - start.x) * velocity * delta
    pos.y += (destination.y - start.y) * velocity * delta
    spriteBatch.draw(sprite, pos.x, pos.y, 8, 8)

    spriteBatch.draw(sprite, start.x, start.y, 8, 8)
    spriteBatch.draw(sprite, destination.x, destination.y, 8, 8)
  }
}
