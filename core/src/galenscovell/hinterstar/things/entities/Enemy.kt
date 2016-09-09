package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.things.ships.*
import galenscovell.hinterstar.ui.components.gamescreen.stages.ActionStage
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.Resources


class Enemy(actionStage: ActionStage) : Group() {
    // NPC ought to have hull health, weapons, evasion, and shields defined here
    // They fire weapons automatically just like the player, with crew assignments determining stats
    private val gameScreen: GameScreen = actionStage.getGameScreen()
    private val ship: Ship = ShipParser().parseSingle("Ship-1")
    private val sprite: Sprite = Resources.atlas.createSprite(ship.getName())
    private val shipActor: Image = Image(sprite)

    init {
        sprite.flip(true, false)
        ship.createInterior()

        this.setSize(420f, 168f)
        shipActor.setSize(420f, 168f)
        ship.getInterior().setSize(420f, 168f)

        this.addActor(shipActor)

        this.addAction(Actions.forever(
                Actions.sequence(
                        Actions.moveBy(0f, -6f, 4.8f),
                        Actions.moveBy(0f, 6f, 5.2f)
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
        return this.addActor(ship.getInterior())
    }

    fun disableOverlay(): Unit {
        ship.getInterior().remove()
    }
}
