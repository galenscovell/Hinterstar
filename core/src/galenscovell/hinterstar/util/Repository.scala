package galenscovell.hinterstar.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import galenscovell.hinterstar.map.Sector
import galenscovell.hinterstar.processing.{EventContainer, EventParser}
import galenscovell.hinterstar.things.inanimate.Location
import galenscovell.hinterstar.ui.components.GameStage
import galenscovell.hinterstar.ui.screens.GameScreen

import scala.collection.mutable.ArrayBuffer


object Repository {
  var gameScreen: GameScreen = null
  var locations: ArrayBuffer[Location] = ArrayBuffer()
  var locationsInRange: ArrayBuffer[Location] = ArrayBuffer()
  var currentLocation: Location = null
  var currentSelection: Location = null
  var sectors: Array[Array[Sector]] = null
  var shapeRenderer: ShapeRenderer = null
  var playerRange: Int = 0
  val eventParser: EventParser = new EventParser()


  /**
    * Called from GameScreen
    */
  def setup(game: GameScreen): Unit = {
    gameScreen = game
    shapeRenderer = new ShapeRenderer
    playerRange = 10
  }

  def setTargetsInRange(): Unit = {
    locationsInRange.clear()

    for (location <- locations) {
      val squareDist: Double = Math.pow(currentLocation.getSector.sx - location.getSector.sx, 2) + Math.pow(currentLocation.getSector.sy - location.getSector.sy, 2)
      if (squareDist <= Math.pow(playerRange, 2)) {
        locationsInRange += location
      }
    }
  }

  def drawShapes(): Unit = {
    val radius: Float = playerRange * Constants.SECTORSIZE
    val centerX: Float = currentLocation.getSector.sx * Constants.SECTORSIZE + (Constants.SECTORSIZE / 2)
    val centerY: Float = Gdx.graphics.getHeight - (currentLocation.getSector.sy * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2)

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f)
    shapeRenderer.circle(centerX, centerY, radius)
    shapeRenderer.circle(centerX, centerY, 20)

    if (locationsInRange != null && locationsInRange.size > 0) {
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


  /**
    * Called from GameStage
    */
  def parseNextEvent: EventContainer = {
    val nextEventType: String = currentLocation.getCurrentEvent.getType
    val parsedEvent: EventContainer = eventParser.parse(nextEventType)
    parsedEvent
  }


  /**
    * Called from MapPanel
    */
  def setSectors(sectors: Array[Array[Sector]]): Unit = {
    Repository.sectors = sectors
  }

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


  /**
    * Called from Sector
    */
  def setSelection(selection: Sector): Unit = {
    if (selection == null) {
      val gameStage: GameStage = gameScreen.getGameStage.asInstanceOf[GameStage]
      gameStage.updateDistanceLabel("Distance: 0.0 AU")
    } else {
      for (location <- locations) {
        if (location.getSector eq selection) {
          if (!(location eq currentLocation)) {
            currentSelection = location
            val distance: Double = Math.sqrt(Math.pow(currentLocation.getSector.sx - selection.sx, 2) + Math.pow(currentLocation.getSector.sy - selection.sy, 2)) * 4
            val gameStage: GameStage = gameScreen.getGameStage.asInstanceOf[GameStage]
            gameStage.updateDistanceLabel(f"Distance: $distance%1.1f AU")
          }
        }
      }
    }
  }


  /**
    * Called from MapGenerator
    */
  def populateLocations(locationsToSet: ArrayBuffer[Location]): Unit = {
    locations = locationsToSet
    for (location <- locations) {
      if (currentLocation == null || location.x < currentLocation.x) {
        currentLocation = location
      }
    }
    currentLocation.getSector.becomeCurrent()
  }
}
