package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.graphics._
import galenscovell.hinterstar.ui.components.gamescreen.stages._
import galenscovell.hinterstar.util._

import scala.util.Random


class GameScreen(root: Hinterstar) extends Screen {
  private val actionCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  private val interfaceCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  private var actionStage: ActionStage = _
  private var interfaceStage: InterfaceStage = _

  private val input: InputMultiplexer = new InputMultiplexer
  private var travelFrames: Int = 0
  private var travelPanelOpen: Boolean = false

  private val timestep: Float = (1 / 60.0f) * 30
  private var accumulator: Float = 0
  private var paused: Boolean = false

  val num0: Int = (Math.random * 8).toInt     // Value between 0-7
  val num1: Int = (Math.random * 4).toInt + 8 // Value between 8-12

  private var normalBg, blurBg: ParallaxBackground = _
  private var bg0: String = num0.toString
  private var bg1: String = num1.toString
  private var bg2: String = "stars0"
  private var bg3: String = "stars1"
  private var bg2Blur: String = "stars0_blur"
  private var bg3Blur: String = "stars1_blur"
  createBackground()
  private var currentBackground: ParallaxBackground = normalBg

  construct()



  private def construct(): Unit = {
    SystemOperations.setup(this)
    val actionViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, actionCamera)
    val interfaceViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, interfaceCamera)
    actionStage = new ActionStage(this, actionViewport, root.spriteBatch)
    interfaceStage = new InterfaceStage(this, interfaceViewport, root.spriteBatch)

    enableInput()
    actionStage.toggleInteriorOverlay()
  }

  private def enableInput(): Unit = {
    input.addProcessor(interfaceStage)
    input.addProcessor(actionStage)
    Gdx.input.setInputProcessor(input)
  }

  def getActionStage: ActionStage = {
    actionStage
  }

  def getInterfaceStage: InterfaceStage = {
    interfaceStage
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

  def transitionSector(bg0: String, bg1: String, bg2: String, bg3: String,
                       bg2Blur: String, bg3Blur: String): Unit = {
    this.bg0 = bg0
    this.bg1 = bg1
    this.bg2 = bg2
    this.bg3 = bg3
    this.bg2Blur = bg2Blur
    this.bg3Blur = bg3Blur

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

  private def travel(): Unit = {
    if (travelFrames > 400) {
      currentBackground.modifySpeed(new Vector2(500 - travelFrames, 0))
    } else if (travelFrames == 400) {
      currentBackground = blurBg
      currentBackground.setSpeed(new Vector2(2500, 0))
    } else if (travelFrames == 90) {
      currentBackground = normalBg
    } else if (travelFrames < 70) {
      currentBackground.modifySpeed(new Vector2(-(70 - travelFrames), 0))
    }
    travelFrames -= 1

    if (travelFrames == 0) {
      currentBackground.setSpeed(new Vector2(2, 0))
      actionStage.toggleInteriorOverlay()
    }
  }

  private def createBackground(): Unit = {
    val normalParallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](4)
    val blurParallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](4)

    val layer0: ParallaxLayer = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg0 + ".png"))),
      new Vector2(0.0f, 0.0f),
      new Vector2(0, 0),
      generateRandomColor
    )
    val layer1: ParallaxLayer = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg1 + ".png"))),
      new Vector2(0.0f, 0.0f),
      new Vector2(0, 0),
      generateRandomColor
    )

    normalParallaxLayers(0) = layer0
    normalParallaxLayers(1) = layer1
    normalParallaxLayers(2) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg2 + ".png"))),
      new Vector2(0.4f, 0.4f),
      new Vector2(0, 0),
      Color.WHITE
    )
    normalParallaxLayers(3) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg3 + ".png"))),
      new Vector2(0.8f, 0.8f),
      new Vector2(0, 0),
      Color.WHITE
    )

    blurParallaxLayers(0) = layer0
    blurParallaxLayers(1) = layer1
    blurParallaxLayers(2) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg2Blur + ".png"))),
      new Vector2(0.4f, 0.4f),
      new Vector2(0, 0),
      Color.WHITE
    )
    blurParallaxLayers(3) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bg3Blur + ".png"))),
      new Vector2(0.8f, 0.8f),
      new Vector2(0, 0),
      Color.WHITE
    )

    normalBg = new ParallaxBackground(
      root.spriteBatch,
      normalParallaxLayers,
      Constants.EXACT_X,
      Constants.EXACT_Y,
      new Vector2(2, 0)
    )

    blurBg = new ParallaxBackground(
      root.spriteBatch,
      blurParallaxLayers,
      Constants.EXACT_X,
      Constants.EXACT_Y,
      new Vector2(2, 0)
    )
  }

  private def generateRandomColor: Color = {
    val random: Random = new Random
    val r: Float = random.nextFloat / 2
    val g: Float = random.nextFloat
    val b: Float = random.nextFloat

    new Color(r, g, b, 1f)
  }



  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)

    // Handle travel and background animations
    if (travelFrames > 0) {
      travel()
    }

    if (currentBackground != null) {
      currentBackground.render(delta)
    }

    // Pause stops combat and crew movement for all players
    if (!paused) {
      if (accumulator > timestep) {
        accumulator -= timestep
        actionStage.combatUpdate()
        CrewOperations.updateCrewmatePositions()
      }
      accumulator += delta
    }

    // Update and render primary game stages
    actionStage.act(delta)
    actionStage.draw()
    actionStage.combatRender(delta)

    interfaceStage.act(delta)
    interfaceStage.draw()

    root.spriteBatch.begin()
    CrewOperations.drawCrewmatePositions(delta, root.spriteBatch)
    root.spriteBatch.end()


    // Draw map panel shapes
    if (travelPanelOpen) {
      SystemOperations.drawShapes()
    }
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
      createBackground()
      normalBg.setSpeed(new Vector2(2500, 0))
      blurBg.setSpeed(new Vector2(2500, 0))
      currentBackground = blurBg
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
