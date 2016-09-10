package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.components.gamescreen.hud.{ActiveWeaponPanel, WeaponSelectPanel}
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable.{ArrayBuffer, Map}


class Ship(name: String, description: String, var weapons: Array[Weapon], subsystemNames: Array[String]) {
  private var interiorOverlay: InteriorOverlay = _
  private val activeWeaponPanel: ActiveWeaponPanel = new ActiveWeaponPanel(this)
  private val weaponSelectPanel: WeaponSelectPanel = new WeaponSelectPanel(this)

  // TODO: Set max health depending on ship
  private val healthBar: ProgressBar = new ProgressBar(0, 100, 1, true, Resources.hullHealthBarStyle)
  healthBar.setValue(healthBar.getMaxValue)
  healthBar.setAnimateDuration(0.5f)

  private var isPlayer: Boolean = false



  /********************
    *     Getters     *
    ********************/
  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getWeapons: Array[Weapon] = {
    weapons
  }

  def getSubsystemNames: Array[String] = {
    subsystemNames
  }

  def isPlayerShip: Boolean = {
    isPlayer
  }

  def getSubsystemMap: Map[String, Tile] = {
    interiorOverlay.getSubsystemMap
  }

  def getHealthBar: ProgressBar = {
    healthBar
  }

  def getHealth: Int = {
    healthBar.getValue.toInt
  }



  /********************
    *     Setters     *
    ********************/
  def setWeapons(w: Array[Weapon]): Unit = {
    weapons = w
  }

  def setPlayerShip(): Unit = {
    isPlayer = true
  }

  def equipWeapon(w: Weapon): Unit = {
    w.activate()
    activeWeaponPanel.refresh(weapons)
  }

  def unequipWeapon(w: Weapon): Unit = {
    w.resetFireBar()
    w.deactivate()
    activeWeaponPanel.refresh(weapons)
  }

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



  /*********************
    *     Updating     *
    *********************/
  def updateActiveWeapons(): Array[Weapon] = {
    val readyWeapons: ArrayBuffer[Weapon] = ArrayBuffer()

    for (weapon: Weapon <- weapons) {
      if (weapon.isActive && weapon.updateFireBar()) {
        readyWeapons.append(weapon)
      }
    }

    readyWeapons.toArray
  }



  /***********************
    *    UI Component    *
    ***********************/
  def createInterior(): Unit = {
    interiorOverlay = new InteriorOverlay(this)
  }

  def getInterior: InteriorOverlay = {
    interiorOverlay
  }

  def getActiveWeaponPanel: Table = {
    activeWeaponPanel
  }

  def getWeaponSelectPanel: Table = {
    weaponSelectPanel
  }

  def refreshWeaponSelectPanel(subsystem: String, crewmate: String): Unit = {
    weaponSelectPanel.refresh(weapons, subsystem, crewmate)
  }
}
