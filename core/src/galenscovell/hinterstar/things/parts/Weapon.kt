package galenscovell.hinterstar.things.parts

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.util.Resources


class Weapon(private val name: String, private val subsystem: String, private val description: String,
             private val damage: Int, private val firerate: Float, private val accuracy: Int,
             private val shots: Int, private val effect: String, fxType: String,
             animationType: String, speed: Float) {
    private val fx: WeaponFx = WeaponFx(fxType, animationType, speed)
    private var active: Boolean = false

    private var target: Tile? = null
    private val fireBar: ProgressBar = createBar(firerate)



    private fun createBar(firerate: Float): ProgressBar {
        val bar: ProgressBar = ProgressBar(0f, firerate, 1f, false, Resources.weaponBarStyle)
        bar.value = 0f
        bar.setAnimateDuration(0.5f)
        return bar
    }



    /********************
     *     Getters     *
     ********************/
    fun getName(): String {
        return name
    }

    fun getSubsystem(): String {
        return subsystem
    }

    fun getDescription(): String {
        return description
    }

    fun getDamage(): Int {
        return damage
    }

    fun getFirerate(): Float {
        return firerate
    }

    fun getAccuracy(): Int {
        return accuracy
    }

    fun getShots(): Int {
        return shots
    }

    fun getEffect(): String {
        return effect
    }

    fun getFireBar(): ProgressBar {
        return fireBar
    }

    fun isActive(): Boolean {
        return active
    }

    fun getFx(): WeaponFx {
        return fx
    }

    fun getTarget(): Tile {
        return target!!
    }



    /********************
     *     Setters     *
     ********************/
    fun updateFireBar(): Boolean {
        if (fireBar.value == fireBar.maxValue) {
            return true
        } else {
            fireBar.value = fireBar.value + 1
            return false
        }
    }

    fun resetFireBar(): Unit {
        fireBar.value = fireBar.minValue
    }

    fun activate(): Unit {
        active = true
    }

    fun deactivate(): Unit {
        active = false
    }

    fun setTarget(tile: Tile): Unit {
        target = tile
    }
}
