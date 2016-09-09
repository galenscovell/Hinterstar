package galenscovell.hinterstar.processing

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.things.entities.Enemy
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import java.util.*


/**
 * Handles combat related processes between player ship and current opposition (generally NPC ship).
 *
 * Every game update an array of ready to fire weapons (for both player and opposition) is sent to this.
 * Each ready weapon uses its internal stats to determine damage, hit chance, and effects.
 * If there is no current opposition then the ready player weapons stay at full and do not fire.
 * Every weapon has a unique animation, with the source being the ship housing the weapon, and the target
 *     being the opposition ship.
 *
 * TODO:
 * The exact coordinate source for each weapon is the subsystem that it's housed within.
 * The exact coordinate target is any subsystem on the opposition ship.
 */
class CombatProcessor(private val actionStage: Stage, private val playerShip: Ship) {
    private val random: Random = Random()
    private var enemy: Enemy? = null
    private val weaponFx: MutableList<WeaponFx> = mutableListOf()
    private val finishedFx: MutableList<WeaponFx> = mutableListOf()



    fun setEnemy(en: Enemy): Unit {
        enemy = en
    }



    /*********************
     *     Updating     *
     *********************/
    fun update(playerReadyWeapons: List<Weapon>, enemyReadyWeapons: List<Weapon>): Unit {
        // Check ready to fire weapons for player ship
        if (playerReadyWeapons.isNotEmpty()) {
            for (weapon: Weapon in playerReadyWeapons) {
                if (!weaponFx.contains(weapon.getFx())) {
                    val weaponSubsystem: String = weapon.getSubsystem()
                    val weaponTile: Tile = playerShip.getSubsystemMap()[weaponSubsystem]!!
                    weaponFx.add(weapon.getFx())

                    val srcCoords: Vector2 = weaponTile.getActorCoordinates()
                    val targetCoords: Vector2 = weapon.getTarget().getActorCoordinates()

                    weapon.getFx().fire(srcCoords, targetCoords)
                    weapon.resetFireBar()
                }
            }
        }

        // Repeat above for enemy
        if (enemyReadyWeapons.isNotEmpty()) {

        }
    }



    /**********************
     *     Rendering     *
     **********************/
    fun render(delta: Float, spriteBatch: Batch): Unit {
        spriteBatch.begin()

        for (fx in weaponFx) {
            fx.draw(delta, spriteBatch)
            if (fx.done()) {
                finishedFx.add(fx)
            }
        }

        spriteBatch.end()

        if (finishedFx.isNotEmpty()) {
            for (fx: WeaponFx in finishedFx) {
                weaponFx.remove(fx)
            }

            finishedFx.clear()
        }
    }
}
