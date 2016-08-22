package galenscovell.hinterstar.graphics

import com.badlogic.gdx.math.Vector2


/**
  * Weapon animation
  * Houses sprite, source coordinates, target coordinates, velocity, etc.
  */
class WeaponFx(src: Vector2, dest: Vector2, spd: Float) {


  def done: Boolean = {
    // Return true if current position surpasses destination
    false
  }

  def draw(delta: Float): Unit = {

  }
}
