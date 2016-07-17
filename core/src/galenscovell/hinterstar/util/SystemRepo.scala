package galenscovell.hinterstar.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import galenscovell.hinterstar.map.{System, SystemMarker}
import galenscovell.hinterstar.processing.{Event, EventParser}
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen

import scala.collection.mutable.ArrayBuffer


/**
  * SystemRepo houses System data and methods that are called throughout application.
  * Currently, System data is needed in some areas that aren't created with it.
  * Ideally, this will be able to be eradicated through redesign in architecture.
  */
object SystemRepo {
  val systemsInRange: ArrayBuffer[System] = ArrayBuffer()
  val eventParser: EventParser = new EventParser()

  var gameScreen: GameScreen = _
  var systems: ArrayBuffer[System] = ArrayBuffer()
  var currentSystem: System = _
  var currentSelection: System = _
  var shapeRenderer: ShapeRenderer = new ShapeRenderer
  var playerRange: Int = 10  // playerRange should be based on equipped Engine Parts


// Called from GameScreen //
  /**
    * Sets the gameScreen for SystemRepo.
    * WHY IT'S HERE: Repository has to have gameScreen set but object has no constructor.
    * WORKAROUND IDEAS:
    */
  def setup(game: GameScreen): Unit = {
    gameScreen = game
  }

  /**
    * Finds all of the Systems in range of the Player's current System.
    * WHY IT'S HERE: Navmap has to display Systems in range of player but it has no access to System data.
    * WORKAROUND IDEAS:
    */
  def setTargetsInRange(): Unit = {
    systemsInRange.clear()

    for (system <- systems) {
      val squareDist: Double = Math.pow(currentSystem.getSystemMarker.sx - system.getSystemMarker.sx, 2) + Math.pow(currentSystem.getSystemMarker.sy - system.getSystemMarker.sy, 2)
      if (squareDist <= Math.pow(playerRange, 2)) {
        systemsInRange += system
      }
    }
  }

  /**
    * Use ShapeRenderer to render circles and pathing on navmap.
    * WHY IT'S HERE: Navmap has to display these shapes but has no access to System data.
    * WORKAROUND IDEAS:
    */
  def drawShapes(): Unit = {
    val radius: Float = playerRange * Constants.SYSTEMMARKER_SIZE
    val centerX: Float = currentSystem.getSystemMarker.sx * Constants.SYSTEMMARKER_SIZE + (Constants.SYSTEMMARKER_SIZE / 2)
    val centerY: Float = Gdx.graphics.getHeight - (currentSystem.getSystemMarker.sy * Constants.SYSTEMMARKER_SIZE) - (2 * Constants.SYSTEMMARKER_SIZE) - (Constants.SYSTEMMARKER_SIZE / 2)

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f)
    shapeRenderer.circle(centerX, centerY, radius)
    shapeRenderer.circle(centerX, centerY, 20)

    if (systemsInRange != null && systemsInRange.nonEmpty) {
      shapeRenderer.setColor(0.93f, 0.94f, 0.95f, 0.6f)

      for (system <- systemsInRange) {
        shapeRenderer.line(
          (currentSystem.getSystemMarker.sx * Constants.SYSTEMMARKER_SIZE) + (Constants.SYSTEMMARKER_SIZE / 2),
          Gdx.graphics.getHeight - (currentSystem.getSystemMarker.sy * Constants.SYSTEMMARKER_SIZE) - (2 * Constants.SYSTEMMARKER_SIZE) - (Constants.SYSTEMMARKER_SIZE / 2),
          (system.getSystemMarker.sx * Constants.SYSTEMMARKER_SIZE) + (Constants.SYSTEMMARKER_SIZE / 2),
          Gdx.graphics.getHeight - (system.getSystemMarker.sy * Constants.SYSTEMMARKER_SIZE) - (2 * Constants.SYSTEMMARKER_SIZE) - (Constants.SYSTEMMARKER_SIZE / 2)
        )
      }
    }
    if (currentSelection != null) {
      shapeRenderer.setColor(0.18f, 0.8f, 0.44f, 0.6f)
      val selectionX: Float = currentSelection.getSystemMarker.sx * Constants.SYSTEMMARKER_SIZE + (Constants.SYSTEMMARKER_SIZE / 2)
      val selectionY: Float = Gdx.graphics.getHeight - (currentSelection.getSystemMarker.sy * Constants.SYSTEMMARKER_SIZE) - (2 * Constants.SYSTEMMARKER_SIZE) - (Constants.SYSTEMMARKER_SIZE / 2)
      shapeRenderer.circle(selectionX, selectionY, 20)
    }

    shapeRenderer.end()
  }



// Called from GameStage //
  /**
    * Increments the current System's current Event.
    * WHY IT'S HERE: GameStage houses 'Next Event' button, but has no access to System data.
    * WORKAROUND IDEAS:
    */
  def parseNextEvent: Event = {
    currentSystem.getCurrentEvent
  }



// Called from MapPanel //
  /**
    * If currently selected System is within range of Player, travel to it (set is as Current and enter()).
    * WHY IT'S HERE: Travel is called from MapPanel, but it has no access to System data.
    * WORKAROUND IDEAS:
    */
  def travelToSelection: Boolean = {
    if (currentSelection != null && systemsInRange.contains(currentSelection)) {
      currentSystem.getSystemMarker.becomeExplored()
      currentSystem = currentSelection
      currentSelection.getSystemMarker.becomeCurrent()
      currentSelection.enter()
      setSelection(null)
      true
    } else {
      false
    }
  }



// Called from SystemMarker //
  /**
    * When a non-EMPTY SystemMarker is clicked on, locates its associated System.
    * If associated System is not the currentSystem, sets this System as the currentSelection,
    *     then calculates 4 * euclidean distance to it and updateDistanceLabel().
    * WHY IT'S HERE: Each SystemMarker has no access to its containing System, GameStage has no access to System data.
    * WORKAROUND IDEAS:
    */
  def setSelection(selection: SystemMarker): Unit = {
    var distance: Double = 0.0

    if (selection != null) {
      for (system <- systems) {
        if ((system.getSystemMarker == selection) && !(system == currentSystem)) {
          currentSelection = system
          distance = 4 * Math.sqrt(
            Math.pow(currentSystem.getSystemMarker.sx - selection.sx, 2) +
              Math.pow(currentSystem.getSystemMarker.sy - selection.sy, 2)
          )
        }
      }
    }

    val gameStage: GameStage = gameScreen.getGameStage.asInstanceOf[GameStage]
    gameStage.updateDistanceLabel(f"Distance: $distance%1.1f AU")
  }



// Called from MapGenerator //
  /**
    * Establishes System data for SystemRepo.
    * Sets the furthest to the left System as the Player's currentSystem.
    * Sets this first System as the Tutorial System.
    * WHY IT'S HERE: SystemRepo needs System data for everything else.
    * WORKAROUND IDEAS:
    */
  def populateSystems(systemsToSet: ArrayBuffer[System]): Unit = {
    systems = systemsToSet
    for (system <- systems) {
      if (currentSystem == null || system.x < currentSystem.x) {
        currentSystem = system
      }
    }
    currentSystem.getSystemMarker.becomeCurrent()
    currentSystem.setAsTutorial()
  }
}
