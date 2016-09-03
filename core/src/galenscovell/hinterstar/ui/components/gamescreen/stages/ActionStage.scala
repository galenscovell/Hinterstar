package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.processing.CombatProcessor
import galenscovell.hinterstar.things.entities.{Enemy, Player}
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class ActionStage(gameScreen: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val player: Player = new Player(this)
  private var npc: Enemy = new Enemy(this)
  private val combatProcessor: CombatProcessor = new CombatProcessor(this, player.getShip)

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val actionGroup: Group = new Group
    actionGroup.setSize(Constants.EXACT_X, Constants.EXACT_Y)
    actionGroup.setPosition(0, 0)

    actionGroup.addActor(player)
    player.setPosition(0, 100)

    actionGroup.addActor(npc)
    npc.setPosition(380, 240)

    mainTable.addActor(actionGroup)
    this.addActor(mainTable)

    combatProcessor.setEnemy(npc)
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
