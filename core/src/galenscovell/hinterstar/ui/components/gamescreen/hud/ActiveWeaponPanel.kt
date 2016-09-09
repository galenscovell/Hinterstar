package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.util.Resources


class ActiveWeaponPanel(rootShip: Ship) : Table() {


    fun refresh(weapons: List<Weapon>): Unit {
        this.clear()

        val activeWeaponTable: Table = Table()

        for (weapon: Weapon in weapons) {
            if (weapon.isActive()) {
                val weaponTable: Table = Table()
                weaponTable.background = Resources.npTest1

                val weaponLabel: Label = Label(weapon.getName(), Resources.labelTinyStyle)
                weaponLabel.setAlignment(Align.left, Align.center)

                val fireBarTable: Table = Table()
                fireBarTable.add(weapon.getFireBar()).width(80f).height(24f)

                weaponTable.add(weaponLabel).expand().left().padLeft(4f).padRight(4f)
                weaponTable.add(fireBarTable).expand().left().padRight(4f)

                activeWeaponTable.add(weaponTable).expand().left().padRight(4f)
            }
        }

        this.add(activeWeaponTable).expand().left()
    }
}
