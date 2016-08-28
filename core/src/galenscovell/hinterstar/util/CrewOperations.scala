package galenscovell.hinterstar.util

import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
  private var gameScreen: GameScreen = _
  private var selectedCrewmate: Crewmate = _
  private var weaponCrewmate: Crewmate = _
  private var crewmatePreviousAssignment: Tile = _



  def initialize(gs: GameScreen): Unit = {
    gameScreen = gs
  }

  /**
    * Called when player selects crewmate from main HUD.
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
      gameScreen.getHudStage.closeWeaponSelect()
      gameScreen.getHudStage.refreshCrewAndStats()

      weaponCrewmate = null
    }
  }

  /**
    * Called if player cancels assigning crewmate to weapon subsystem.
    * Closes weapon select panel and assigns crewmate back to previous assignment.
    */
  def cancelWeaponAssignment(): Unit = {
    if (weaponCrewmate != null) {
      gameScreen.getHudStage.closeWeaponSelect()
      weaponCrewmate.getAssignment.removeCrewmate()

      if (crewmatePreviousAssignment != null) {
        weaponCrewmate.setAssignment(crewmatePreviousAssignment)
        crewmatePreviousAssignment.assignCrewmate()
        crewmatePreviousAssignment = null
      }
      gameScreen.getHudStage.refreshCrewAndStats()

      weaponCrewmate = null
    }
  }

  /**
    * Called when player selects subsystem after selecting a crewmate for assignment.
    * Assigns crewmate to new subsystem.
    * If new assignment is a weapon subsystem, opens weapon select panel.
    */
  def assignCrewmate(newAssignment: Tile): Unit = {
    if (selectedCrewmate != null) {
      // TODO: Open 'Select a subsystem' tooltip label on HUD
      // TODO: Have option to cancel assignment
      if (!newAssignment.occupancyFull) {
        val oldAssignment: Tile = selectedCrewmate.getAssignment

        if (oldAssignment != null) {
          crewmatePreviousAssignment = oldAssignment
          oldAssignment.removeCrewmate()

          if (oldAssignment.isWeaponSubsystem && !newAssignment.isWeaponSubsystem) {
            val currentWeapon: Weapon = selectedCrewmate.getWeapon
            if (currentWeapon != null) {
              selectedCrewmate.removeWeapon()
              PlayerData.getShip.unequipWeapon(currentWeapon)
            }
          }
        }

        selectedCrewmate.setAssignment(newAssignment)
        newAssignment.assignCrewmate()
        gameScreen.getHudStage.refreshCrewAndStats()

        if (newAssignment.isWeaponSubsystem) {
          weaponCrewmate = selectedCrewmate
          PlayerData.getShip.refreshWeaponSelectPanel(newAssignment.getName)
          gameScreen.getHudStage.openWeaponSelect()
        }
      }

      selectedCrewmate.unhighlightTable()
      selectedCrewmate = null
    }
  }
}
