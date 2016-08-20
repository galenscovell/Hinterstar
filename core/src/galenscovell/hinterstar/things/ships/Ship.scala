package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable.ArrayBuffer


class Ship(n: String, desc: String, w: Array[Weapon], s: Array[String]) {
  private val name: String = n
  private val description: String = desc
  private val subsystems: Array[String] = s

  private var weapons: Array[Weapon] = w
  private val equippedWeapons: ArrayBuffer[Weapon] = ArrayBuffer()
  private val weaponPanel: Table = new Table

  val debugWeapon: Weapon = new Weapon("Railgun", "None", 5, 10, "None")
  equipWeapon(debugWeapon)
  refreshWeaponPanel()


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getWeapons: Array[Weapon] = {
    weapons
  }

  def setWeapons(w: Array[Weapon]): Unit = {
    weapons = w
  }

  def getSubsystems: Array[String] = {
    subsystems
  }



  def equipWeapon(w: Weapon): Unit = {
    equippedWeapons.append(w)
  }

  def unequipWeapon(w: Weapon): Unit = {
    if (equippedWeapons.contains(w)) {
      val index: Int = weapons.indexOf(w)
      equippedWeapons.remove(index)
    }
  }

  def updateEquippedWeapons(): Array[Weapon] = {
    // Iterate through currently active weapons
    // If weapon is set to fire, append it to an array that is returned
    val readyWeapons: ArrayBuffer[Weapon] = ArrayBuffer()

    for (weapon: Weapon <- equippedWeapons) {
      if (weapon.updateFireBar()) {
        readyWeapons.append(weapon)
      }
    }

    readyWeapons.toArray
  }



  /**********************
    *    UI Component   *
    **********************/
  def getWeaponPanel: Table = {
    weaponPanel
  }

  def refreshWeaponPanel(): Unit = {
    val weaponTable: Table = new Table
    weaponTable.setBackground(Resources.npTest1)

    for (w: Weapon <- equippedWeapons) {
      val weaponLabel: Label = new Label(w.getName, Resources.labelTinyStyle)
      weaponLabel.setAlignment(Align.left, Align.center)

      val fireBarTable: Table = new Table
      fireBarTable.add(w.getFireBar).width(80).height(24)

      weaponTable.add(weaponLabel).expand.left.padLeft(4).padRight(4)
      weaponTable.add(fireBarTable).expand.left.padRight(4)

      weaponPanel.add(weaponTable).expand.left.padRight(4)
    }
  }
}
