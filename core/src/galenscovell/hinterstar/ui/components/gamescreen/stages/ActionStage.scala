package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class ActionStage(game: GameScreen, viewport: FitViewport, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val gameScreen: GameScreen = game
  private val player: Player = new Player(this)

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    // Make one table on the left for holding the player
    // Make one table on the right for holding other ships/bases/etc.
    val actionTable: Table = new Table
    actionTable.add(player).expand.fill.left.padLeft(30)

    mainTable.add(actionTable).width(Constants.EXACT_X).expand.fill

    this.addActor(mainTable)
  }

  def updatePlayerAnimation(): Unit = {
    player.clearActions()
    player.addAction(Actions.sequence(
      Actions.moveBy(80, 0, 1.7f, Interpolation.exp5In),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(0, -8, 2.0f, Interpolation.linear),
      Actions.moveBy(0, 4, 1.5f, Interpolation.linear),
      Actions.moveBy(-80, 0, 3.0f, Interpolation.exp5In),
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 8, 4.0f),
          Actions.moveBy(0, -8, 4.0f)
        ))
    ))
  }

  def toggleSubsystemOverlay(): Unit = {
    if (player.overlayPresent) {
      player.disableOverlay()
    } else {
      player.enableOverlay()
    }
  }

  def disableSubsystemOverlay(): Unit = {
    if (player.overlayPresent) {
      player.disableOverlay()
    }
  }

  def getGameScreen: GameScreen = {
    gameScreen
  }
}
