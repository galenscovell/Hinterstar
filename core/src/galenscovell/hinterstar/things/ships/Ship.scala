package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.components.gamescreen.hud.{ActiveWeaponPanel, WeaponSelectPanel}

import scala.collection.mutable.{ArrayBuffer, Map}


class Ship(name: String, description: String, var weapons: Array[Weapon], subsystemNames: Array[String]) {
  private var interiorOverlay: InteriorOverlay = _

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

  def getSubsystemNames: Array[String] = {
    subsystemNames
  }

  def isPlayerShip: Boolean = {
    isPlayer
  }

  def getSubsystemMap: Map[String, Tile] = {
    interiorOverlay.getSubsystemMap
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
