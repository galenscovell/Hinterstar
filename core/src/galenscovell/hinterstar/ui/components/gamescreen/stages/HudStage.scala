package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.ui.components.gamescreen.hud._
import galenscovell.hinterstar.ui.components.gamescreen.views._
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class HudStage(game: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val gameScreen: GameScreen = game

  private val sectorView: SectorView = new SectorView(this)
  private val crewView: CrewView = new CrewView(this)
  private val shipView: ShipView = new ShipView(this)
  private val viewButtons: ViewButtons = new ViewButtons(this)

  private val hullHealthPanel: HullHealthPanel = new HullHealthPanel(this)
  private val shipStatsPanel: ShipStatsPanel = new ShipStatsPanel(this)
  private val crewPanel: CrewPanel = new CrewPanel(this)
  private val weaponPanel: WeaponPanel = new WeaponPanel(this)
  private val topTable: Table = new Table

  private var eventPanel: EventPanel = _
  private val pausePanel: PausePanel = new PausePanel()

  construct()
  CrewOperations.initialize(gameScreen)


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val centerTable: Table = new Table
    val actionTable = new Table

    topTable.add(hullHealthPanel)
      .width((Constants.EXACT_X / 2) - 20)
      .left.padLeft(10).padRight(10)
    topTable.add(viewButtons)
      .width(Constants.EXACT_X / 2)
      .height(32)
      .right

    centerTable.add(actionTable)
      .width(Constants.EXACT_X)
      .expand.fill.center

    mainTable.add(topTable)
      .width(Constants.EXACT_X)
    mainTable.row
    mainTable.add(shipStatsPanel)
      .width(Constants.EXACT_X / 2)
      .height(32)
      .left
    mainTable.row
    mainTable.add(centerTable)
      .width(Constants.EXACT_X)
      .expand.fill
    mainTable.row
    mainTable.add(weaponPanel)
      .width(Constants.EXACT_X)
      .height(32)
      .bottom
    mainTable.row
    mainTable.add(crewPanel)
      .width(Constants.EXACT_X)
      .height(60)
      .bottom
    this.addActor(mainTable)
  }

  def toggleView(num: Int): Unit = {
    if (!sectorView.hasActions) {
      if (sectorView.hasParent) {
        sectorView.addAction(Actions.sequence(
          sectorViewAction,
          Actions.moveTo(800, 0, 0.2f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 0) {
        this.addActor(sectorView)
        sectorView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.2f, Interpolation.sine),
          sectorViewAction
        ))
      }
    }

    if (!crewView.hasActions) {
      if (crewView.hasParent) {
        crewView.addAction(Actions.sequence(
          crewViewAction,
          Actions.moveTo(800, 0, 0.2f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 1) {
        this.addActor(crewView)
        crewView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.2f, Interpolation.sine),
          crewViewAction
        ))
      }
    }

    if (!shipView.hasActions) {
      if (shipView.hasParent) {
        shipView.addAction(Actions.sequence(
          shipViewAction,
          Actions.moveTo(800, 0, 0.2f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 2) {
        this.addActor(shipView)
        shipView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.2f, Interpolation.sine),
          shipViewAction
        ))
      }
    }
  }

  def hideUI(): Unit = {
    crewPanel.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.fadeOut(0.5f, Interpolation.sine)
    ))
    topTable.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.fadeOut(0.5f, Interpolation.sine)
    ))
    shipStatsPanel.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.fadeOut(0.5f, Interpolation.sine)
    ))
    weaponPanel.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.fadeOut(0.5f, Interpolation.sine)
    ))
  }

  def showUI(): Unit = {
    crewPanel.addAction(Actions.sequence(
      Actions.fadeIn(0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    topTable.addAction(Actions.sequence(
      Actions.fadeIn(0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    shipStatsPanel.addAction(Actions.sequence(
      Actions.fadeIn(0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    weaponPanel.addAction(Actions.sequence(
      Actions.fadeIn(0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
  }

  def updateDistanceLabel(d: String): Unit = {
    sectorView.updateDistanceLabel(d)
  }

  def getViewButtons: ViewButtons = {
    viewButtons
  }

  def getGameScreen: GameScreen = {
    gameScreen
  }

  def getWeaponPanel: WeaponPanel = {
    weaponPanel
  }

  def togglePause(): Unit = {
    if (pausePanel.hasParent) {
      pausePanel.remove()
      gameScreen.setPause(false)
    } else {
      this.addActor(pausePanel)
      gameScreen.setPause(true)
    }
  }

  def refreshCrewAndStats(): Unit = {
    crewPanel.refreshCrewBoxes()
    shipStatsPanel.refreshStats()
  }

  private def showEventPanel(): Unit = {
    if (eventPanel != null) {
      eventPanel.remove()
      eventPanel = null
    }
    eventPanel = new EventPanel(this, SystemRepo.parseNextEvent)
    this.addActor(eventPanel)
    hideUI()
  }



  /**
    * Custom Scene2D Actions
    */
  private[components] var sectorViewAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      gameScreen.toggleSectorView()
      true
    }
  }

  private[components] var crewViewAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }

  private[components] var shipViewAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }

}
