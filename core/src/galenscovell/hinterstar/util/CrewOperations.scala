package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.g2d.Batch
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
        if (flag.step) {
          flag.setNextDestination()

          if (flag.hasPath) {
            flag.start(5)
          } else {
            // Path is empty -- reached destination
            val flagTile: Tile = flag.getCurrentTile
            flagTile.assignCrewmate()
            crewmate.setAssignment(flagTile)

            if (flagTile.isWeaponSubsystem) {
              weaponCrewmate = crewmate
              PlayerData.getShip.refreshWeaponSelectPanel(flagTile.getName)
              gameScreen.getInterfaceStage.openWeaponSelect()
            }

            crewmate.setAssignmentIcon()
            gameScreen.getInterfaceStage.refreshStatsPanel()
            flag.stop()
          }
        }
      }
    }
  }

  /**
    * Called every game render in mai ngame loop.
    * Draws crewmate flag markers in their current position on the ship.
    */
  def drawCrewmatePositions(delta: Float, spriteBatch: Batch): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val flag: CrewmateFlag = crewmate.getFlag

      if (flag.hasPath || flag.drawing) {
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
      gameScreen.getInterfaceStage.closeAssignmentSelect()
    }

    if (selectedCrewmate == newCrewmate) {
      selectedCrewmate = null
    } else {
      newCrewmate.highlightTable()
      selectedCrewmate = newCrewmate
      // TODO: Open 'Select a subsystem' tooltip label on HUD
      // TODO: Have option to cancel ^ assignment
      gameScreen.getInterfaceStage.openAssignmentSelect()
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
      val currentAssignment: Tile = selectedCrewmate.getAssignment
      if (currentAssignment != newAssignment && !newAssignment.occupancyFull) {
        gameScreen.getInterfaceStage.closeAssignmentSelect()
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

        // Now the question is -- what to do when multiple crewmates hit the weapon subsystem at once?
        // IDEA: When crewmate enters weapon subsystem, show an option somewhere to pick their weapon
        //    this would leave the timing entirely to the player rather than automatic.
        if (currentAssignment == null) {
          flag.setPath(flag.getCurrentPosition, pathfinder.findPath(flag.getPreviousTile, newAssignment))
        } else {
          flag.setPath(pathfinder.findPath(currentAssignment, newAssignment))
        }

        selectedCrewmate.setAssignment(null)
        flag.start(5)

        selectedCrewmate.setAssignmentIcon()
      }

      selectedCrewmate.unhighlightTable()
      selectedCrewmate = null
    }
  }
}
