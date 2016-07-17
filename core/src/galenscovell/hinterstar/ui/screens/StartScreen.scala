package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent, Stage}
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.ui.components.startscreen.{ShipSelectPanel, TeamSelectPanel}
import galenscovell.hinterstar.util.{Constants, PlayerData, ResourceManager}

import scala.collection.mutable.Map


class StartScreen(gameRoot: Hinterstar) extends AbstractScreen(gameRoot) {
  private val teamPanel: TeamSelectPanel = new TeamSelectPanel
  private val shipPanel: ShipSelectPanel = new ShipSelectPanel
  private val teamPanelButton: TextButton = new TextButton("Team", ResourceManager.toggleButtonStyle)
  private val shipPanelButton: TextButton = new TextButton("Ship", ResourceManager.toggleButtonStyle)
  private var currentPanelButton: TextButton = teamPanelButton
  private val contentPanel: Table = new Table


  protected override def create(): Unit = {
    shipPanel.updateShipDetails()
    shipPanel.updateShipDisplay(true)

    stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch)

    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val titleTable: Table = createTitleTable
    val contentTable: Table = new Table
    contentTable.add(contentPanel).width(780).height(400)
    updateContent(true)
    currentPanelButton.setChecked(true)

    mainTable.add(titleTable).width(770).height(50).pad(5)
    mainTable.row
    mainTable.add(contentTable).width(780).height(400)

    stage.addActor(mainTable)
    mainTable.addAction(Actions.sequence(
      Actions.fadeOut(0),
      Actions.fadeIn(0.3f))
    )
  }

  private def createTitleTable: Table = {
    val titleTable: Table = new Table

    val noticeTable: Table = new Table
    teamPanelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        if (currentPanelButton != teamPanelButton) {
          currentPanelButton.setChecked(false)
          currentPanelButton = teamPanelButton
          updateContent(false)
        }
        currentPanelButton.setChecked(true)
      }
    })
    shipPanelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        if (currentPanelButton != shipPanelButton) {
          currentPanelButton.setChecked(false)
          currentPanelButton = shipPanelButton
          updateContent(true)
        }
        currentPanelButton.setChecked(true)
      }
    })
    noticeTable.add(teamPanelButton).width(90).height(50).left
    noticeTable.add(shipPanelButton).width(90).height(50).right

    val returnButton: TextButton = new TextButton("Return", ResourceManager.greenButtonStyle)
    returnButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.3f),
          toMainMenuAction)
        )
      }
    })

    val embarkButton: TextButton = new TextButton("Embark", ResourceManager.greenButtonStyle)
    embarkButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        PlayerData.clear()
        val team: Array[String] = teamPanel.getTeammates
        val selectedShip: Ship = shipPanel.getShip
        establishTeam(team)
        establishShip(selectedShip)

        root.createGameScreen()
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.3f),
          toGameScreenAction)
        )
      }
    })

    titleTable.add(returnButton).width(200).height(50)
    titleTable.add(noticeTable).width(364).height(50)
    titleTable.add(embarkButton).width(200).height(50)

    titleTable
  }

  def updateContent(right: Boolean): Unit = {
    contentPanel.clearActions()

    if (right) {
      contentPanel.addAction(Actions.sequence(
        Actions.fadeOut(0.1f),
        contentTransitionRightAction,
        Actions.moveTo(100, 0),
        Actions.parallel(
          Actions.fadeIn(0.1f),
          Actions.moveBy(-100, 0, 0.15f)
        )
      ))
    } else {
      contentPanel.addAction(Actions.sequence(
        Actions.fadeOut(0.1f),
        contentTransitionLeftAction,
        Actions.moveTo(-100, 0),
        Actions.parallel(
          Actions.fadeIn(0.1f),
          Actions.moveBy(100, 0, 0.15f)
        )
      ))
    }
  }

  private def establishTeam(team: Array[String]): Unit = {
    PlayerData.establishTeam(team)
  }

  private def establishShip(selectedShip: Ship): Unit = {
    PlayerData.establishShip(selectedShip)
  }

  private def establishParts(): Unit = {
    // Each ship has specific starting parts, grab the parts for the chosen ship and save them
  }

  private def establishResources(resources: Map[String, Int]): Unit = {

  }



  /**
    * Custom Scene2D Actions
    */
  private[screens] var toMainMenuAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.mainMenuScreen)
      true
    }
  }

  private[screens] var toGameScreenAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.gameScreen)
      true
    }
  }

  private[screens] var contentTransitionRightAction: Action = new Action {
    def act(delta: Float): Boolean = {
      var newContent: Table = null
      if (teamPanel.hasParent) {
        teamPanel.remove()
        newContent = shipPanel
      } else if (shipPanel.hasParent) {
        shipPanel.remove()
        newContent = teamPanel
      } else {
        newContent = teamPanel
      }
      contentPanel.add(newContent).expand.fill.center
      true
    }
  }

  private[screens] var contentTransitionLeftAction: Action = new Action {
    def act(delta: Float): Boolean = {
      var newContent: Table = null
      if (teamPanel.hasParent) {
        teamPanel.remove()
        newContent = shipPanel
      } else if (shipPanel.hasParent) {
        shipPanel.remove()
        newContent = teamPanel
      } else {
        newContent = teamPanel
      }
      contentPanel.add(newContent).expand.fill.center
      true
    }
  }
}
