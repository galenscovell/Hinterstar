package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.things.parts.Shield
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.ui.components.gamescreen.stages.EntityStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class Player(entityStage: EntityStage) extends Group {
  private val gameScreen: GameScreen = entityStage.getGameScreen
  private val ship: Ship = PlayerData.getShip
  private val shipActor: Image = new Image(Resources.atlas.createSprite(ship.getName))
  private val shields: Shield = new Shield(544 + 80, 218 + 80)

  construct()


  private def construct(): Unit = {
    ship.createInterior()

    this.setSize(544, 218)
    shipActor.setSize(544, 218)

    ship.getInterior.setSize(544, 218)

    this.addActor(shipActor)
    this.addActor(shields)

    this.addAction(Actions.forever(
      Actions.sequence(
        Actions.moveBy(0, 6, 5.0f),
        Actions.moveBy(0, -6, 5.0f)
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
