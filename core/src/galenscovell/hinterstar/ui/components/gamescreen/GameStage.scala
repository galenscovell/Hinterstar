package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.components.gamescreen.views._
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class GameStage(game: GameScreen, spriteBatch: SpriteBatch) extends Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch) {
  val gameScreen: GameScreen = game

  private val sectorView: SectorView = new SectorView(this)
  private val crewView: CrewView = new CrewView(this)
  private val shipView: ShipView = new ShipView(this)
  private val interiorView: InteriorView = new InteriorView(this)
  private val viewButtons: ViewButtons = new ViewButtons(this)

  private val player: Player = new Player(this)
  private val detailTable: DetailTable = new DetailTable(this)

  private var nextEventButton: TextButton = _
  private var eventPanel: EventPanel = _
  private var nextAnimationFrames: Int = 0

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    this.nextEventButton = new TextButton(">", Resources.buttonMapStyle0)
    nextEventButton.getLabelCell.width(60)
    nextEventButton.invalidate()
    nextEventButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        if (nextAnimationFrames == 0) {
          startNextEventAnimation()
        }
      }
    })

    val actionTable = new Table
    actionTable.add(player).expand.fill.left.padLeft(60)
    actionTable.add(nextEventButton).width(60).height(260).expand.fill.right

    mainTable.add(viewButtons).width(Constants.EXACT_X / 2).height(2 + Constants.SYSTEMMARKER_SIZE * 2)
    mainTable.row
    mainTable.add(actionTable).width(Constants.EXACT_X).height(340)
    mainTable.row
    mainTable.add(detailTable).width(Constants.EXACT_X).height(110)
    this.addActor(mainTable)


    this.addActor(interiorView)
  }

  def toggleView(num: Int): Unit = {
    if (!sectorView.hasActions) {
      if (sectorView.hasParent) {
        sectorView.addAction(Actions.sequence(
          sectorViewAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 0) {
        this.addActor(sectorView)
        sectorView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          sectorViewAction
        ))
      }
    }

    if (!crewView.hasActions) {
      if (crewView.hasParent) {
        crewView.addAction(Actions.sequence(
          crewViewAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 1) {
        this.addActor(crewView)
        crewView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          crewViewAction
        ))
      }
    }

    if (!shipView.hasActions) {
      if (shipView.hasParent) {
        shipView.addAction(Actions.sequence(
          shipViewAction,
          Actions.moveTo(800, 0, 0.3f, Interpolation.sine),
          Actions.removeActor()
        ))
      }
      else if (num == 2) {
        this.addActor(shipView)
        shipView.addAction(Actions.sequence(
          Actions.moveTo(-800, 0),
          Actions.moveTo(0, 0, 0.3f, Interpolation.sine),
          shipViewAction
        ))
      }
    }
  }

  def hideViewButtons(): Unit = {
    viewButtons.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(0, 2 + Constants.SYSTEMMARKER_SIZE * 2, 0.5f, Interpolation.sine)
    ))
    nextEventButton.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(100, 0, 0.5f, Interpolation.sine)
    ))
    detailTable.addAction(Actions.sequence(
      Actions.touchable(Touchable.disabled),
      Actions.moveBy(0, -110, 0.5f, Interpolation.sine)
    ))
  }

  def showViewButtons(): Unit = {
    viewButtons.addAction(Actions.sequence(
      Actions.moveBy(0, -(2 + Constants.SYSTEMMARKER_SIZE * 2), 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    nextEventButton.addAction(Actions.sequence(
      Actions.moveBy(-100, 0, 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
    detailTable.addAction(Actions.sequence(
      Actions.moveBy(0, 110, 0.5f, Interpolation.sine),
      Actions.touchable(Touchable.enabled)
    ))
  }

  def updateDistanceLabel(d: String): Unit = {
    sectorView.updateDistanceLabel(d)
  }

  def getViewButtons: ViewButtons = {
    viewButtons
  }

  def updateDetailTable(loc: String): Unit = {
    detailTable.updateLocation(loc)
    player.clearActions()
    player.addAction(Actions.sequence(
      Actions.moveBy(80, 0, 1.7f, Interpolation.exp5In),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(0, -8, 2.0f, Interpolation.linear),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(-80, 0, 3.0f, Interpolation.exp5In),
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 8, 4.0f),
          Actions.moveBy(0, -8, 4.0f)
        ))
    ))
  }

  def startNextEventAnimation(): Unit = {
    nextAnimationFrames = 300
    player.clearActions()
    player.addAction(Actions.sequence(
      Actions.moveBy(40, 0, 2.0f, Interpolation.exp5In),
      Actions.moveBy(0, 2, 1.0f, Interpolation.linear),
      Actions.moveBy(0, -2, 1.0f, Interpolation.linear),
      Actions.moveBy(-40, 0, 1.0f, Interpolation.exp5In),
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 8, 4.0f),
          Actions.moveBy(0, -8, 4.0f)
        ))
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
    eventPanel = new EventPanel(this, SystemRepo.parseNextEvent)
    this.addActor(eventPanel)
    hideViewButtons()
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
