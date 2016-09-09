package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.util.*


class WeaponSelectPanel(rootShip: Ship) : Table() {


    fun refresh(weapons: List<Weapon>, subsystem: String, crewmate: String): Unit {
        this.clear()
        this.setFillParent(true)
        this.touchable = Touchable.enabled

        val selectionPanel: Table = Table()
        selectionPanel.background = Resources.npTest1

        val topTable: Table = Table()
        topTable.background = Resources.npTest4
        val crewmateLabel: Label = Label(crewmate, Resources.labelTinyStyle)
        crewmateLabel.setAlignment(Align.center)
        val closeButton: TextButton = TextButton("Close", Resources.greenButtonStyle)
        closeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                CrewOperations.cancelWeaponAssignment()
            }
        })

        topTable.add(crewmateLabel).expand().fill().width(200f).height(20f).left()
        topTable.add(closeButton).expand().fill().width(200f).height(20f).right()
        selectionPanel.add(topTable).expand().fill().height(20f).center()

        selectionPanel.row()

        val weaponTableHolder: Table = Table()

        for (weapon: Weapon in weapons) {
            val weaponTable: Table = Table()

            if (weapon.getSubsystem() == subsystem) {
                if (!weapon.isActive()) {
                    weaponTable.background = Resources.npTest4
                } else {
                    weaponTable.background = Resources.npTest0
                }
            } else {
                weaponTable.background = Resources.npTest2
            }

            val topBarTable: Table = Table()
            topBarTable.background = Resources.npTest3

            val iconTable = Table()
            iconTable.background = Resources.npTest0

            val damageLabel: Label = Label(weapon.getDamage().toString(), Resources.labelSmallStyle)
            damageLabel.setAlignment(Align.center)

            topBarTable.add(iconTable).expand().fill().width(20f).height(20f)
            topBarTable.add(damageLabel).expand().fill().width(80f).height(20f)

            val weaponImage: Table = Table()
            weaponImage.touchable = Touchable.enabled

            if (!weapon.isActive() && weapon.getSubsystem() == subsystem) {
                weaponImage.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                        CrewOperations.selectWeapon(weapon)
                    }
                })
            }

            val bottomWeaponTable: Table = Table()
            bottomWeaponTable.background = Resources.npTest1
            val weaponLabel: Label = Label(weapon.getName(), Resources.labelSmallStyle)
            weaponLabel.setAlignment(Align.center)

            bottomWeaponTable.add(weaponLabel).expand().fill()

            weaponTable.add(topBarTable).expand().fill().height(20f)
            weaponTable.row()
            weaponTable.add(weaponImage).expand().fill().height(50f)
            weaponTable.row()
            weaponTable.add(bottomWeaponTable).expand().fill().height(20f)

            weaponTableHolder.add(weaponTable).expand().width(100f).pad(5f).left()
        }

        selectionPanel.add(weaponTableHolder).expand().left()

        this.add(selectionPanel).expand().fill().height(120f).center().bottom()
    }
}
