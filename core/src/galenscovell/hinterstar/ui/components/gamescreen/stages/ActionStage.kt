package galenscovell.hinterstar.ui.components.gamescreen.stages

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.processing.CombatProcessor
import galenscovell.hinterstar.things.entities.*
import galenscovell.hinterstar.ui.screens.GameScreen
import galenscovell.hinterstar.util.Constants


class ActionStage(private val gameScreen: GameScreen, viewport: FitViewport,
                  private val spriteBatch: SpriteBatch) : Stage(viewport, spriteBatch) {
    private val player: Player = Player(this)
    private val combatProcessor: CombatProcessor = CombatProcessor(this, player.getShip())
    private var npc: Enemy? = Enemy(this)

    init {
        // TODO: Make two separate cameras, one for player slice and one for enemy slice
        val mainTable: Table = Table()
        mainTable.setFillParent(true)

        val actionGroup: Group = Group()
        actionGroup.setSize(Constants.EXACT_X, Constants.EXACT_Y)
        actionGroup.setPosition(0f, 0f)

        actionGroup.addActor(player)
        player.setPosition(24f, 130f)

        actionGroup.addActor(npc)
        npc!!.setPosition(380f, 270f)

        mainTable.addActor(actionGroup)
        this.addActor(mainTable)

        combatProcessor.setEnemy(npc!!)
    }



    fun updatePlayerAnimation(): Unit {
        player.clearActions()
        npc!!.clearActions()

        npc!!.addAction(Actions.sequence(
                Actions.moveBy(-120f, 0f, 1.6f, Interpolation.exp5In),
                Actions.parallel(
                        Actions.moveBy(-800f, 0f, 0.5f),
                        Actions.fadeOut(0.5f)
                ),
                Actions.removeActor()
        ))

        player.addAction(Actions.sequence(
                Actions.moveBy(80f, 0f, 1.75f, Interpolation.exp5In),
                Actions.moveBy(0f, 4f, 1.25f, Interpolation.linear),
                Actions.moveBy(0f, -8f, 1.75f, Interpolation.linear),
                Actions.moveBy(0f, 4f, 1.25f, Interpolation.linear),
                Actions.moveBy(-80f, 0f, 2.0f, Interpolation.exp5In),
                Actions.forever(
                        Actions.sequence(
                                Actions.moveBy(0f, 8f, 4.0f),
                                Actions.moveBy(0f, -8f, 4.0f)
                        ))
        ))
    }

    fun toggleInteriorOverlay(): Unit {
        if (player.overlayPresent()) {
            player.disableOverlay()
        } else {
            player.enableOverlay()
        }

        if (npc != null) {
            if (npc!!.overlayPresent()) {
                npc!!.disableOverlay()
            } else {
                npc!!.enableOverlay()
            }
        }
    }

    fun disableInteriorOverlay(): Unit {
        if (player.overlayPresent()) {
            player.disableOverlay()
        }

        if (npc != null) {
            if (npc!!.overlayPresent()) {
                npc!!.disableOverlay()
            }
        }
    }

    fun getGameScreen(): GameScreen {
        return gameScreen
    }

    fun getPlayer(): Player {
        return player
    }



    /*******************
     *     Combat     *
     *******************/
    fun combatUpdate(): Unit {
        combatProcessor.update(player.getShip().updateActiveWeapons(), npc!!.getShip().updateActiveWeapons())
    }

    fun combatRender(delta: Float): Unit {
        combatProcessor.render(delta, spriteBatch)
    }
}
