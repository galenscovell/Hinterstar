package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.processing.Pathfinder
import galenscovell.hinterstar.things.entities.{Crewmate, CrewmateFlag}
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
  private var gameScreen: GameScreen = _
  private var selectedCrewmate: Crewmate = _
  private var weaponCrewmate: Crewmate = _
  private val pathfinder: Pathfinder = new Pathfinder


  def initialize(gs: GameScreen): Unit = {
    gameScreen = gs
  }

  /**
    * Called every update in game loop.
    * Loops through current crewmates and checks if they're currently in motion to a new assignment.
    * If they are, decrement the frames they've been in their current assignment.
    * If their frames == 0, move them to the next tile in their assignment path.
    * TEMP: Mark current crewmate tile as path for debug drawing to see where they are.
    */
  def updateCrewmatePositions(): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val flag: CrewmateFlag = crewmate.getFlag

      if (flag.isActive) {
        if (flag.getFrames == 0) {
          // Reached destination
          if (!flag.hasPath) {
            val targetAssignment: Tile = crewmate.getTargetAssignment
            targetAssignment.assignCrewmate()
            crewmate.setCurrentAssignment(targetAssignment)
            crewmate.setTargetAssignment(null)

            if (targetAssignment.isWeaponSubsystem) {
              weaponCrewmate = crewmate
              PlayerData.getShip.refreshWeaponSelectPanel(targetAssignment.getName)
              gameScreen.getInterfaceStage.openWeaponSelect()
            }

            crewmate.setAssignmentIcon()
            gameScreen.getInterfaceStage.refreshStatsPanel()
            flag.setInactive()
          } else {
            flag.setDestination(flag.getNextDestination)
            flag.setFrames(5)
          }
        } else {
          flag.decrementFrames()
        }
      }
    }
  }

  def drawCrewmatePositions(delta: Float, spriteBatch: Batch): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val flag: CrewmateFlag = crewmate.getFlag

      if (flag.hasPath || !flag.finished) {
        flag.draw(delta, spriteBatch)
      }
    }
  }

  /**
    * Called when player selects crewmate from main interface.
    * Sets crewmate of interest for assignment operations.
    */
  def selectCrewmate(newCrewmate: Crewmate): Unit = {
    if (selectedCrewmate != null) {
      selectedCrewmate.unhighlightTable()
    }

    if (selectedCrewmate == newCrewmate) {
      selectedCrewmate = null
    } else {
      newCrewmate.highlightTable()
      selectedCrewmate = newCrewmate
    }
  }

  /**
    * Called when player makes a weapon selection from weapon select panel.
    * Sets weapon for selected crewmate and equips the weapon for ship.
    */
  def equipWeapon(weapon: Weapon): Unit = {
    if (weaponCrewmate != null) {
      val currentWeapon: Weapon = weaponCrewmate.getWeapon
      if (currentWeapon != null) {
        weaponCrewmate.removeWeapon()
        PlayerData.getShip.unequipWeapon(currentWeapon)
      }
      weaponCrewmate.setWeapon(weapon)
      PlayerData.getShip.equipWeapon(weapon)
      gameScreen.getInterfaceStage.closeWeaponSelect()
      gameScreen.getInterfaceStage.refreshStatsPanel()

      weaponCrewmate = null
    }
  }

  /**
    * Called when player selects subsystem after selecting a crewmate for assignment.
    * Assigns target subsystem to selected crewmate, sending them in motion with a path.
    */
  def assignCrewmate(newAssignment: Tile): Unit = {
    if (selectedCrewmate != null) {
      // TODO: Open 'Select a subsystem' tooltip label on HUD
      // TODO: Have option to cancel ^ assignment
      val currentAssignment: Tile = selectedCrewmate.getCurrentAssignment
      if (currentAssignment != newAssignment && !newAssignment.occupancyFull) {
        val flag: CrewmateFlag = selectedCrewmate.getFlag

        if (currentAssignment != null) {
          // If crewmate is currently in motion, they're not fully assigned to a subsystem, so skip this
          if (!flag.hasPath) {
            currentAssignment.removeCrewmate()
          }

          // If previous assignment was a weapon, unequip that weapon
          if (currentAssignment.isWeaponSubsystem) {
            val currentWeapon: Weapon = selectedCrewmate.getWeapon
            if (currentWeapon != null) {
              selectedCrewmate.removeWeapon()
              PlayerData.getShip.unequipWeapon(currentWeapon)
            }
          }
        }

        // Pathfinding, don't select weapon subsystem for now... (how to work around weapon select?)
        // AHA! Have crewmate pick their weapon _when they get there_ rather than before!
        // Now the question is -- what to do when multiple crewmates hit the weapon subsystem at once?
        // IDEA: When crewmate enters weapon subsystem, show an option somewhere to pick their weapon
        //    this would leave the timing entirely to the player rather than automatic.
        if (currentAssignment == null) {
          flag.setPath(flag.getCurrentPosition, pathfinder.findPath(flag.getCurrentTile, newAssignment))
        } else {
          flag.setPath(currentAssignment, pathfinder.findPath(currentAssignment, newAssignment))
        }

        selectedCrewmate.setCurrentAssignment(null)
        flag.setFrames(5)
        flag.setActive()

        selectedCrewmate.setAssignmentIcon()
        selectedCrewmate.setTargetAssignment(newAssignment)
      }

      selectedCrewmate.unhighlightTable()
      selectedCrewmate = null
    }
  }
}
