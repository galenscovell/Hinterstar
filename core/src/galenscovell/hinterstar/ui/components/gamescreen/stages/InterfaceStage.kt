package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.components.gamescreen.hud.*
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.*


class InterfaceStage(private val gameScreen: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch,
                     player: Player) : Stage(viewport, spriteBatch) {
    private val travelPanel: TravelPanel = TravelPanel(this)
    private val travelButton: TravelButton = TravelButton(this)

    private val hullHealthPanel: HullHealthPanel = HullHealthPanel(this, player.getShip().getHealthBar())
    private val shipStatsPanel: ShipStatsPanel = ShipStatsPanel(this)
    private val crewPanel: CrewPanel = CrewPanel(this)
    private val activeWeaponPanel: Table = PlayerData.getShip().getActiveWeaponPanel()

    private val pauseInfo: InfoPanel = InfoPanel("Paused")
    private val assignmentSelectInfo: InfoPanel = InfoPanel("Select Subsystem to Assign Crewmate")
    private val targetSelectInfo: InfoPanel = InfoPanel("Select Enemy Subsystem to Target")
    private val weaponSelectInfo: InfoPanel = InfoPanel("Select Weapon to Equip")

    private var eventPanel: EventPanel? = null

    init {
        val mainTable: Table = Table()
        mainTable.setFillParent(true)

        val centerTable = Table()
        val actionTable = Table()

        centerTable.add(hullHealthPanel).width(24f).height(300f).expand().fill().left().bottom()
        centerTable.add(actionTable).width(Constants.EXACT_X - 24).expand().fill()

        mainTable.add(centerTable).width(Constants.EXACT_X).expand().fill()
        mainTable.row()
        mainTable.add(activeWeaponPanel).width(Constants.EXACT_X).height(32f).bottom()
        mainTable.row()
        mainTable.add(shipStatsPanel).width(180f).height(32f).left()
        mainTable.row()
        mainTable.add(crewPanel).width(Constants.EXACT_X).height(66f).bottom()

        this.addActor(mainTable)

        CrewOperations.setGameScreen(gameScreen)
        enableTravelButton()
    }



    fun hideUI(): Unit {
        crewPanel.addAction(Actions.sequence(
                Actions.touchable(Touchable.disabled),
                Actions.fadeOut(0.5f, Interpolation.sine)
        ))
        hullHealthPanel.addAction(Actions.sequence(
                Actions.touchable(Touchable.disabled),
                Actions.fadeOut(0.5f, Interpolation.sine)
        ))
        shipStatsPanel.addAction(Actions.sequence(
                Actions.touchable(Touchable.disabled),
                Actions.fadeOut(0.5f, Interpolation.sine)
        ))
        activeWeaponPanel.addAction(Actions.sequence(
                Actions.touchable(Touchable.disabled),
                Actions.fadeOut(0.5f, Interpolation.sine)
        ))
    }

    fun showUI(): Unit {
        crewPanel.addAction(Actions.sequence(
                Actions.fadeIn(0.5f, Interpolation.sine),
                Actions.touchable(Touchable.enabled)
        ))
        hullHealthPanel.addAction(Actions.sequence(
                Actions.fadeIn(0.5f, Interpolation.sine),
                Actions.touchable(Touchable.enabled)
        ))
        shipStatsPanel.addAction(Actions.sequence(
                Actions.fadeIn(0.5f, Interpolation.sine),
                Actions.touchable(Touchable.enabled)
        ))
        activeWeaponPanel.addAction(Actions.sequence(
                Actions.fadeIn(0.5f, Interpolation.sine),
                Actions.touchable(Touchable.enabled)
        ))
    }

    fun updateDistanceLabel(d: String): Unit {
        travelPanel.updateDistanceLabel(d)
    }

    fun getTravelButton(): TravelButton {
        return travelButton
    }

    fun getGameScreen(): GameScreen {
        return gameScreen
    }

    fun refreshCrewPanel(): Unit {
        crewPanel.refresh()
    }

    fun refreshStatsPanel(): Unit {
        shipStatsPanel.refresh()
    }



    /*************************
     *    Panel Handling    *
     *************************/
    fun enableTravelButton(): Unit {
        this.addActor(travelButton)
        travelButton.addAction(Actions.sequence(
                Actions.fadeIn(0.5f, Interpolation.sine)
        ))
    }

    fun disableTravelButton(): Unit {
        travelButton.addAction(Actions.sequence(
                Actions.fadeOut(0.5f, Interpolation.sine),
                Actions.removeActor()
        ))
    }

    fun openTravelPanel(): Unit {
        this.addActor(travelPanel)
        gameScreen.openTravelPanel()
    }

    fun closeTravelPanel(): Unit {
        travelPanel.remove()
        gameScreen.closeTravelPanel()
    }

    fun togglePause(): Unit {
        // Pause only happens during events
        if (pauseInfo.hasParent()) {
            pauseInfo.remove()
            gameScreen.setPause(false)
        } else {
            this.addActor(pauseInfo)
            gameScreen.setPause(true)
        }
    }

    fun openAssignmentSelect(): Unit {
        if (!assignmentSelectInfo.hasParent()) {
            this.addActor(assignmentSelectInfo)
        }
    }

    fun closeAssignmentSelect(): Unit {
        if (assignmentSelectInfo.hasParent()) {
            assignmentSelectInfo.remove()
        }
    }

    fun openTargetSelect(): Unit {
        if (!targetSelectInfo.hasParent()) {
            this.addActor(targetSelectInfo)
        }
    }

    fun closeTargetSelect(): Unit {
        if (targetSelectInfo.hasParent()) {
            targetSelectInfo.remove()
        }
    }

    fun openWeaponSelect(): Unit {
        this.addActor(PlayerData.getShip().getWeaponSelectPanel())

        if (!weaponSelectInfo.hasParent()) {
            this.addActor(weaponSelectInfo)
        }
    }

    fun closeWeaponSelect(): Unit {
        val weaponSelectPanel: Table = PlayerData.getShip().getWeaponSelectPanel()
        if (weaponSelectPanel.hasParent()) {
            weaponSelectPanel.remove()
        }
        if (weaponSelectInfo.hasParent()) {
            weaponSelectInfo.remove()
        }
    }

    private fun showEventPanel(): Unit {
        if (eventPanel != null) {
            eventPanel!!.remove()
            eventPanel = null
        }

        eventPanel = EventPanel(this, SystemOperations.parseNextEvent()!!)
        this.addActor(eventPanel)
        hideUI()
    }
}
