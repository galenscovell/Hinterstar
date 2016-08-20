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

  def equipWeapon(weapon: Weapon): Unit = {
    if (weaponCrewmate != null) {
      val currentWeapon: Weapon = weaponCrewmate.getWeapon
      if (currentWeapon != null) {
        PlayerData.getShip.unequipWeapon(weapon)
      }
      weaponCrewmate.setWeapon(weapon)
      PlayerData.getShip.equipWeapon(weapon)
      gameScreen.getHudStage.closeWeaponSelect()

      weaponCrewmate = null
    }
  }

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

  def assignCrewmate(subsystem: Tile): Unit = {
    if (selectedCrewmate != null) {
      if (!subsystem.occupancyFull) {
        val currentAssignment: Tile = selectedCrewmate.getAssignment
        if (currentAssignment != null) {
          crewmatePreviousAssignment = currentAssignment
          currentAssignment.removeCrewmate()
        }
        selectedCrewmate.setAssignment(subsystem)
        subsystem.assignCrewmate()
        gameScreen.getHudStage.refreshCrewAndStats()

        // TODO: Add support for other weapon subsystems
        if (subsystem.getName == "Weapon Control") {
          weaponCrewmate = selectedCrewmate
          PlayerData.getShip.refreshWeaponSelectPanel(subsystem.getName)
          gameScreen.getHudStage.openWeaponSelect()
        }
      }

      selectedCrewmate.unhighlightTable()
      selectedCrewmate = null
    }
  }
}
