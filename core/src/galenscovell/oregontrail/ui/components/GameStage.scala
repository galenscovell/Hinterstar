package galenscovell.oregontrail.ui.components

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.oregontrail.things.entity.Player
import galenscovell.oregontrail.ui.screens.GameScreen
import galenscovell.oregontrail.util._


class GameStage(game: GameScreen, spriteBatch: SpriteBatch) extends Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), spriteBatch) {
  final val gameScreen: GameScreen = game
  private final val player: Player = new Player(this)
  private final val mapPanel: MapPanel = new MapPanel(this)
  private final val teamPanel: TeamPanel = new TeamPanel(this)
  private final val shipPanel: ShipPanel = new ShipPanel(this)
  private var navButtons: NavButtons = null
  private var actionTable: Table = null
  private var detailTable: DetailTable = null

  construct
  

  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)
    this.navButtons = new NavButtons(this)
    this.actionTable = new Table
    actionTable.add(player).expand.fill.left.padLeft(80)
    this.detailTable = new DetailTable(this)
    mainTable.add(navButtons).width(Constants.EXACT_X / 2).height(2 + Constants.SECTORSIZE * 3)
    mainTable.row
    mainTable.add(actionTable).width(Constants.EXACT_X).height(340)
    mainTable.row
    mainTable.add(detailTable).width(Constants.EXACT_X).height(110)
    this.addActor(mainTable)
  }

  def togglePanel(num: Int): Unit = {
    if (!mapPanel.hasActions) {
      if (mapPanel.hasParent) {
        mapPanel.addAction(Actions.sequence(mapAction, Actions.moveTo(800, 0, 0.3f, Interpolation.sine), Actions.removeActor))
      }
      else if (num == 0) {
        this.addActor(mapPanel)
        mapPanel.addAction(Actions.sequence(Actions.moveTo(-800, 0), Actions.moveTo(0, 0, 0.3f, Interpolation.sine), mapAction))
      }
    }
    if (!teamPanel.hasActions) {
      if (teamPanel.hasParent) {
        teamPanel.addAction(Actions.sequence(teamAction, Actions.moveTo(800, 0, 0.3f, Interpolation.sine), Actions.removeActor))
      }
      else if (num == 1) {
        this.addActor(teamPanel)
        teamPanel.addAction(Actions.sequence(Actions.moveTo(-800, 0), Actions.moveTo(0, 0, 0.3f, Interpolation.sine), teamAction))
      }
    }
    if (!shipPanel.hasActions) {
      if (shipPanel.hasParent) {
        shipPanel.addAction(Actions.sequence(shipAction, Actions.moveTo(800, 0, 0.3f, Interpolation.sine), Actions.removeActor))
      }
      else if (num == 2) {
        this.addActor(shipPanel)
        shipPanel.addAction(Actions.sequence(Actions.moveTo(-800, 0), Actions.moveTo(0, 0, 0.3f, Interpolation.sine), shipAction))
      }
    }
  }

  def toggleNavButtons(): Unit = {
    navButtons.addAction(Actions.sequence(Actions.touchable(Touchable.disabled), Actions.moveBy(0, 2 + Constants.SECTORSIZE * 2, 0.5f, Interpolation.sine), Actions.delay(7.5f), Actions.moveBy(0, -(2 + Constants.SECTORSIZE * 2), 0.5f, Interpolation.sine), Actions.touchable(Touchable.enabled)))
  }

  def updateDistanceLabel(d: String): Unit = {
    mapPanel.updateDistanceLabel(d)
  }

  def getNavButtons: NavButtons = {
    navButtons
  }


  private[components] var mapAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      gameScreen.toggleMap
      true
    }
  }
  private[components] var teamAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }
  private[components] var shipAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      true
    }
  }
}
