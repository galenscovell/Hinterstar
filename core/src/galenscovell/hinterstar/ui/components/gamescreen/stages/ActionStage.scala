package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.graphics.{ParallaxBackground, ParallaxLayer}
import galenscovell.hinterstar.processing.CombatProcessor
import galenscovell.hinterstar.things.entities.{Enemy, Player}
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._

import scala.util.Random


class ActionStage(gameScreen: GameScreen, viewport: FitViewport, private val spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val player: Player = new Player(this)
  private var npc: Enemy = new Enemy(this)
  private val combatProcessor: CombatProcessor = new CombatProcessor(this, player.getShip)

  private var normalBg, blurBg, currentBackground: ParallaxBackground = _
  private val bgStrings: Array[String] = Array.ofDim(6)

  construct()


  private def construct(): Unit = {
    // TODO: Make two separate cameras, one for player slice and one for enemy slice
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val actionGroup: Group = new Group
    actionGroup.setSize(Constants.EXACT_X, Constants.EXACT_Y)
    actionGroup.setPosition(0, 0)

    actionGroup.addActor(player)
    player.setPosition(24, 130)

    actionGroup.addActor(npc)
    npc.setPosition(380, 270)

    mainTable.addActor(actionGroup)
    this.addActor(mainTable)

    combatProcessor.setEnemy(npc)

    setupBackground(null)
    createBackground()
    currentBackground = normalBg
    toggleInteriorOverlay()
  }

  def updatePlayerAnimation(): Unit = {
    player.clearActions()
    npc.clearActions()

    npc.addAction(Actions.sequence(
      Actions.moveBy(-120, 0, 1.6f, Interpolation.exp5In),
      Actions.parallel(
        Actions.moveBy(-800, 0, 0.5f),
        Actions.fadeOut(0.5f)
      ),
      Actions.removeActor()
    ))

    player.addAction(Actions.sequence(
      Actions.moveBy(80, 0, 1.75f, Interpolation.exp5In),
      Actions.moveBy(0, 4, 1.25f, Interpolation.linear),
      Actions.moveBy(0, -8, 1.75f, Interpolation.linear),
      Actions.moveBy(0, 4, 1.25f, Interpolation.linear),
      Actions.moveBy(-80, 0, 2.0f, Interpolation.exp5In),
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 8, 4.0f),
          Actions.moveBy(0, -8, 4.0f)
        ))
    ))
  }

  def toggleInteriorOverlay(): Unit = {
    if (player.overlayPresent()) {
      player.disableOverlay()
    } else {
      player.enableOverlay()
    }

    if (npc != null) {
      if (npc.overlayPresent()) {
        npc.disableOverlay()
      } else {
        npc.enableOverlay()
      }
    }
  }

  def disableInteriorOverlay(): Unit = {
    if (player.overlayPresent()) {
      player.disableOverlay()
    }

    if (npc != null) {
      if (npc.overlayPresent()) {
        npc.disableOverlay()
      }
    }
  }

  def getGameScreen: GameScreen = {
    gameScreen
  }

  def getPlayer: Player = {
    player
  }


  /**************************
    *   Stage Operations    *
    **************************/
  def drawBackground(delta: Float): Unit = {
    if (currentBackground != null) {
      currentBackground.render(delta)
    }
  }

  def setupBackground(newBgStrings: Array[String]): Unit = {
    if (newBgStrings != null) {
      for (idx: Int <- newBgStrings.indices) {
        bgStrings(idx) = newBgStrings(idx)
      }
    } else {
      val num0: Int = (Math.random * 8).toInt     // Value between 0-7
      val num1: Int = (Math.random * 4).toInt + 8 // Value between 8-12

      bgStrings(0) = num0.toString
      bgStrings(1) = num1.toString
      bgStrings(2) = "stars0"
      bgStrings(3) = "stars1"
      bgStrings(4) = "stars0_blur"
      bgStrings(5) = "stars1_blur"
    }
  }

  def createBackground(): Unit = {
    val normalParallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](4)
    val blurParallaxLayers: Array[ParallaxLayer] = new Array[ParallaxLayer](4)

    val layer0: ParallaxLayer = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(0) + ".png"))),
      new Vector2(0.0f, 0.0f), new Vector2(0, 0), generateRandomColor
    )
    val layer1: ParallaxLayer = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(1) + ".png"))),
      new Vector2(0.0f, 0.0f), new Vector2(0, 0), generateRandomColor
    )

    normalParallaxLayers(0) = layer0
    normalParallaxLayers(1) = layer1
    normalParallaxLayers(2) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(2) + ".png"))),
      new Vector2(0.4f, 0.4f), new Vector2(0, 0), Color.WHITE
    )
    normalParallaxLayers(3) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(3) + ".png"))),
      new Vector2(0.8f, 0.8f), new Vector2(0, 0), Color.WHITE
    )

    blurParallaxLayers(0) = layer0
    blurParallaxLayers(1) = layer1
    blurParallaxLayers(2) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(4) + ".png"))),
      new Vector2(0.4f, 0.4f), new Vector2(0, 0), Color.WHITE
    )
    blurParallaxLayers(3) = new ParallaxLayer(
      new TextureRegion(new Texture(Gdx.files.internal("backgrounds/" + bgStrings(5) + ".png"))),
      new Vector2(0.8f, 0.8f), new Vector2(0, 0), Color.WHITE
    )

    normalBg = new ParallaxBackground(spriteBatch, normalParallaxLayers,
      Constants.EXACT_X, Constants.EXACT_Y, new Vector2(2, 0)
    )

    blurBg = new ParallaxBackground(spriteBatch, blurParallaxLayers,
      Constants.EXACT_X, Constants.EXACT_Y, new Vector2(2, 0)
    )
  }

  private def generateRandomColor: Color = {
    val random: Random = new Random
    val r: Float = random.nextFloat / 2
    val g: Float = random.nextFloat
    val b: Float = random.nextFloat

    new Color(r, g, b, 1f)
  }

  def warp(): Unit = {
    createBackground()
    normalBg.setSpeed(new Vector2(2500, 0))
    blurBg.setSpeed(new Vector2(2500, 0))
    currentBackground = blurBg
  }

  def travel(travelFrames: Int): Unit = {
    if (travelFrames == 0) {
      currentBackground.setSpeed(new Vector2(2, 0))
      toggleInteriorOverlay()
    } else {
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
    }
  }



  /*******************
    *     Combat     *
    *******************/
  def combatUpdate(): Unit = {
    combatProcessor.update(player.getShip.updateActiveWeapons(), npc.getShip.updateActiveWeapons())
  }

  def combatRender(delta: Float): Unit = {
    combatProcessor.render(delta, spriteBatch)
  }
}
