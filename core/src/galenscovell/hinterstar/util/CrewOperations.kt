package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.g2d.Batch
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.processing.Pathfinder
import galenscovell.hinterstar.things.entities.*
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
    lateinit private var gameScreen: GameScreen
    private val pathfinder: Pathfinder = Pathfinder()

    private var selectedCrewmate: Crewmate? = null
    private var weaponCrewmate: Crewmate? = null
    private var selectedWeapon: Weapon? = null



    fun setGameScreen(gs: GameScreen): Unit {
        gameScreen = gs
    }

    fun selectCrewmate(newCrewmate: Crewmate): Unit {
        if (selectedCrewmate != null) {
            selectedCrewmate!!.unhighlightTable()
            gameScreen.getInterfaceStage().closeAssignmentSelect()
        }

        if (selectedCrewmate == newCrewmate) {
            selectedCrewmate = null
        } else {
            newCrewmate.highlightTable()
            selectedCrewmate = newCrewmate
            gameScreen.getInterfaceStage().openAssignmentSelect()
        }
    }

    fun assignCrewmate(newAssignment: Tile): Unit {
        if (selectedCrewmate != null) {
            val currentAssignment: Tile? = selectedCrewmate!!.getAssignment()

            if (currentAssignment != null && currentAssignment != newAssignment && !newAssignment.occupancyFull()) {
                gameScreen.getInterfaceStage().closeAssignmentSelect()
                val flag: CrewmateFlag = selectedCrewmate!!.getFlag()

                // If crewmate is currently in motion, they're not assigned to a subsystem, so skip unassignment
                if (!flag.hasPath()) {
                    currentAssignment.removeCrewmate()
                }

                // If previous assignment was a weapon, unequip that weapon
                if (currentAssignment.isWeaponSubsystem()) {
                    val currentWeapon: Weapon? = selectedCrewmate?.getWeapon()

                    if (currentWeapon != null) {
                        selectedCrewmate!!.removeWeapon()
                        PlayerData.getShip().unequipWeapon(currentWeapon)
                    }
                }

                if (flag.hasPath()) {
                    // If no current assignment, set path start to the last visited tile and start position to current pos
                    flag.setPath(flag.getCurrentPosition(), pathfinder.findPath(flag.getPreviousTile(), newAssignment))
                } else {
                    // If flag has current assignment, set path start to that tile
                    flag.setPath(pathfinder.findPath(currentAssignment, newAssignment))
                }

                selectedCrewmate!!.setAssignment(null)
                selectedCrewmate!!.setAssignmentIcon()
                flag.start(0)
            }

            selectedCrewmate!!.unhighlightTable()
            selectedCrewmate = null
        }
    }



    fun showWeaponSelect(crewmate: Crewmate): Unit {
        weaponCrewmate = crewmate
        PlayerData.getShip().refreshWeaponSelectPanel(weaponCrewmate!!.getAssignment()!!.getSubsystemName(), weaponCrewmate!!.getName())
        gameScreen.getInterfaceStage().openWeaponSelect()
    }

    fun cancelWeaponAssignment(): Unit {
        gameScreen.getInterfaceStage().closeWeaponSelect()
        gameScreen.getInterfaceStage().closeTargetSelect()
        weaponCrewmate = null
        selectedWeapon = null
    }

    fun selectWeapon(weapon: Weapon): Unit {
        selectedWeapon = weapon
        gameScreen.getInterfaceStage().closeWeaponSelect()
        gameScreen.getInterfaceStage().openTargetSelect()
    }

    fun weaponSelected(): Boolean {
        return selectedWeapon != null
    }

    fun targetEnemySubsystem(enemySubsystem: Tile): Unit {
        selectedWeapon!!.setTarget(enemySubsystem)
        equipWeapon(selectedWeapon!!)
    }

    fun equipWeapon(weapon: Weapon): Unit {
        if (weaponCrewmate != null) {
            val oldWeapon: Weapon? = weaponCrewmate!!.getWeapon()

            if (oldWeapon != null) {
                weaponCrewmate!!.removeWeapon()
                PlayerData.getShip().unequipWeapon(oldWeapon)
            }

            weaponCrewmate!!.setWeapon(weapon)
            PlayerData.getShip().equipWeapon(weapon)
            gameScreen.getInterfaceStage().closeTargetSelect()
            gameScreen.getInterfaceStage().refreshStatsPanel()

            weaponCrewmate = null
            selectedWeapon = null
        }
    }



    fun updateCrewmatePositions(): Unit {
        for (crewmate: Crewmate in PlayerData.getCrew()) {
            val flag: CrewmateFlag = crewmate.getFlag()

            if (flag.isActive()) {
                if (flag.step()) {
                    flag.setNextDestination()

                    if (flag.hasPath()) {
                        flag.start(5)
                    } else {
                        // Path is empty -- reached destination
                        val flagTile: Tile = flag.getCurrentTile()
                        flagTile.assignCrewmate()
                        crewmate.setAssignment(flagTile)
                        crewmate.setAssignmentIcon()
                        gameScreen.getInterfaceStage().refreshStatsPanel()
                        flag.stop()
                    }
                }
            }
        }
    }

    fun drawCrewmatePositions(delta: Float, spriteBatch: Batch): Unit {
        for (crewmate: Crewmate in PlayerData.getCrew()) {
            val flag: CrewmateFlag = crewmate.getFlag()

            if (flag.hasPath() || flag.drawing()) {
                flag.draw(delta, spriteBatch)
            }
        }
    }
}
