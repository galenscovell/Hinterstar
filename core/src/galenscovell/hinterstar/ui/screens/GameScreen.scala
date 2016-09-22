package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.profiling.GLProfiler
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math._
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.{Gdx, _}
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.graphics.{ParallaxBackground, ParallaxLayer}
import galenscovell.hinterstar.processing.GestureHandler
import galenscovell.hinterstar.ui.components.gamescreen.stages._
import galenscovell.hinterstar.util._

import scala.util.Random


class GameScreen(root: Hinterstar) extends Screen {
  private var entityStage: EntityStage = _
  private var interfaceStage: InterfaceStage = _

  private val input: InputMultiplexer = new InputMultiplexer
  private val gestureHandler: GestureDetector = new GestureDetector(new GestureHandler(this))
  private var travelFrames: Int = 0
  private var travelPanelOpen: Boolean = false

  private val timestep: Float = (1 / 60.0f) * 30
  private var accumulator: Float = 0
  private var paused: Boolean = false
  private var time: Float = 0f

  private val lerp: Float = 0.9f
  private val originVector: Vector2 = new Vector2(Constants.EXACT_X / 2, Constants.EXACT_Y / 2)
  private val cameraXmin: Float = Constants.EXACT_X * 0.4f
  private val cameraXmax: Float = Constants.EXACT_X * 0.6f

  private var shader: ShaderProgram = _
  private var parallaxBackground: ParallaxBackground = _
  private val backgroundBatch: SpriteBatch = new SpriteBatch()
  private val uiBatch: SpriteBatch = new SpriteBatch()
  private val bgStrings: Array[String] = Array.ofDim(6)

  construct()



  /********************
    *       Init      *
    ********************/
  private def construct(): Unit = {
    SystemOperations.setup(this)
    val playerCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    val playerViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, playerCamera)
    entityStage = new EntityStage(this, playerViewport, playerCamera, new SpriteBatch())

