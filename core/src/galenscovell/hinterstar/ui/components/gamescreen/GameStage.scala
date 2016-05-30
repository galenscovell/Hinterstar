package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.processing.EventContainer
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class GameStage(game: GameScreen, spriteBatch: SpriteBatch) extends Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch) {
  final val gameScreen: GameScreen = game
  private final val player: Player = new Player(this)
  private var eventButton: TextButton = null
  private var eventPanel: EventPanel = null
  private final val mapPanel: MapPanel = new MapPanel(this)
  private final val teamPanel: TeamPanel = new TeamPanel(this)
  private final val shipPanel: ShipPanel = new ShipPanel(this)
  private var navButtons: NavButtons = null
  private var actionTable: Table = null
  private var detailTable: DetailTable = null
  private var nextAnimationFrames: Int = 0

  construct()
  

  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    this.eventButton = new TextButton("Next", ResourceManager.buttonMapStyle0)
    eventButton.getLabelCell.width(80)
    eventButton.invalidate()
    eventButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        if (nextAnimationFrames == 0) {
          startNextEventAnimation()
        }
      }
    })

    this.navButtons = new NavButtons(this)
    this.actionTable = new Table
    actionTable.add(player).expand.fill.left.padLeft(60)
    actionTable.add(eventButton).width(90).height(220).expand.fill.right
    this.detailTable = new DetailTable(this)

    mainTable.add(navButtons).width(Constants.EXACT_X / 2).height(2 + Constants.SECTORSIZE * 2)
    mainTable.row
    mainTable.add(actionTable).width(Constants.EXACT_X).height(340)
    mainTable.row
    mainTable.add(detailTable).width(Constants.EXACT_X).height(110)
    this.addActor(mainTable)
  }


  def togglePanel(num: Int): Unit = {
    if (!mapPanel.hasActions) {
      if (mapPanel.hasParent) {
        mapPanel.addAction(Actions.sequence(
          mapButtonAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 0) {
        this.addActor(mapPanel)
        mapPanel.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          mapButtonAction
        ))
      }
    }
    if (!teamPanel.hasActions) {
      if (teamPanel.hasParent) {
        teamPanel.addAction(Actions.sequence(
          teamButtonAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 1) {
        this.addActor(teamPanel)
        teamPanel.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          teamButtonAction
        ))
      }
    }
    if (!shipPanel.hasActions) {
      if (shipPanel.hasParent) {
        shipPanel.addAction(Actions.sequence(
          shipButtonAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 2) {
        this.addActor(shipPanel)
        shipPanel.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          shipButtonAction
        ))
      }
    }
  }


  def hideUIElements(): Unit = {
    navButtons.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(0, 2 + Constants.SECTORSIZE * 2, 0.5f, Interpolation.sine)
    ))
    eventButton.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(100, 0, 0.5f, Interpolation.sine)
    ))
    detailTable.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(0, -110, 0.5f, Interpolation.sine)
    ))
  }


  def showUIElements(): Unit = {
    navButtons.addAction(Actions.sequence(
      Actions.moveBy(0, -(2 + Constants.SECTORSIZE * 2), 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    eventButton.addAction(Actions.sequence(
      Actions.moveBy(-100, 0, 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    detailTable.addAction(Actions.sequence(
      Actions.moveBy(0, 110, 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
  }


  def updateDistanceLabel(d: String): Unit = {
    mapPanel.updateDistanceLabel(d)
  }


  def getNavButtons: NavButtons = {
    navButtons
  }


  def updateDetailTable(loc: String): Unit = {
    detailTable.updateLocation(loc)
    player.addAction(Actions.sequence(
      Actions.moveBy(80, 0, 1.7f, Interpolation.exp5In),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(0, -8, 2.0f, Interpolation.linear),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(-80, 0, 3.0f, Interpolation.exp5In)
    ))
  }


  def startNextEventAnimation(): Unit = {
    nextAnimationFrames = 300
    player.addAction(Actions.sequence(
      Actions.moveBy(40, 0, 2.0f, Interpolation.exp5In),
      Actions.moveBy(0, 2, 1.0f, Interpolation.linear),
      Actions.moveBy(0, -2, 1.0f, Interpolation.linear),
      Actions.moveBy(-40, 0, 1.0f, Interpolation.exp5In)
    ))
  }


  def getNextAnimationFrames: Int = {
    nextAnimationFrames
  }


  def nextEventAnimation(): Unit = {
    if (nextAnimationFrames > 200) {
      gameScreen.currentBackground.modifySpeed(new Vector2(300 - nextAnimationFrames, 0))
    } else if (nextAnimationFrames == 200) {
      gameScreen.currentBackground.setSpeed(new Vector2(2400, 0))
    } else if (nextAnimationFrames == 90) {
    } else if (nextAnimationFrames < 70) {
      gameScreen.currentBackground.modifySpeed(new Vector2(-(70 - nextAnimationFrames), 0))
    }
    nextAnimationFrames -= 1
    if (nextAnimationFrames == 0) {
      gameScreen.currentBackground.setSpeed(new Vector2(40, 0))
      showEventPanel()
    }
  }


  private def showEventPanel(): Unit = {
    if (eventPanel != null) {
      eventPanel.remove()
      eventPanel = null
    }
    val parsedEvent: EventContainer = Repository.parseNextEvent
    eventPanel = new EventPanel(this, parsedEvent)
    this.addActor(eventPanel)
    hideUIElements()
  }



  /**
    * Custom Scene2D Actions
    */
  private[components] var mapButtonAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      gameScreen.toggleMap()
      true
    }
  }
  private[components] var teamButtonAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }
  private[components] var shipButtonAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }
}
