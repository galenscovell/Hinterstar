package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.g2d.Batch
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.processing.Pathfinder
import galenscovell.hinterstar.things.entities.{Crewmate, CrewmateFlag}
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
  private val pathfinder: Pathfinder = new Pathfinder
  private var gameScreen: GameScreen = _

  private var selectedCrewmate: Crewmate = _
  private var weaponCrewmate: Crewmate = _



  def initialize(gs: GameScreen): Unit = {
    gameScreen = gs
  }



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
      gameScreen.getInterfaceStage.openAssignmentSelect()
    }
  }

  def showWeaponSelect(crewmate: Crewmate): Unit = {
    weaponCrewmate = crewmate
    PlayerData.getShip.refreshWeaponSelectPanel(weaponCrewmate.getAssignment.getName, weaponCrewmate.getName)
    gameScreen.getInterfaceStage.openWeaponSelect()
  }

  def cancelWeaponAssignment(): Unit = {
    if (weaponCrewmate != null) {
      gameScreen.getInterfaceStage.closeWeaponSelect()
      weaponCrewmate = null
    }
  }

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

  def assignCrewmate(newAssignment: Tile): Unit = {
    if (selectedCrewmate != null) {
      val currentAssignment: Tile = selectedCrewmate.getAssignment

      if (currentAssignment != newAssignment && !newAssignment.occupancyFull) {
        gameScreen.getInterfaceStage.closeAssignmentSelect()
        val flag: CrewmateFlag = selectedCrewmate.getFlag

        if (currentAssignment != null) {
          // If crewmate is currently in motion, they're not assigned to a subsystem, so skip unassignment
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

        if (currentAssignment == null) {
          // If no current assignment, set path start to the last visited tile and start position to current pos
          flag.setPath(flag.getCurrentPosition, pathfinder.findPath(flag.getPreviousTile, newAssignment))
        } else {
          // If flag has current assignment, set path start to that tile
          flag.setPath(pathfinder.findPath(currentAssignment, newAssignment))
        }

        selectedCrewmate.setAssignment(null)
        selectedCrewmate.setAssignmentIcon()
        flag.start(5)
      }

      selectedCrewmate.unhighlightTable()
      selectedCrewmate = null
    }
  }




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
            crewmate.setAssignmentIcon()
            gameScreen.getInterfaceStage.refreshStatsPanel()
            flag.stop()
          }
        }
      }
    }
  }

  def drawCrewmatePositions(delta: Float, spriteBatch: Batch): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      val flag: CrewmateFlag = crewmate.getFlag

      if (flag.hasPath || flag.drawing) {
        flag.draw(delta, spriteBatch)
      }
    }
  }
}
