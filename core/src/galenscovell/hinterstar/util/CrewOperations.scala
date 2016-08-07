package galenscovell.hinterstar.util

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

  def assignCrewmate(subsystem: String): Unit = {
    if (selectedCrewmate != null) {
      selectedCrewmate.setAssignment(subsystem)
      gameScreen.getHudStage.refreshCrewAndStats()
      gameScreen.getActionStage.disableSubsystemOverlay()
    }
  }
}
