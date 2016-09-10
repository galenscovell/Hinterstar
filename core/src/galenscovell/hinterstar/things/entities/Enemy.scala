package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.things.ships.{Ship, ShipParser}
import galenscovell.hinterstar.ui.components.gamescreen.stages.EntityStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class Enemy(entityStage: EntityStage) extends Group {
  // NPC ought to have hull health, weapons, evasion, and shields defined here
  // They fire weapons automatically just like the player, with crew assignments determining stats
  private val gameScreen: GameScreen = entityStage.getGameScreen
  private val ship: Ship = new ShipParser().parseSingle("Ship-1")
  private val sprite: Sprite = Resources.atlas.createSprite(ship.getName)
  sprite.flip(true, false)
  private val shipActor: Image = new Image(sprite)

  construct()


  private def construct(): Unit = {
    ship.createInterior()

    this.setSize(480, 192)
    shipActor.setSize(480, 192)
    ship.getInterior.setSize(480, 192)

    this.addActor(shipActor)

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, -6, 4.8f),
        Actions.moveBy(0, 6, 5.2f)
      )
    ))
  }

  def getShip: Ship = {
    ship
  }

  def overlayPresent(): Boolean = {
    ship.getInterior.hasParent
  }

  def enableOverlay(): Unit = {
     this.addActor(ship.getInterior)
  }

  def disableOverlay(): Unit = {
    ship.getInterior.remove()
  }
}
