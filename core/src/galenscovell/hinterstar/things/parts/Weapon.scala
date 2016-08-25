package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.util.Resources


class Weapon(n: String, ss: String, desc: String, dmg: Int, fr: Int, eff: String, fxType: String, speed: Float) {
  private val name: String = n
  private val subsystem: String = ss
  private val description: String = desc
  private val damage: Int = dmg
  private val firerate: Int = fr
  private val effect: String = eff
  private val fx: WeaponFx = new WeaponFx(fxType, speed)

  private var active: Boolean = false

  private val fireBar: ProgressBar = new ProgressBar(0, firerate, 1, false, Resources.healthBarStyle)
  fireBar.setValue(fireBar.getMinValue)
  fireBar.setAnimateDuration(0.5f)



  /********************
    *     Getters     *
    ********************/
  def getName: String = {
    name
  }

  def getSubsystem: String = {
    subsystem
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

  def isActive: Boolean = {
    active
  }

  def getFx: WeaponFx = {
    fx
  }



  /********************
    *     Setters     *
    ********************/
  def updateFireBar(): Boolean = {
    if (fireBar.getValue == fireBar.getMaxValue) {
      true
    } else {
      fireBar.setValue(fireBar.getValue + 1)
      false
    }
  }

  def resetFireBar(): Unit = {
    fireBar.setValue(fireBar.getMinValue)
  }

  def activate(): Unit = {
    active = true
  }

  def deactivate(): Unit = {
    active = false
  }
}
