package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.*
import com.badlogic.gdx.utils.*
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.*
import galenscovell.hinterstar.util.*


class ShipSelectPanel : Table() {
    private val allShips: List<Ship> = ShipParser().parseAll()
    private var currentShipIndex: Int = 0

    private val shipDisplay: Table = Table()
    private val shipImage: Image = Image()
    private val shipDetail: Table = Table()

    init {
        val topTable: Table = createTopTable()
        val bottomTable: Table = Table()

        bottomTable.add(shipDetail)

        this.add(topTable).width(520f).height(140f).center()
        this.row()
        this.add(bottomTable).width(520f).height(260f).center()

        shipImage.setScaling(Scaling.fillY)
        shipDetail.background = Resources.npTest1
        shipDetail.color = Constants.NORMAL_UI_COLOR
    }



    fun getShip(): Ship {
        return allShips[currentShipIndex]
    }

    private fun createTopTable(): Table {
        val topTable: Table = Table()

        val scrollLeftButton: TextButton = TextButton("<", Resources.blueButtonStyle)
        scrollLeftButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                currentShipIndex -= 1
                if (currentShipIndex < 0) {
                    currentShipIndex = allShips.size - 1
                }
                updateShipDisplay(false)
                updateShipDetails()
            }
        })
        val scrollRightButton: TextButton = TextButton(">", Resources.blueButtonStyle)
        scrollRightButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                currentShipIndex += 1
                if (currentShipIndex > allShips.size - 1) {
                    currentShipIndex = 0
                }
                updateShipDisplay(true)
                updateShipDetails()
            }
        })

        topTable.add(scrollLeftButton).width(60f).height(130f).expand().fill().left()
        topTable.add(shipDisplay).expand().fill().center()
        topTable.add(scrollRightButton).width(60f).height(130f).expand().fill().right()

        return topTable
    }

    fun updateShipDisplay(transitionRight: Boolean): Unit {
        var amount: Float = 60f
        var origin: Float = 0f
        if (!transitionRight) {
            origin = amount * 2
            amount = -amount
        }

        shipDisplay.clearActions()
        shipDisplay.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.fadeOut(0.2f, Interpolation.sine),
                        Actions.moveBy(amount, 0f, 0.2f, Interpolation.sine)
                ),
                updateShipDisplayAction,
                Actions.moveTo(origin, 0f),
                Actions.parallel(
                        Actions.fadeIn(0.2f, Interpolation.sine),
                        Actions.moveBy(amount, 0f, 0.2f, Interpolation.sine)
                ),
                Actions.forever(
                        Actions.sequence(
                                Actions.moveBy(0f, 6f, 5.0f),
                                Actions.moveBy(0f, -6f, 5.0f)
                        )
                )
        ))
    }

    fun updateShipDetails(): Unit {
        shipDetail.clear()

        // Ship Detail Display
        val shipDetailTop: Table = Table()
        shipDetailTop.background = Resources.npTest1

        val shipNameLabel: Label = Label(allShips[currentShipIndex].getName(), Resources.labelLargeStyle)
        shipNameLabel.setAlignment(Align.center, Align.center)
        val shipDescLabel: Label = Label(allShips[currentShipIndex].getDescription(), Resources.labelMediumStyle)
        shipDescLabel.setAlignment(Align.top, Align.center)
        shipDescLabel.setWrap(true)

        shipDetailTop.add(shipNameLabel).expand().fill().height(20f).pad(5f)
        shipDetailTop.row()
        shipDetailTop.add(shipDescLabel).expand().fill().height(60f).pad(5f)

        val shipDetailBottom: Table = Table()

        // Ship Weapon Display
        // Max # starting weapons for any given ship is 4
        val shipWeaponTable: Table = Table()
        for (weapon: Weapon in allShips[currentShipIndex].getWeapons()) {
            val weaponTable: Table = Table()
            weaponTable.background = Resources.npTest4

            val topBarTable: Table = Table()
            topBarTable.background = Resources.npTest3
            val iconTable = Table()
            iconTable.background = Resources.npTest0
            val damageLabel: Label = Label(weapon.getDamage().toString(), Resources.labelSmallStyle)
            damageLabel.setAlignment(Align.center)

            topBarTable.add(iconTable).expand().fill().width(20f).height(20f)
            topBarTable.add(damageLabel).expand().fill().width(80f).height(20f)

            val weaponImage: Table = Table()

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

            shipWeaponTable.add(weaponTable).width(100f).pad(5f)
        }

        // Ship Subsystem Display
        // Max # subsystems for any given ship is 8
        val shipSubsystemTable: Table = Table()
        val shipSubsystems: List<String> = allShips[currentShipIndex].getSubsystemNames()

        // Row 1
        for (i in 0..4) {
        val subsystemTable: Table = Table()

        if (shipSubsystems.size >= i + 1) {
            val subsystem: String = shipSubsystems[i]

            subsystemTable.background = Resources.npTest4
            val subsystemLabel: Label = Label(subsystem, Resources.labelTinyStyle)
            subsystemLabel.setAlignment(Align.center)
            subsystemTable.add(subsystemLabel).expand().fill().pad(2f)
        }
        shipSubsystemTable.add(subsystemTable).width(120f).pad(2f)
    }

        shipSubsystemTable.row()

        // Row 2
        for (i in 4..8) {
        val subsystemTable: Table = Table()

        if (shipSubsystems.size >= i + 1) {
            val subsystem: String = shipSubsystems[i]

            subsystemTable.background = Resources.npTest4
            val subsystemLabel: Label = Label(subsystem, Resources.labelTinyStyle)
            subsystemLabel.setAlignment(Align.center)
            subsystemTable.add(subsystemLabel).expand().fill().pad(2f)
        }
        shipSubsystemTable.add(subsystemTable).width(120f).pad(2f)
    }

        shipDetailBottom.add(shipWeaponTable).expand().fill().height(95f).pad(2f)
        shipDetailBottom.row()
        shipDetailBottom.add(shipSubsystemTable).expand().fill().height(30f).pad(2f)

        shipDetail.add(shipDetailTop).expand().fill().width(516f).height(100f).top().pad(2f)
        shipDetail.row()
        shipDetail.add(shipDetailBottom).expand().fill().width(516f).height(156f).top().pad(2f)

        shipDetail.addAction(Actions.sequence(
                Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
                Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine)
        ))
    }



    /***************************
     * Custom Scene2D Actions *
     ***************************/
    private var updateShipDisplayAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            val shipName: String = allShips[currentShipIndex].getName()
            shipImage.drawable = TextureRegionDrawable(Resources.atlas.findRegion(shipName))

            if (!shipImage.hasParent()) {
                shipDisplay.add(shipImage).height(130f)
            }
            return true
        }
    }
}
