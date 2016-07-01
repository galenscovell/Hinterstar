package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.graphics._
import galenscovell.hinterstar.processing.controls.InputHandler
import galenscovell.hinterstar.ui.components.gamescreen.{GameStage, LocationPanel}
import galenscovell.hinterstar.util._


class GameScreen(gameRoot: Hinterstar) extends AbstractScreen(gameRoot) {
  private val input: InputMultiplexer = new InputMultiplexer
  private var travelFrames: Int = 0
  private var mapOpen: Boolean = false

  var normalBg: ParallaxBackground = createBackground("purple_bg", "bg1", "bg2")
  var blurBg: ParallaxBackground = createBackground("purple_bg", "bg1_blur", "bg2_blur")
  var currentBackground: ParallaxBackground = normalBg
  var locationPanel: LocationPanel = null

  var bg0: String = null
  var bg1: String = null
  var bg2: String = null
  var bg0Blur: String = null
  var bg1Blur: String = null
  var bg2Blur: String = null

  create()


  protected override def create(): Unit = {
    Repository.setup(this)
    this.stage = new GameStage(this, root.spriteBatch)
    input.addProcessor(stage)
    input.addProcessor(new InputHandler(this))
    Gdx.input.setInputProcessor(input)
  }

  override def render(delta: Float): Unit = {
    // Clear screen
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    // Handle travel and background animations
    if (travelFrames > 0) {
      travel()
    }
    if (currentBackground != null) {
      currentBackground.render(delta)
    }
    // Update and render game stage
    stage.act()
    if (stage.asInstanceOf[GameStage].getNextAnimationFrames > 0) {
      stage.asInstanceOf[GameStage].nextEventAnimation()
    }
    stage.draw()
    // Draw map panel shapes
    if (mapOpen) {
      Repository.drawShapes()
    }
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(input)
  }

  def getGameStage: Stage = {
    stage
  }

  def toMainMenu(): Unit = {
    root.setScreen(root.mainMenuScreen)
  }

  def beginTravel(): Unit = {
    travelFrames = 600
  }

  def toggleMap(): Unit = {
    mapOpen = !mapOpen
    if (mapOpen) {
      Repository.setTargetsInRange()
    }
  }

  def transitionSector(bg0: String, bg1: String, bg2: String, bg0Blur: String, bg1Blur: String, bg2Blur: String): Unit = {
    this.bg0 = bg0
    this.bg1 = bg1
    this.bg2 = bg2
    this.bg0Blur = bg0Blur
    this.bg1Blur = bg1Blur
    this.bg2Blur = bg2Blur
    val locationDetail: Array[String] = Repository.currentLocation.getDetails
    this.locationPanel = new LocationPanel(locationDetail(0), locationDetail(1))
    stage.asInstanceOf[GameStage].updateDetailTable(locationDetail(0))
    stage.getRoot.addAction(Actions.sequence(
      Actions.delay(3),
      Actions.fadeOut(1.0f),
      travelTransitionAction,
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
        ResourceManager.uiAtlas.findRegion(bg0),
        new Vector2(0.02f, 0.02f),
        new Vector2(0, 0)
      )
      parallaxLayers(1) = new ParallaxLayer(
        ResourceManager.uiAtlas.findRegion(bg1),
        new Vector2(0.5f, 0.5f),
        new Vector2(0, 0)
      )
      parallaxLayers(2) = new ParallaxLayer(
        ResourceManager.uiAtlas.findRegion(bg2),
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
        ResourceManager.uiAtlas.findRegion(bg1),
        new Vector2(0.5f, 0.5f),
        new Vector2(0, 0)
      )
      parallaxLayers(1) = new ParallaxLayer(
        ResourceManager.uiAtlas.findRegion(bg2),
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
    * Custom Scene2D Actions
    */
  private[screens] var travelTransitionAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      normalBg = createBackground(bg0, bg1, bg2)
      normalBg.setSpeed(new Vector2(2500, 0))
      blurBg = createBackground(bg0Blur, bg1Blur, bg2Blur)
      blurBg.setSpeed(new Vector2(2500, 0))
      currentBackground = blurBg
      stage.addActor(locationPanel)
      locationPanel.addAction(Actions.sequence(
        Actions.delay(3.5f),
        Actions.fadeOut(1.25f),
        showUIElements
      ))
      true
    }
  }

  private[screens] var showUIElements: Action = new Action() {
    def act(delta: Float): Boolean = {
      stage.asInstanceOf[GameStage].showNavButtons()
      true
    }
  }
}
