package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, Screen}
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.ui.components.startscreen.{CrewSelectPanel, ShipSelectPanel}
import galenscovell.hinterstar.util._


class StartScreen(gameRoot: Hinterstar) extends Screen {
  private val root: Hinterstar = gameRoot
  private var stage: Stage = _
  private val camera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)

  private val crewPanel: CrewSelectPanel = new CrewSelectPanel
  private val shipPanel: ShipSelectPanel = new ShipSelectPanel
  private val crewPanelButton: TextButton = new TextButton("Crew", Resources.toggleButtonStyle)
  private val shipPanelButton: TextButton = new TextButton("Ship", Resources.toggleButtonStyle)
  private var currentPanelButton: TextButton = crewPanelButton
  private val contentPanel: Table = new Table


  private def create(): Unit = {
    shipPanel.updateShipDetails()
    shipPanel.updateShipDisplay(true)

    val viewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, camera)
    stage = new Stage(viewport, root.spriteBatch)

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

  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)
    stage.act(delta)
    stage.draw()
  }

  override def resize(width: Int, height: Int): Unit = {
    if (stage != null) {
      stage.getViewport.update(width, height, true)
    }
  }

  override def show(): Unit = {
    create()
    Gdx.input.setInputProcessor(stage)
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {
    if (stage != null) {
      stage.dispose()
    }
  }

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}



  private def createTitleTable: Table = {
    val titleTable: Table = new Table
    val noticeTable: Table = new Table

    crewPanelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        if (currentPanelButton != crewPanelButton) {
          currentPanelButton.setChecked(false)
          currentPanelButton = crewPanelButton
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
    noticeTable.add(crewPanelButton).width(90).height(50).left
    noticeTable.add(shipPanelButton).width(90).height(50).right

    val returnButton: TextButton = new TextButton("Return", Resources.greenButtonStyle)
    returnButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.3f),
          toMainMenuAction)
        )
      }
    })

    val embarkButton: TextButton = new TextButton("Embark", Resources.greenButtonStyle)
    embarkButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        PlayerData.clear()
        PlayerData.saveCrew(crewPanel.getCrewmates)
        PlayerData.loadCrew()
        PlayerData.saveShip(shipPanel.getShip)
        PlayerData.loadShip()
        PlayerData.saveWeapons()
        PlayerData.loadWeapons()

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
      if (crewPanel.hasParent) {
        crewPanel.remove()
        newContent = shipPanel
      } else if (shipPanel.hasParent) {
        shipPanel.remove()
        newContent = crewPanel
      } else {
        newContent = crewPanel
      }
      contentPanel.add(newContent).expand.fill.center
      true
    }
  }
  private[screens] var contentTransitionLeftAction: Action = new Action {
    def act(delta: Float): Boolean = {
      var newContent: Table = null
      if (crewPanel.hasParent) {
        crewPanel.remove()
        newContent = shipPanel
      } else if (shipPanel.hasParent) {
        shipPanel.remove()
        newContent = crewPanel
      } else {
        newContent = crewPanel
      }
      contentPanel.add(newContent).expand.fill.center
      true
    }
  }
}
