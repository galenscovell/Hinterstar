package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.g2d.Batch
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.processing.Pathfinder
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.screens.GameScreen


object CrewOperations {
  private val pathfinder: Pathfinder = new Pathfinder
  private var gameScreen: GameScreen = _
  private var selectedCrewmate: Crewmate = _



  def initialize(gs: GameScreen): Unit = {
    gameScreen = gs
  }



  def crewmateSelected: Boolean = {
    selectedCrewmate != null
  }

  def selectCrewmate(newCrewmate: Crewmate): Unit = {
    if (selectedCrewmate != null) {
      selectedCrewmate.unhighlight()
      // gameScreen.getInterfaceStage.closeAssignmentSelect()
    }

    if (selectedCrewmate == newCrewmate) {
      selectedCrewmate = null
    } else {
      newCrewmate.highlight()
      selectedCrewmate = newCrewmate
      // gameScreen.getInterfaceStage.openAssignmentSelect()
    }
  }

  def moveCrewmate(assignment: Tile): Unit = {
    if (selectedCrewmate != null) {
      if (selectedCrewmate.getAssignment != assignment) {
        val path: Array[Tile] = pathfinder.findPath(selectedCrewmate.getAssignment, assignment)
        selectedCrewmate.setPath(path)
        selectedCrewmate.unhighlight()
        selectedCrewmate = null
      }
    }
  }



  /********************
    *    Updating     *
    ********************/
  def update(): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      if (crewmate != null) {
        crewmate.update()
      }
    }
  }


  /********************
    *    Rendering    *
    ********************/
  def drawCrewmateStats(delta: Float, batch: Batch): Unit = {
    for (crewmate: Crewmate <- PlayerData.getCrew) {
      if (crewmate != null) {
        crewmate.drawStats(delta, batch)
      }
    }
  }
}
