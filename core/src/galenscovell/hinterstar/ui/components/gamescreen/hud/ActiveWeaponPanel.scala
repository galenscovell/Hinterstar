package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.util.Resources


class ActiveWeaponPanel(rootShip: Ship) extends Table {



  def refresh(weapons: Array[Weapon]): Unit = {
    this.clear()

    val activeWeaponTable: Table = new Table

    for (weapon: Weapon <- weapons) {
      if (weapon.isActive) {
        val weaponTable: Table = new Table
        weaponTable.setBackground(Resources.npDarkBlue)

        val weaponLabel: Label = new Label(weapon.getName, Resources.labelTinyStyle)
        weaponLabel.setAlignment(Align.left, Align.center)

        val fireBarTable: Table = new Table
        fireBarTable.add(weapon.getFireBar).width(80).height(24)

        weaponTable.add(weaponLabel).expand.left.padLeft(4).padRight(4)
        weaponTable.add(fireBarTable).expand.left.padRight(4)

        activeWeaponTable.add(weaponTable).expand.left.padRight(4)
      }
    }

    this.add(activeWeaponTable).expand.left
  }
}
