package galenscovell.hinterstar.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import galenscovell.hinterstar.map.{Location, Sector}
import galenscovell.hinterstar.processing.{Event, EventParser}
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen

import scala.collection.mutable.ArrayBuffer


/**
  * LocationRepo houses Location data and methods that are called throughout application.
  * Currently, Location data is needed in some areas that aren't created with it.
  * Ideally, this will be able to be eradicated through redesign in architecture.
  */
object LocationRepo {
  val locationsInRange: ArrayBuffer[Location] = ArrayBuffer()
  val eventParser: EventParser = new EventParser()

  var gameScreen: GameScreen = _
  var locations: ArrayBuffer[Location] = ArrayBuffer()
  var currentLocation: Location = _
  var currentSelection: Location = _
  var shapeRenderer: ShapeRenderer = new ShapeRenderer
  var playerRange: Int = 10  // playerRange should be based on equipped Engine Parts


// Called from GameScreen //
  /**
    * Sets the gameScreen for LocationRepo.
    * WHY IT'S HERE: Repository has to have gameScreen set but object has no constructor.
    * WORKAROUND IDEAS:
    */
  def setup(game: GameScreen): Unit = {
    gameScreen = game
  }

  /**
    * Finds all of the Locations in range of the Player's current Location.
    * WHY IT'S HERE: Navmap has to display Locations in range of player but it has no access to Location data.
    * WORKAROUND IDEAS:
    */
  def setTargetsInRange(): Unit = {
    locationsInRange.clear()

    for (location <- locations) {
      val squareDist: Double = Math.pow(currentLocation.getSector.sx - location.getSector.sx, 2) + Math.pow(currentLocation.getSector.sy - location.getSector.sy, 2)
      if (squareDist <= Math.pow(playerRange, 2)) {
        locationsInRange += location
      }
    }
  }

  /**
    * Use ShapeRenderer to render circles and pathing on navmap.
    * WHY IT'S HERE: Navmap has to display these shapes but has no access to Location data.
    * WORKAROUND IDEAS:
    */
  def drawShapes(): Unit = {
    val radius: Float = playerRange * Constants.SECTORSIZE
    val centerX: Float = currentLocation.getSector.sx * Constants.SECTORSIZE + (Constants.SECTORSIZE / 2)
    val centerY: Float = Gdx.graphics.getHeight - (currentLocation.getSector.sy * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2)

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f)
    shapeRenderer.circle(centerX, centerY, radius)
    shapeRenderer.circle(centerX, centerY, 20)

    if (locationsInRange != null && locationsInRange.nonEmpty) {
      shapeRenderer.setColor(0.93f, 0.94f, 0.95f, 0.6f)

      for (location <- locationsInRange) {
        shapeRenderer.line(
          (currentLocation.getSector.sx * Constants.SECTORSIZE) + (Constants.SECTORSIZE / 2),
          Gdx.graphics.getHeight - (currentLocation.getSector.sy * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2),
          (location.getSector.sx * Constants.SECTORSIZE) + (Constants.SECTORSIZE / 2),
          Gdx.graphics.getHeight - (location.getSector.sy * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2)
        )
      }
    }
    if (currentSelection != null) {
      shapeRenderer.setColor(0.18f, 0.8f, 0.44f, 0.6f)
      val selectionX: Float = currentSelection.getSector.sx * Constants.SECTORSIZE + (Constants.SECTORSIZE / 2)
      val selectionY: Float = Gdx.graphics.getHeight - (currentSelection.getSector.sy * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2)
      shapeRenderer.circle(selectionX, selectionY, 20)
    }

    shapeRenderer.end()
  }



// Called from GameStage //
  /**
    * Increments the current Location's current Event.
    * WHY IT'S HERE: GameStage houses 'Next Event' button, but has no access to Location data.
    * WORKAROUND IDEAS:
    */
  def parseNextEvent: Event = {
    currentLocation.getCurrentEvent
  }



// Called from MapPanel //
  /**
    * If currently selected Location is within range of Player, travel to it (set is as Current and enter()).
    * WHY IT'S HERE: Travel is called from MapPanel, but it has no access to Location data.
    * WORKAROUND IDEAS:
    */
  def travelToSelection: Boolean = {
    if (currentSelection != null && locationsInRange.contains(currentSelection)) {
      currentLocation.getSector.becomeExplored()
      currentLocation = currentSelection
      currentSelection.getSector.becomeCurrent()
      currentSelection.enter()
      setSelection(null)
      true
    } else {
      false
    }
  }



// Called from Sector //
  /**
    * When a non-EMPTY Sector is clicked on, locates its associated Location.
    * If associated Location is not the currentLocation, sets this Location as the currentSelection,
    *     then calculates 4 * euclidean distance to it and updateDistanceLabel().
    * WHY IT'S HERE: Each Sector has no access to its containing Location, GameStage has no access to Location data.
    * WORKAROUND IDEAS:
    */
  def setSelection(selection: Sector): Unit = {
    var distance: Double = 0.0

    if (selection != null) {
      for (location <- locations) {
        if ((location.getSector == selection) && !(location == currentLocation)) {
          currentSelection = location
          distance = 4 * Math.sqrt(
            Math.pow(currentLocation.getSector.sx - selection.sx, 2) +
              Math.pow(currentLocation.getSector.sy - selection.sy, 2)
          )
        }
      }
    }

    val gameStage: GameStage = gameScreen.getGameStage.asInstanceOf[GameStage]
    gameStage.updateDistanceLabel(f"Distance: $distance%1.1f AU")
  }



// Called from MapGenerator //
  /**
    * Establishes Location data for LocationRepo.
    * Sets the furthest to the left Location as the Player's currentLocation.
    * Sets this first Location as the Tutorial Location.
    * WHY IT'S HERE: LocationRepo needs Location data for everything else.
    * WORKAROUND IDEAS:
    */
  def populateLocations(locationsToSet: ArrayBuffer[Location]): Unit = {
    locations = locationsToSet
    for (location <- locations) {
      if (currentLocation == null || location.x < currentLocation.x) {
        currentLocation = location
      }
    }
    currentLocation.getSector.becomeCurrent()

    // Establish starting location as the Sol sector
    currentLocation.setTutorialLocation()
  }
}
