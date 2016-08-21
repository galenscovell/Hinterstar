package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.{InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class WeaponSelectPanel(ship: Ship) extends Table {
  private val rootShip: Ship = ship

  this.setFillParent(true)
  this.setTouchable(Touchable.enabled)


  def refresh(weapons: Array[Weapon], subsystem: String): Unit = {
    this.clearChildren()

    val selectionLabel: Label = new Label("Select a weapon", Resources.labelMenuStyle)
    selectionLabel.setAlignment(Align.center)

    val selectionPanel: Table = new Table
    selectionPanel.setBackground(Resources.npTest1)

    val closeButton: Table = new TextButton("Close", Resources.greenButtonStyle)

    selectionPanel.add(closeButton).expand.width(300).height(20).center
    closeButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.cancelWeaponAssignment()
      }
    })
    selectionPanel.row

    val weaponTableHolder: Table = new Table

    for (weapon: Weapon <- weapons) {
      val weaponTable: Table = new Table

      if (weapon.getSubsystem == subsystem) {
        if (!weapon.isActive) {
          weaponTable.setBackground(Resources.npTest4)
        } else {
          weaponTable.setBackground(Resources.npTest0)
        }
      } else {
        weaponTable.setBackground(Resources.npTest2)
      }

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

      if (!weapon.isActive && weapon.getSubsystem == subsystem) {
        weaponImage.addListener(new ClickListener() {
          override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            rootShip.equipWeapon(weapon)
            CrewOperations.equipWeapon(weapon)
          }
        })
      }

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

      weaponTableHolder.add(weaponTable).expand.width(100).pad(5).left
    }

    selectionPanel.add(weaponTableHolder).expand.left

    this.add(selectionLabel).expand.fill.center.top.padTop(48)
    this.row
    this.add(selectionPanel).expand.fill.height(120).center.bottom
  }
}
