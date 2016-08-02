package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.{Vector2, Vector3}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.graphics._
import galenscovell.hinterstar.processing.controls.GestureHandler
import galenscovell.hinterstar.ui.components.gamescreen.hud.LocationPanel
import galenscovell.hinterstar.ui.components.gamescreen.stages._
import galenscovell.hinterstar.util._


class GameScreen(gameRoot: Hinterstar) extends Screen {
  private val root: Hinterstar = gameRoot
  private val actionCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  private val hudCamera: OrthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
  private var actionStage: ActionStage = _
  private var hudStage: HudStage = _

  private val input: InputMultiplexer = new InputMultiplexer
  private val gestureHandler: GestureDetector = new GestureDetector(new GestureHandler(this))
  private var travelFrames: Int = 0
  private var sectorViewOpen: Boolean = false

  private var normalBg: ParallaxBackground = createBackground("purple_bg", "bg1", "bg2")
  private var blurBg: ParallaxBackground = createBackground("purple_bg", "bg1_blur", "bg2_blur")
  private var locationPanel: LocationPanel = _
  var currentBackground: ParallaxBackground = normalBg

  private var bg0, bg1, bg2: String = _
  private var bg0Blur, bg1Blur, bg2Blur: String = _
  private val lerp: Float = 0.9f
  private val originVector: Vector3 = new Vector3(Constants.EXACT_X / 2, Constants.EXACT_Y / 2, 0)

  create()


  private def create(): Unit = {
    SystemRepo.setup(this)
    val actionViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, actionCamera)
    val hudViewport: FitViewport = new FitViewport(Constants.EXACT_X, Constants.EXACT_Y, hudCamera)
    actionStage = new ActionStage(this, actionViewport, root.spriteBatch)
    hudStage = new HudStage(this, hudViewport, root.spriteBatch)

    enableInput()
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    Gdx.gl.glClearColor(0, 0, 0, 1)

    // Handle travel and background animations
    if (travelFrames > 0) {
      centerCamera(delta)
      travel()
    }
    if (currentBackground != null) {
      currentBackground.render(delta)
    }

    // Update and render game stage
    actionStage.act(delta)
    actionStage.draw()
    hudStage.act(delta)
    hudStage.draw()

