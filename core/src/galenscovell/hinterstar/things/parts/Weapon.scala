package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.util.Resources


class Weapon(name: String, subsystem: String, description: String, damage: Int,
             firerate: Int, accuracy: Int, shots: Int, effect: String,
             fxType: String, animationType: String, speed: Float) {
  private val fx: WeaponFx = new WeaponFx(fxType, animationType, speed)
  private var active: Boolean = false
  private var target: Tile = _
  private val fireBar: ProgressBar = new ProgressBar(0, firerate, 1, false, Resources.hullHealthBarStyle)
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

  def getAccuracy: Int = {
    accuracy
  }

  def getShots: Int = {
    shots
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

  def getTarget: Tile = {
    target
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

  def setTarget(tile: Tile): Unit = {
    target = tile
  }
}
