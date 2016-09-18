package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.util.Resources


class Ship() {
  private var activeWeapons: Int = 0

  // TODO: Set max health depending on ship
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, false, Resources.hullHealthBarStyle)
  healthBar.setValue(healthBar.getMaxValue)
  healthBar.setAnimateDuration(0.5f)



  /********************
    *     Getters     *
    ********************/
  def getHealthBar: ProgressBar = {
    healthBar
  }

  def getHealth: Int = {
    healthBar.getValue.toInt
  }



  /********************
    *     Setters     *
    ********************/
  def setHealth(amount: Int): Unit = {
    healthBar.setValue(amount)
  }

  def updateHealth(amount: Int): Unit = {
    var health: Int = healthBar.getValue.toInt + amount

    if (health < 0) {
      // TODO: This means game over, at some point
      health = 0
    } else if (health > healthBar.getMaxValue) {
      health = healthBar.getMaxValue.toInt
    }

    healthBar.setValue(health)
  }
}
