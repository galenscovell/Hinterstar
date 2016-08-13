package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.generation.interior.InteriorOverlay
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class NpcShip(actionStage: ActionStage) extends Group {
  // NPC Ships ought to have hull health, weapons, evasion, and shields defined here
  // They fire weapons automatically just like the player, with crew assignments determining stats
  private val gameScreen: GameScreen = actionStage.getGameScreen

  private val sprite: Sprite =  Resources.shipAtlas.createSprite(PlayerData.getShip.getName)
  sprite.flip(true, false)
  private val shipActor: Image = new Image(sprite)
  private val overlayActor: Actor = new InteriorOverlay(PlayerData.getShip.getName)

  construct()


  private def construct(): Unit = {
    this.setSize(480, 192)
    shipActor.setSize(480, 192)
    overlayActor.setSize(480, 192)

    this.addActor(shipActor)

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, -6, 4.8f),
        Actions.moveBy(0, 6, 5.2f)
      )
    ))
  }

  def overlayPresent(): Boolean = {
    overlayActor.hasParent
  }

  def enableOverlay(): Unit = {
    this.addActor(overlayActor)
  }

  def disableOverlay(): Unit = {
    overlayActor.remove()
  }
}
