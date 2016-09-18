package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.components.gamescreen.hud._
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class InterfaceStage(gameScreen: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch, player: Player) extends Stage(viewport, spriteBatch) {
  private val travelPanel: TravelPanel = new TravelPanel(this)
  private val travelButton: TravelButton = new TravelButton(this)

  private var eventPanel: EventPanel = _

  private val pauseInfo: InfoPanel = new InfoPanel("Paused")
  private val assignmentSelectInfo: InfoPanel = new InfoPanel("Select Subsystem to Assign Crewmate")

  construct()
  // DEBUG
  enableTravelButton()



  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    mainTable.add(new Table).width(Constants.EXACT_X).expand.fill

    this.addActor(mainTable)
  }



  def hideUI(): Unit = {

  }

  def showUI(): Unit = {

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



  /*************************
    *    Panel Handling    *
    *************************/
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

  def togglePause(): Unit = {
    // Pause only happens during events
    if (pauseInfo.hasParent) {
      pauseInfo.remove()
      gameScreen.setPause(false)
    } else {
      this.addActor(pauseInfo)
      gameScreen.setPause(true)
    }
  }

  def openAssignmentSelect(): Unit = {
    if (!assignmentSelectInfo.hasParent) {
      this.addActor(assignmentSelectInfo)
    }
  }

  def closeAssignmentSelect(): Unit = {
    if (assignmentSelectInfo.hasParent) {
      assignmentSelectInfo.remove()
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
