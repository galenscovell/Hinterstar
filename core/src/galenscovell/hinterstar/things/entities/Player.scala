package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.things.ships.Interior
import galenscovell.hinterstar.ui.components.gamescreen.stages.EntityStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util._


class Player(entityStage: EntityStage) extends Group {
  private val gameScreen: GameScreen = entityStage.getGameScreen
  private val shipActor: Image = new Image(Resources.atlas.createSprite("ship"))
  private val interior: Interior = new Interior()

  construct()


  private def construct(): Unit = {
    this.setSize(Constants.INTERIOR_WIDTH, Constants.INTERIOR_HEIGHT)
    shipActor.setSize(Constants.INTERIOR_WIDTH, Constants.INTERIOR_HEIGHT)
    interior.setSize(Constants.INTERIOR_WIDTH, Constants.INTERIOR_HEIGHT)

    this.addActor(shipActor)

    this.addAction(Actions.sequence(
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 4, 4.0f),
          Actions.moveBy(0, -4, 4.0f)
        ))
    ))
  }

  def interiorPresent: Boolean = {
    interior.hasParent
  }

  def enableInterior(): Unit = {
    this.addActor(interior)
  }

  def disableInterior(): Unit = {
    interior.remove()
  }
}
