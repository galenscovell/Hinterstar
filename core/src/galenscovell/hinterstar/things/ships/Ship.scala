package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.components.gamescreen.hud.{ActiveWeaponPanel, WeaponSelectPanel}

import scala.collection.mutable.ArrayBuffer


class Ship(n: String, desc: String, w: Array[Weapon], s: Array[String]) {
  private val name: String = n
  private val description: String = desc
  private val subsystems: Array[String] = s

  private var weapons: Array[Weapon] = w
  private val activeWeaponPanel: ActiveWeaponPanel = new ActiveWeaponPanel(this)
  private val weaponSelectPanel: WeaponSelectPanel = new WeaponSelectPanel(this)

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

  def getSubsystems: Array[String] = {
    subsystems
  }

  def isPlayerShip: Boolean = {
    isPlayer
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



  /*******************
    *     Update     *
    *******************/
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
  def getActiveWeaponPanel: Table = {
    activeWeaponPanel
  }

  def getWeaponSelectPanel: Table = {
    weaponSelectPanel
  }

  def refreshWeaponSelectPanel(subsystem: String): Unit = {
    weaponSelectPanel.refresh(weapons, subsystem)
  }
}
