package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.things.entities.Player
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.Constants


class EntityStage(gameScreen: GameScreen, viewport: FitViewport,
                  camera: Camera, spriteBatch: SpriteBatch) extends Stage(viewport, spriteBatch) {
  private val player: Player = new Player(this)
  private val primaryTable: Table = new Table

  construct()


  private def construct(): Unit = {
    primaryTable.setFillParent(true)

    val actionTable: Table = new Table
    val leftTable: Table = new Table
    val centerTable: Table = new Table
    val rightTable: Table = new Table

    centerTable.add(player)

    actionTable.add(leftTable).expand.fill.left
    actionTable.add(centerTable).expand.fill.center
    actionTable.add(rightTable).expand.fill.right

    primaryTable.add(actionTable).width(Constants.EXACT_X * 1.75f).expand.fill
    this.addActor(primaryTable)

    toggleInterior()
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

  def toggleInterior(): Unit = {
    if (player.interiorPresent) {
      player.disableInterior()
    } else {
      player.enableInterior()
    }
  }

  def disableInterior(): Unit = {
    if (player.interiorPresent) {
      player.disableInterior()
    }
  }

  def getGameScreen: GameScreen = {
    gameScreen
  }

  def getPlayer: Player = {
    player
  }


  /***************************
    *    Stage Operations    *
    ***************************/
  def warp(): Unit = {

  }

  def travel(travelFrames: Int): Unit = {
    if (travelFrames == 0) {
      toggleInterior()
    } else {
      if (travelFrames > 400) {

      } else if (travelFrames == 400) {

      } else if (travelFrames == 90) {

      } else if (travelFrames < 70) {

      }
    }
  }
}
