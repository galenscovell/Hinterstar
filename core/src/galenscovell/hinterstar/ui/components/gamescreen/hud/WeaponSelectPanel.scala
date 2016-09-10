package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.{InputEvent, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class WeaponSelectPanel(rootShip: Ship) extends Table {
  this.setFillParent(true)
  this.setTouchable(Touchable.enabled)



  def refresh(weapons: Array[Weapon], subsystem: String, crewmate: String): Unit = {
    this.clear()

    val selectionPanel: Table = new Table
    selectionPanel.setBackground(Resources.npDarkBlue)

    val topTable: Table = new Table
    topTable.setBackground(Resources.npDarkGray)
    val crewmateLabel: Label = new Label(crewmate, Resources.labelTinyStyle)
    crewmateLabel.setAlignment(Align.center)
    val closeButton: TextButton = new TextButton("Close", Resources.greenButtonStyle)
    closeButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        CrewOperations.cancelWeaponAssignment()
      }
    })

    topTable.add(crewmateLabel).expand.fill.width(200).height(20).left
    topTable.add(closeButton).expand.fill.width(200).height(20).right
    selectionPanel.add(topTable).expand.fill.height(20).center

    selectionPanel.row

    val weaponTableHolder: Table = new Table

    for (weapon: Weapon <- weapons) {
      val weaponTable: Table = new Table

      if (weapon.getSubsystem == subsystem) {
        if (!weapon.isActive) {
          weaponTable.setBackground(Resources.npDarkGray)
        } else {
          weaponTable.setBackground(Resources.npGreen)
        }
      } else {
        weaponTable.setBackground(Resources.npGray)
      }

      val topBarTable: Table = new Table
      topBarTable.setBackground(Resources.npBlue)

      val iconTable = new Table
      iconTable.setBackground(Resources.npGreen)

      val damageLabel: Label = new Label(weapon.getDamage.toString, Resources.labelSmallStyle)
      damageLabel.setAlignment(Align.center)

      topBarTable.add(iconTable).expand.fill.width(20).height(20)
      topBarTable.add(damageLabel).expand.fill.width(80).height(20)

      val weaponImage: Table = new Table
      weaponImage.setTouchable(Touchable.enabled)

      if (!weapon.isActive && weapon.getSubsystem == subsystem) {
        weaponImage.addListener(new ClickListener() {
          override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            CrewOperations.selectWeapon(weapon)
          }
        })
      }

      val bottomWeaponTable: Table = new Table
      bottomWeaponTable.setBackground(Resources.npDarkBlue)
      val weaponLabel: Label = new Label(weapon.getName, Resources.labelSmallStyle)
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

    this.add(selectionPanel).expand.fill.height(120).center.bottom
  }
}
