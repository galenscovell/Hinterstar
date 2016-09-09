package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.*


class Player(actionStage: ActionStage) : Group() {
    private val gameScreen: GameScreen = actionStage.getGameScreen()
    private val ship: Ship = PlayerData.getShip()
    private val shipActor: Image = Image(Resources.atlas.createSprite(ship.getName()))

    init {
        ship.createInterior()

        this.setSize(420f, 168f)
        shipActor.setSize(420f, 168f)
        ship.getInterior().setSize(420f, 168f)

        this.addActor(shipActor)

        this.addAction(Actions.forever(
                Actions.sequence(
                        Actions.moveBy(0f, 6f, 5.0f),
                        Actions.moveBy(0f, -6f, 5.0f)
                )
        ))
    }



    fun getShip(): Ship {
        return ship
    }

    fun overlayPresent(): Boolean {
        return ship.getInterior().hasParent()
    }

    fun enableOverlay(): Unit {
        this.addActor(ship.getInterior())
    }

    fun disableOverlay(): Unit {
        ship.getInterior().remove()
    }
}
