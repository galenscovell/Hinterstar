package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.{InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.util.{CrewOperations, Resources}

import scala.collection.mutable.ArrayBuffer


class Ship(n: String, desc: String, w: Array[Weapon], s: Array[String]) {
  private val name: String = n
  private val description: String = desc
  private val subsystems: Array[String] = s

  private var weapons: Array[Weapon] = w
  private val activeWeaponPanel: Table = new Table
  private val weaponSelectPanel: Table = new Table
  weaponSelectPanel.setFillParent(true)


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
    w.activate()
    refreshActiveWeaponPanel()
  }

  def unequipWeapon(w: Weapon): Unit = {
    w.deactivate()
    w.resetFireBar()
    refreshActiveWeaponPanel()
  }

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

  def refreshActiveWeaponPanel(): Unit = {
    activeWeaponPanel.clearChildren()

    val weaponTable: Table = new Table
    weaponTable.setBackground(Resources.npTest1)

    for (weapon: Weapon <- weapons) {
      if (weapon.isActive) {
        val weaponLabel: Label = new Label(weapon.getName, Resources.labelTinyStyle)
        weaponLabel.setAlignment(Align.left, Align.center)

        val fireBarTable: Table = new Table
        fireBarTable.add(weapon.getFireBar).width(80).height(24)

        weaponTable.add(weaponLabel).expand.left.padLeft(4).padRight(4)
        weaponTable.add(fireBarTable).expand.left.padRight(4)

        activeWeaponPanel.add(weaponTable).expand.left.padRight(4)
      }
    }
  }

  def getWeaponSelectPanel: Table = {
    weaponSelectPanel
  }

  def refreshWeaponSelectPanel(subsystem: String): Unit = {
    weaponSelectPanel.clearChildren()

    val selectionLabel: Label = new Label("Select a weapon", Resources.labelMenuStyle)
    selectionLabel.setAlignment(Align.center)

    val selectionPanel: Table = new Table
    selectionPanel.setBackground(Resources.npTest1)

    val closeTable: Table = new Table
    val closeButton: Table = new TextButton("Close", Resources.greenButtonStyle)

    closeTable.add(closeButton).width(200).center.pad(4)
    selectionPanel.add(closeButton).expand.width(300).height(20).center
    closeButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.cancelWeaponAssignment()
      }
    })
    selectionPanel.row

    for (weapon: Weapon <- weapons) {
      if (!weapon.isActive && weapon.getSubsystem == subsystem) {
        val weaponTable: Table = new Table
        weaponTable.setBackground(Resources.npTest4)

        val topBarTable: Table = new Table
        topBarTable.setBackground(Resources.npTest3)

        val iconTable = new Table
        iconTable.setBackground(Resources.npTest0)

        val damageLabel: Label = new Label(weapon.getDamage.toString, Resources.labelDetailStyle)
        damageLabel.setAlignment(Align.center)

        topBarTable.add(iconTable).expand.fill.width(20).height(20)
        topBarTable.add(damageLabel).expand.fill.width(80).height(20)

        val weaponImage: Table = new Table
        weaponImage.setTouchable(Touchable.enabled)
        weaponImage.addListener(new ClickListener() {
          override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            equipWeapon(weapon)
            CrewOperations.equipWeapon(weapon)
          }
        })

        val bottomWeaponTable: Table = new Table
        bottomWeaponTable.setBackground(Resources.npTest1)
        val weaponLabel: Label = new Label(weapon.getName, Resources.labelDetailStyle)
        weaponLabel.setAlignment(Align.center)

        bottomWeaponTable.add(weaponLabel).expand.fill

        weaponTable.add(topBarTable).expand.fill.height(20)
        weaponTable.row
        weaponTable.add(weaponImage).expand.fill.height(50)
        weaponTable.row
        weaponTable.add(bottomWeaponTable).expand.fill.height(20)

        selectionPanel.add(weaponTable).expand.width(100).pad(5).left
      }
    }

    weaponSelectPanel.add(selectionLabel).expand.fill.center.top.padTop(48)
    weaponSelectPanel.row
    weaponSelectPanel.add(selectionPanel).expand.fill.height(120).center.bottom
  }
}
