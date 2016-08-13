package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import galenscovell.hinterstar.util.Resources


class Weapon(n: String, desc: String, dmg: Int, fr: Int, fx: String) {
  private val name: String = n
  private val description: String = desc
  private val damage: Int = dmg
  private val firerate: Int = fr
  private val effect: String = fx
  private var active: Boolean = false
  private val fireBar: ProgressBar = new ProgressBar(0, firerate, 1, false, Resources.healthBarStyle)
  fireBar.setValue(fireBar.getMinValue)
  fireBar.setAnimateDuration(0.1f)


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getDamage: Int = {
    damage
  }

  def getFirerate: Int = {
    firerate
  }

  def getEffect: String = {
    effect
  }

  def getFireBar: ProgressBar = {
    fireBar
  }

  def updateFireBar(): Boolean = {
    fireBar.setValue(fireBar.getValue + 1)

    if (fireBar.getValue == fireBar.getMaxValue) {
      fireBar.setValue(fireBar.getMinValue)
      true
    } else {
      false
    }
  }


  def isActive: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def activate: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def deactivate(): Unit = {
    // TODO: Not yet implemented
    active = false
  }
}
