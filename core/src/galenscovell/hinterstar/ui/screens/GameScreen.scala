package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.profiling.GLProfiler
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.processing.{CombatProcessor, GestureHandler}
import galenscovell.hinterstar.ui.components.gamescreen.stages._
import galenscovell.hinterstar.util._


class GameScreen(root: Hinterstar) extends Screen {
  private val actionCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight / 2)
  private val interfaceCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  private var actionStage: ActionStage = _
  private var interfaceStage: InterfaceStage = _
  private var combatProcessor: CombatProcessor = _

  private val input: InputMultiplexer = new InputMultiplexer
  private val gestureHandler: GestureDetector = new GestureDetector(new GestureHandler(this))
  private var travelFrames: Int = 0
  private var travelPanelOpen: Boolean = false

  private val timestep: Float = (1 / 60.0f) * 30
  private var accumulator: Float = 0
  private var paused: Boolean = false

  private val lerp: Float = 0.9f
  private val originVector: Vector2 = new Vector2(Constants.EXACT_X / 2, Constants.EXACT_Y / 2)
  private val cameraXmin: Float = Constants.EXACT_X * 0.375f
  private val cameraXmax: Float = Constants.EXACT_X * 0.625f

  construct()



  private def construct(): Unit = {
    SystemOperations.setup(this)
    val actionViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y / 2, actionCamera)
    val interfaceViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, interfaceCamera)
    actionStage = new ActionStage(this, actionViewport, root.spriteBatch)
    interfaceStage = new InterfaceStage(this, interfaceViewport, root.spriteBatch, actionStage.getPlayer)
    combatProcessor = new CombatProcessor(actionStage)
    combatProcessor.setEnemy(actionStage.getEnemy)

    enableInput()
    // GLProfiler.enable();
  }

  private def enableInput(): Unit = {
    input.addProcessor(interfaceStage)
    input.addProcessor(actionStage)
    input.addProcessor(gestureHandler)
    Gdx.input.setInputProcessor(input)
  }

  def getActionStage: ActionStage = {
    actionStage
  }

  def getInterfaceStage: InterfaceStage = {
    interfaceStage
  }

  def getRoot: Hinterstar = {
    root
  }

  def toMainMenu(): Unit = {
    root.setScreen(root.mainMenuScreen)
  }

  def beginTravel(): Unit = {
    if (!paused) {
      interfaceStage.togglePause()
    }
    getActionStage.disableInteriorOverlay()
    input.clear()
    travelFrames = 500
  }

  def openTravelPanel(): Unit = {
    travelPanelOpen = true
    SystemOperations.setTargetsInRange()
  }

  def closeTravelPanel(): Unit = {
    travelPanelOpen = false
  }

  def setPause(setting: Boolean): Unit = {
    paused = setting
  }

  def transitionSector(bgStrings: Array[String]): Unit = {
    actionStage.setupBackground(bgStrings)
    actionStage.updatePlayerAnimation()
    actionStage.getRoot.addAction(Actions.sequence(
      Actions.delay(3f),
      Actions.fadeOut(1.0f),
      Actions.fadeIn(1.0f)
    ))

    interfaceStage.getRoot.addAction(Actions.sequence(
      Actions.delay(3f),
      Actions.fadeOut(1.0f),
      warpTransitionAction,
      Actions.fadeIn(1.0f),
      Actions.delay(2.75f),
      showViewButtonsAction,
      Actions.delay(1f),
      enableInputAction
    ))
  }



  /***********************
    * Gesture operations *
    ***********************/
  def actionPan(dx: Float, dy: Float): Unit = {
    val newCameraX: Float = getActionStage.getCamera.position.x - dx
    val newSpeed: Vector2 = new Vector2(40, 0)

    if (newCameraX >= cameraXmin && newCameraX < cameraXmax) {
      getActionStage.getCamera.translate(-dx, 0, 0)
    }
  }



  /*******************
    *     Combat     *
    *******************/
  def combatUpdate(): Unit = {
    combatProcessor.update(
      actionStage.getPlayerShip.updateActiveWeapons(),
      actionStage.getEnemyShip.updateActiveWeapons())
  }

  def combatRender(delta: Float): Unit = {
    combatProcessor.render(delta, root.spriteBatch)
  }



  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)

    // Handle travel and background animations
    if (travelFrames > 0) {
      travelFrames -= 1
      actionStage.travel(travelFrames)
    }

    // Pause stops combat and crew movement for all players
    if (!paused) {
      if (accumulator > timestep) {
        accumulator -= timestep
        combatUpdate()
        CrewOperations.updateCrewmatePositions()
      }
      accumulator += delta
    }

    // Update and render primary game stages
    actionStage.act(delta)
    // Enemy Stage
    Gdx.gl.glViewport(0, 2 * Gdx.graphics.getHeight / 3, Gdx.graphics.getWidth, Gdx.graphics.getHeight / 3)
    actionStage.drawBackground(delta)
    actionStage.draw()

    // Player Stage
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, 2 * Gdx.graphics.getHeight / 3)
    actionStage.drawBackground(delta)
    actionStage.draw()

    // Interface Stage
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    combatRender(delta)
    interfaceStage.act(delta)
    interfaceStage.draw()

    // Draw map panel shapes or crewmate flags if not currently traveling
    if (travelPanelOpen) {
      SystemOperations.drawShapes()
    } else if (travelFrames == 0) {
      root.spriteBatch.begin()
      CrewOperations.drawCrewmatePositions(delta, root.spriteBatch)
      root.spriteBatch.end()
    }

//    println("Calls: " + GLProfiler.drawCalls + ", Bindings: " + GLProfiler.textureBindings)
//    println("Draw Calls: " + GLProfiler.drawCalls)
//    GLProfiler.reset()
  }

  override def show(): Unit = {
    enableInput()
  }

  override def resize(width: Int, height: Int): Unit = {
    if (actionStage != null) {
      actionStage.getViewport.update(width, height, true)
    }
    if (interfaceStage != null) {
      interfaceStage.getViewport.update(width, height, true)
    }
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {
    if (actionStage != null) {
      actionStage.dispose()
    }
    if (interfaceStage != null) {
      interfaceStage.dispose()
    }
  }

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}



  /***************************
    * Custom Scene2D Actions *
    ***************************/
  private[screens] var warpTransitionAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      actionStage.warp()
      true
    }
  }
  private[screens] var showViewButtonsAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      interfaceStage.showUI()
      true
    }
  }
  private[screens] var enableInputAction: Action = new Action {
    def act(delta: Float): Boolean = {
      enableInput()
      true
    }
  }
}
