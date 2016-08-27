package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.processing.CombatHandler
import galenscovell.hinterstar.things.entities.{Npc, Player}
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class ActionStage(game: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val gameScreen: GameScreen = game
  private val player: Player = new Player(this)
  private var npc: Npc = new Npc(this)
  private val combatHandler: CombatHandler = new CombatHandler(this, player.getShip)

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val actionGroup: Group = new Group
    actionGroup.setSize(Constants.EXACT_X * 1.125f, Constants.EXACT_Y)
    actionGroup.setPosition(0, 0)

    actionGroup.addActor(player)
    player.setPosition(0, 96)

    actionGroup.addActor(npc)
    npc.setPosition(380, 240)

    mainTable.addActor(actionGroup)
    this.addActor(mainTable)

    combatHandler.setOpposition(npc)
  }

  def updatePlayerAnimation(): Unit = {
    player.clearActions()
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

  def toggleSubsystemOverlay(): Unit = {
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

  def disableSubsystemOverlay(): Unit = {
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
    combatHandler.update(player.getShip.updateActiveWeapons(), npc.getShip.updateActiveWeapons())
  }

  def combatRender(delta: Float): Unit = {
    combatHandler.render(delta, this.spriteBatch)
  }
}
