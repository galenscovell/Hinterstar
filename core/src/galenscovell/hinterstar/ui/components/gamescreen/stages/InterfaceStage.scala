package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.ui.components.gamescreen.hud._
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class InterfaceStage(gameScreen: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val travelPanel: TravelPanel = new TravelPanel(this)
  private val travelButton: TravelButton = new TravelButton(this)

  private val hullHealthPanel: HullHealthPanel = new HullHealthPanel(this)
  private val shipStatsPanel: ShipStatsPanel = new ShipStatsPanel(this)
  private val crewPanel: CrewPanel = new CrewPanel(this)
  private val activeWeaponPanel: Table = PlayerData.getShip.getActiveWeaponPanel

  private var eventPanel: EventPanel = _
  private val pausePanel: PausePanel = new PausePanel()

  construct()
  CrewOperations.initialize(gameScreen)

  // DEBUG
  enableTravelButton()



  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val centerTable: Table = new Table
    val actionTable = new Table

    centerTable.add(actionTable).width(Constants.EXACT_X).expand.fill.center

    mainTable.add(hullHealthPanel).width(Constants.EXACT_X / 2).height(32).left.padLeft(8)
    mainTable.row
    mainTable.add(shipStatsPanel).width(Constants.EXACT_X / 2).height(32).left.padLeft(8)
    mainTable.row
    mainTable.add(centerTable).width(Constants.EXACT_X).expand.fill
    mainTable.row
    mainTable.add(activeWeaponPanel).width(Constants.EXACT_X).height(32).bottom
    mainTable.row
    mainTable.add(crewPanel).width(Constants.EXACT_X).height(66).bottom

    this.addActor(mainTable)
  }

  def enableTravelButton(): Unit = {
    this.addActor(travelButton)
    travelButton.addAction(Actions.sequence(
      Actions.fadeIn(0.5f, Interpolation.sine)
    ))
  }

  def disableTravelButton(): Unit = {
    travelButton.addAction(Actions.sequence(
      Actions.fadeOut(0.5f, Interpolation.sine),
      Actions.removeActor()
    ))
  }

  def openTravelPanel(): Unit = {
    this.addActor(travelPanel)
    gameScreen.openTravelPanel()
  }

  def closeTravelPanel(): Unit = {
    travelPanel.remove()
    gameScreen.closeTravelPanel()
  }

  def hideUI(): Unit = {
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

  def showUI(): Unit = {
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

  def updateDistanceLabel(d: String): Unit = {
    travelPanel.updateDistanceLabel(d)
  }

  def getTravelButton: TravelButton = {
    travelButton
  }

  def getGameScreen: GameScreen = {
    gameScreen
  }

  def togglePause(): Unit = {
    // Pause only happens during events
    if (pausePanel.hasParent) {
      pausePanel.remove()
      gameScreen.setPause(false)
    } else {
      this.addActor(pausePanel)
      gameScreen.setPause(true)
    }
  }

  def refreshCrewPanel(): Unit = {
    crewPanel.refresh()
  }

  def refreshStatsPanel(): Unit = {
    shipStatsPanel.refresh()
  }

  def openWeaponSelect(): Unit = {
    this.addActor(PlayerData.getShip.getWeaponSelectPanel)
  }

  def closeWeaponSelect(): Unit = {
    val weaponSelectPanel: Table = PlayerData.getShip.getWeaponSelectPanel
    if (weaponSelectPanel.hasParent) {
      weaponSelectPanel.remove()
    }
  }

  private def showEventPanel(): Unit = {
    if (eventPanel != null) {
      eventPanel.remove()
      eventPanel = null
    }

    eventPanel = new EventPanel(this, SystemOperations.parseNextEvent)
    this.addActor(eventPanel)
    hideUI()
  }
}