    // Draw map panel shapes
    if (sectorViewOpen) {
      SystemRepo.drawShapes()
    }
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(input)
  }

  override def resize(width: Int, height: Int): Unit = {
    if (actionStage != null) {
      actionStage.getViewport.update(width, height, true)
    }
    if (hudStage != null) {
      hudStage.getViewport.update(width, height, true)
    }
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def pause(): Unit =  {}

  override def resume(): Unit =  {}

  override def dispose(): Unit = {
    if (actionStage != null) {
      actionStage.dispose()
    }
    if (hudStage != null) {
      hudStage.dispose()
    }
  }


  private def enableInput(): Unit = {
    input.addProcessor(hudStage)
    input.addProcessor(gestureHandler)
    input.addProcessor(actionStage)
    // input.addProcessor(new InputHandler(this))
    Gdx.input.setInputProcessor(input)
  }

  def getActionStage: ActionStage = {
    actionStage
  }

  def getHudStage: HudStage = {
    hudStage
  }

  def toMainMenu(): Unit = {
    root.setScreen(root.mainMenuScreen)
  }

  def beginWarp(): Unit = {
    input.clear()
    travelFrames = 600
  }

  def toggleSectorView(): Unit = {
    sectorViewOpen = !sectorViewOpen
    if (sectorViewOpen) {
      SystemRepo.setTargetsInRange()
    }
  }

  def transitionSector(bg0: String, bg1: String, bg2: String, bg0Blur: String, bg1Blur: String, bg2Blur: String): Unit = {
    this.bg0 = bg0
    this.bg1 = bg1
    this.bg2 = bg2
    this.bg0Blur = bg0Blur
    this.bg1Blur = bg1Blur
    this.bg2Blur = bg2Blur

    val systemDetail: Array[String] = SystemRepo.currentSystem.getDetails
    locationPanel = new LocationPanel(systemDetail(0), systemDetail(1))
    actionStage.updatePlayerAnimation()

    hudStage.getRoot.addAction(Actions.sequence(
      Actions.delay(3),
      Actions.fadeOut(1.0f),
      warpTransitionAction,
      Actions.fadeIn(1.0f),
      Actions.delay(5.2f),
      Actions.removeActor(locationPanel)
    ))
  }

  private def travel(): Unit = {
    if (travelFrames > 500) {
      currentBackground.modifySpeed(new Vector2(600 - travelFrames, 0))
    } else if (travelFrames == 500) {
      currentBackground = blurBg
      currentBackground.setSpeed(new Vector2(2500, 0))
    } else if (travelFrames == 90) {
      currentBackground = normalBg
    } else if (travelFrames < 70) {
      currentBackground.modifySpeed(new Vector2(-(70 - travelFrames), 0))
    }
    travelFrames -= 1

    if (travelFrames == 0) {
      currentBackground.setSpeed(new Vector2(40, 0))
    }
  }

  private def createBackground(bg0: String, bg1: String, bg2: String): ParallaxBackground = {
    if (!(bg0 == "")) {
      val parallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](3)
      parallaxLayers(0) = new ParallaxLayer(
        Resources.uiAtlas.findRegion(bg0),
        new Vector2(0.02f, 0.02f),
        new Vector2(0, 0)
      )
      parallaxLayers(1) = new ParallaxLayer(
        Resources.uiAtlas.findRegion(bg1),
        new Vector2(0.5f, 0.5f),
        new Vector2(0, 0)
      )
      parallaxLayers(2) = new ParallaxLayer(
        Resources.uiAtlas.findRegion(bg2),
        new Vector2(0.75f, 0.75f),
        new Vector2(0, 0)
      )
      new ParallaxBackground(
        root.spriteBatch,
        parallaxLayers,
        Constants.EXACT_X,
        Constants.EXACT_Y,
        new Vector2(40, 0)
      )
    } else {
      val parallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](2)
      parallaxLayers(0) = new ParallaxLayer(
        Resources.uiAtlas.findRegion(bg1),
        new Vector2(0.5f, 0.5f),
        new Vector2(0, 0)
      )
      parallaxLayers(1) = new ParallaxLayer(
        Resources.uiAtlas.findRegion(bg2),
        new Vector2(0.75f, 0.75f),
        new Vector2(0, 0)
      )
      new ParallaxBackground(
        root.spriteBatch,
        parallaxLayers,
        Constants.EXACT_X,
        Constants.EXACT_Y,
        new Vector2(40, 0)
      )
    }
  }


  /**
    * Screen gesture operations
    */
  def actionPan(dx: Float, dy: Float): Unit = {
    getActionStage.getCamera.translate(-dx, -dy, 0)
  }

  def actionZoom(zoom: Float): Unit = {
    val initialZoom: Float = getActionStage.getCamera.asInstanceOf[OrthographicCamera].zoom

    if (!(initialZoom + zoom > 1.25) && !(initialZoom + zoom < 0.5)) {
      getActionStage.getCamera.asInstanceOf[OrthographicCamera].zoom += zoom
    }
  }

  def centerCamera(delta: Float): Unit = {
    getActionStage.getCamera.asInstanceOf[OrthographicCamera].zoom +=
      (1 - getActionStage.getCamera.asInstanceOf[OrthographicCamera].zoom) * lerp * delta

    getActionStage.getCamera.position.x +=
      (originVector.x - getActionStage.getCamera.position.x) * lerp * delta
    getActionStage.getCamera.position.y +=
      (originVector.y - getActionStage.getCamera.position.y) * lerp * delta
  }



  /**
    * Custom Scene2D Actions
    */
  private[screens] var warpTransitionAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      normalBg = createBackground(bg0, bg1, bg2)
      normalBg.setSpeed(new Vector2(2500, 0))
      blurBg = createBackground(bg0Blur, bg1Blur, bg2Blur)
      blurBg.setSpeed(new Vector2(2500, 0))
      currentBackground = blurBg
      hudStage.addActor(locationPanel)
      locationPanel.addAction(Actions.sequence(
        Actions.delay(3.5f),
        Actions.fadeOut(1.25f),
        showViewButtonsAction
      ))
      true
    }
  }

  private[screens] var showViewButtonsAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      hudStage.showViewButtons()
      enableInput()
      true
    }
  }
}
