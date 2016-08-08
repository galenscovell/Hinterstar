package galenscovell.hinterstar.util

import galenscovell.hinterstar.generation.interior.Subsystem
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
  private var gameScreen: GameScreen = _
  private var selectedCrewmate: Crewmate = _


  def initialize(gs: GameScreen): Unit = {
    gameScreen = gs
  }

  def selectCrewmate(cm: Crewmate): Unit = {
    selectedCrewmate = cm
  }

  def assignCrewmate(subsystem: Subsystem): Unit = {
    if (selectedCrewmate != null) {
      if (!subsystem.occupancyFull) {
        val currentAssignment: Subsystem = selectedCrewmate.getAssignment
        if (currentAssignment != null) {
          currentAssignment.removeCrewmate()
        }
        selectedCrewmate.setAssignment(subsystem)
        subsystem.assignCrewmate()
        gameScreen.getHudStage.refreshCrewAndStats()
        gameScreen.getActionStage.disableSubsystemOverlay()
      }
    }
  }
}