    val interfaceCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    val interfaceViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, interfaceCamera)
    interfaceStage = new InterfaceStage(this, interfaceViewport, root.spriteBatch, entityStage.getPlayer)

    enableInput()
    // GLProfiler.enable()

    setupShader()
    setupBackground(null)
    createBackground()

    CrewOperations.initialize(this)
  }

  private def setupShader(): Unit = {
    ShaderProgram.pedantic = false

    shader = new ShaderProgram(
      Gdx.files.internal("shaders/water_ripple.vert").readString(),
      Gdx.files.internal("shaders/water_ripple.frag").readString()
    )

    if (!shader.isCompiled) {
      println(shader.getLog)
    }

    entityStage.getBatch.setShader(shader)
    backgroundBatch.setShader(shader)
  }

  private def enableInput(): Unit = {
    input.addProcessor(interfaceStage)
    input.addProcessor(entityStage)
    input.addProcessor(gestureHandler)
    Gdx.input.setInputProcessor(input)
  }

  def getEntityStage: EntityStage = {
    entityStage
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
    getEntityStage.disableInterior()
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
    setupBackground(bgStrings)

    entityStage.updatePlayerAnimation()
    entityStage.getRoot.addAction(Actions.sequence(
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



  /***************************
    *   ParallaxBackground   *
    ***************************/
  def setupBackground(newBgStrings: Array[String]): Unit = {
    if (newBgStrings != null) {
      for (idx: Int <- newBgStrings.indices) {
        bgStrings(idx) = newBgStrings(idx)
      }
    } else {
      val num0: Int = (Math.random * 8).toInt     // Value between 0-7
      val num1: Int = (Math.random * 4).toInt + 8 // Value between 8-12

      bgStrings(0) = "water"
      bgStrings(1) = num0.toString
      bgStrings(2) = "stars0"
      bgStrings(3) = "stars1"
      bgStrings(4) = "stars0_blur"
      bgStrings(5) = "stars1_blur"
    }
  }

  def createBackground(): Unit = {
    val parallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](4)

    parallaxLayers(0) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(0) + ".png"))),
      new Vector2(0.0f, 0.0f), new Vector2(0, 0), Color.WHITE
    )
    parallaxLayers(1) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(1) + ".png"))),
      new Vector2(0.0f, 0.0f), new Vector2(0, 0), generateRandomColor
    )
    parallaxLayers(2) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(2) + ".png"))),
      new Vector2(0.2f, 0.2f), new Vector2(0, 0), Color.WHITE
    )
    parallaxLayers(3) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(3) + ".png"))),
      new Vector2(0.8f, 0.8f), new Vector2(0, 0), Color.WHITE
    )

    parallaxBackground = new ParallaxBackground(parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(0, -8))
  }

  private def generateRandomColor: Color = {
    val random: Random = new Random
    val r: Float = random.nextFloat / 4
    val g: Float = random.nextFloat / 2
    val b: Float = random.nextFloat * 1.5f

    new Color(r, g, b, 1f)
  }



  /***********************
    * Gesture operations *
    ***********************/
  def actionPan(dx: Float, dy: Float): Unit = {
    val entityCameraX: Float = getEntityStage.getCamera.position.x - dx

    if (entityCameraX >= cameraXmin && entityCameraX < cameraXmax) {
      getEntityStage.getCamera.translate(-dx, 0, 0)
      parallaxBackground.getCamera.translate(-dx / 8, 0, 0)
    }
  }

  def centerCamera(delta: Float): Unit = {
    // Center and undo zoom for Entity Camera
    getEntityStage.getCamera.asInstanceOf[OrthographicCamera].zoom +=
      (1 - getEntityStage.getCamera.asInstanceOf[OrthographicCamera].zoom) * lerp * delta
    getEntityStage.getCamera.position.x += (originVector.x - getEntityStage.getCamera.position.x) * lerp * delta

    // Center and undo zoom for ParallaxBackground Camera
    parallaxBackground.getCamera.asInstanceOf[OrthographicCamera].zoom +=
      (1 - parallaxBackground.getCamera.asInstanceOf[OrthographicCamera].zoom) * lerp * delta
    parallaxBackground.getCamera.position.x += (originVector.x - parallaxBackground.getCamera.position.x) * lerp * delta
  }



  /*******************
    *     Combat     *
    *******************/
  def combatUpdate(): Unit = {

  }

  def combatRender(delta: Float): Unit = {

  }



  /**********************
    * Screen Operations *
    **********************/
  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)

    // Shader
    time += delta
    if (time > 1200) {
      time = 0f
    }
    shader.begin()
    shader.setUniformf("u_time", time)
    shader.end()

    // Handle travel
    if (travelFrames > 0) {
      travelFrames -= 1
      centerCamera(delta)
      entityStage.travel(travelFrames)
    }

    // Pause stops combat, crew movement and background animation
    if (!paused && !travelPanelOpen) {
      parallaxBackground.render(delta, backgroundBatch)

      if (accumulator > timestep) {
        accumulator -= timestep
        CrewOperations.update()
      }
      accumulator += delta
    }
//
    // Update and draw Entities
    entityStage.act(delta)
    entityStage.draw()

    uiBatch.setProjectionMatrix(entityStage.getCamera.combined)
    uiBatch.begin()
    CrewOperations.drawCrewmateStats(delta, uiBatch)
    uiBatch.end()

    // Update and draw Interface
    interfaceStage.act(delta)
    interfaceStage.draw()

    // Draw map panel shapes
    if (travelPanelOpen) {
      SystemOperations.drawShapes()
    }

//    println("Calls: " + GLProfiler.drawCalls + ", Bindings: " + GLProfiler.textureBindings)
//    println("Draw Calls: " + GLProfiler.drawCalls)
//    GLProfiler.reset()
  }

  override def show(): Unit = {
    enableInput()
  }

  override def resize(width: Int, height: Int): Unit = {
    if (entityStage != null) {
      entityStage.getViewport.update(width, height, true)
    }
    if (interfaceStage != null) {
      interfaceStage.getViewport.update(width, height, true)
    }
    shader.begin()
    shader.setUniformf("u_resolution", width, height)
    shader.end()
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {
    if (entityStage != null) {
      entityStage.dispose()
    }
    if (interfaceStage != null) {
      interfaceStage.dispose()
    }
    shader.dispose()
  }

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}



  /***************************
    * Custom Scene2D Actions *
    ***************************/
  private[screens] var warpTransitionAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      entityStage.warp()
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
