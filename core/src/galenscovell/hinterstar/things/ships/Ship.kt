package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui.*
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.components.gamescreen.hud.*
import galenscovell.hinterstar.util.Resources



class Ship(private val name: String, private val description: String,
           private var weapons: List<Weapon>, private val subsystemNames: List<String>) {
    private val activeWeaponPanel: ActiveWeaponPanel = ActiveWeaponPanel(this)
    private val weaponSelectPanel: WeaponSelectPanel = WeaponSelectPanel(this)

    // TODO: Set max health depending on ship
    private val healthBar: ProgressBar = createBar(100f)
    private var isPlayer: Boolean = false

    lateinit private var interiorOverlay: InteriorOverlay



    private fun createBar(health: Float): ProgressBar {
        val bar: ProgressBar = ProgressBar(0f, health, 1f, true, Resources.hullHealthBarStyle)
        bar.value = bar.maxValue
        bar.setAnimateDuration(0.5f)
        return bar
    }



    /********************
     *     Getters     *
     ********************/
    fun getName(): String {
        return name
    }

    fun getDescription(): String {
        return description
    }

    fun getWeapons(): List<Weapon> {
        return weapons
    }

    fun getSubsystemNames(): List<String> {
        return subsystemNames
    }

    fun isPlayerShip(): Boolean {
        return isPlayer
    }

    fun getSubsystemMap(): MutableMap<String, Tile> {
        return interiorOverlay!!.getSubsystemMap()
    }

    fun getHealthBar(): ProgressBar {
        return healthBar
    }

    fun getHealth(): Int {
        return healthBar.value.toInt()
    }



    /********************
     *     Setters     *
     ********************/
    fun setWeapons(w: List<Weapon>): Unit {
        weapons = w
    }

    fun setPlayerShip(): Unit {
        isPlayer = true
    }

    fun equipWeapon(w: Weapon): Unit {
        w.activate()
        activeWeaponPanel.refresh(weapons)
    }

    fun unequipWeapon(w: Weapon): Unit {
        w.resetFireBar()
        w.deactivate()
        activeWeaponPanel.refresh(weapons)
    }

    fun setHealth(amount: Float): Unit {
        healthBar.value = amount
    }

    fun updateHealth(amount: Float): Unit {
        var health: Float = healthBar.value.toInt() + amount

        if (health < 0) {
            // TODO: This means game over, at some point
            health = 0f
        } else if (health > healthBar.maxValue) {
            health = healthBar.maxValue
        }

        healthBar.value = health
    }



    /*********************
     *     Updating     *
     *********************/
    fun updateActiveWeapons(): List<Weapon> {
        val readyWeapons: MutableList<Weapon> = mutableListOf()

        for (weapon: Weapon in weapons) {
            if (weapon.isActive() && weapon.updateFireBar()) {
                readyWeapons.add(weapon)
            }
        }

        return readyWeapons.toList()
    }



    /***********************
     *    UI Component    *
     ***********************/
    fun createInterior(): Unit {
        interiorOverlay = InteriorOverlay(this)
    }

    fun getInterior(): InteriorOverlay {
        return interiorOverlay
    }

    fun getActiveWeaponPanel(): Table {
        return activeWeaponPanel
    }

    fun getWeaponSelectPanel(): Table {
        return weaponSelectPanel
    }

    fun refreshWeaponSelectPanel(subsystem: String, crewmate: String): Unit {
        weaponSelectPanel.refresh(weapons, subsystem, crewmate)
    }
}
