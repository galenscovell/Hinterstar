package galenscovell.oregontrail.ui.screens

import com.badlogic.gdx._
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import galenscovell.oregontrail.OregonTrailMain
import galenscovell.oregontrail.graphics._
import galenscovell.oregontrail.processing.controls.InputHandler
import galenscovell.oregontrail.ui.components._
import galenscovell.oregontrail.util._


class GameScreen(gameRoot: OregonTrailMain) extends AbstractScreen(gameRoot) {
  private var input: InputMultiplexer = null
  private var travelTicker: Int = 0
  private var mapOpen: Boolean = false
  var currentbackground: ParallaxBackground = null
  var normalBg: ParallaxBackground = null
  var blurBg: ParallaxBackground = null
  var bg0: String = null
  var bg1: String = null
  var bg2: String = null
  var bg0Blur: String = null
  var bg1Blur: String = null
  var bg2Blur: String = null
  var locationPanel: LocationPanel = null

  create


  protected override def create(): Unit = {
    Repository.setup(this)
    this.stage = new GameStage(this, root.spriteBatch)
    normalBg = createBackground("purple_bg", "bg1", "bg2")
    blurBg = createBackground("purple_bg", "bg1_blur", "bg2_blur")
    currentbackground = normalBg
    setupInput
  }

  override def render(delta: Float): Unit = {
    stage.act
    if (travelTicker > 0) {
      travel
    }
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    if (currentbackground != null) {
      currentbackground.render(delta)
    }
    stage.draw
    if (mapOpen) {
      Repository.drawShapes
    }
  }

  override def show(): Unit = {
    Gdx.input.setInputProcessor(input)
  }

  def getGameStage(): Stage = {
    stage
  }

  def toMainMenu(): Unit = {
    root.setScreen(root.mainMenuScreen)
  }

  def setTravel(): Unit = {
    travelTicker = 600
  }

  def toggleMap(): Unit = {
    mapOpen = !mapOpen
    if (mapOpen) {
      Repository.setTargetsInRange
    }
  }

  def travel(): Unit = {
    if (travelTicker > 500) {
      currentbackground.modifySpeed(new Vector2((600 - travelTicker), 0))
    }
    else if (travelTicker == 500) {
      currentbackground = blurBg
      currentbackground.setSpeed(new Vector2(2500, 0))
    }
    else if (travelTicker == 120) {
      currentbackground = normalBg
    }
    else if (travelTicker < 70) {
      currentbackground.modifySpeed(new Vector2(-(70 - travelTicker), 0))
    }
    travelTicker -= 1
    if (travelTicker == 0) {
      currentbackground.setSpeed(new Vector2(40, 0))
    }
  }

  private def setupInput(): Unit =  {
    this.input = new InputMultiplexer
    input.addProcessor(stage)
    input.addProcessor(new InputHandler(this))
    Gdx.input.setInputProcessor(input)
  }

  def setBackground(bg0: String, bg1: String, bg2: String, bg0Blur: String, bg1Blur: String, bg2Blur: String): Unit = {
    this.bg0 = bg0
    this.bg1 = bg1
    this.bg2 = bg2
    this.bg0Blur = bg0Blur
    this.bg1Blur = bg1Blur
    this.bg2Blur = bg2Blur
    val locationDetail: Array[String] = Repository.currentLocation.getDetails
    this.locationPanel = new LocationPanel(locationDetail(0), locationDetail(1))
    stage.getRoot.addAction(Actions.sequence(Actions.delay(3), Actions.fadeOut(1.0f), transition, Actions.fadeIn(1.0f), Actions.delay(3), Actions.removeActor(locationPanel)))
  }

  private def createBackground(bg0: String, bg1: String, bg2: String): ParallaxBackground = {
    var parallaxBackground: ParallaxBackground = null
    if (!(bg0 == "")) {
      val parallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](3)
      parallaxLayers(0) = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg0), new Vector2(0.02f, 0.02f), new Vector2(0, 0))
      parallaxLayers(1) = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0))
      parallaxLayers(2) = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0))
      parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0))
    }
    else {
      val parallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](2)
      parallaxLayers(0) = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg1), new Vector2(0.5f, 0.5f), new Vector2(0, 0))
      parallaxLayers(1) = new ParallaxLayer(ResourceManager.uiAtlas.findRegion(bg2), new Vector2(0.75f, 0.75f), new Vector2(0, 0))
      parallaxBackground = new ParallaxBackground(root.spriteBatch, parallaxLayers, Constants.EXACT_X, Constants.EXACT_Y, new Vector2(40, 0))
    }
    return parallaxBackground
  }


  private[screens] var transition: Action = new Action() {
    def act(delta: Float): Boolean = {
      normalBg = createBackground(bg0, bg1, bg2)
      normalBg.setSpeed(new Vector2(2500, 0))
      blurBg = createBackground(bg0Blur, bg1Blur, bg2Blur)
      blurBg.setSpeed(new Vector2(2500, 0))
      currentbackground = blurBg
      stage.addActor(locationPanel)
      true
    }
  }
}
