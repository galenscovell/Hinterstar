package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util._


class Tile(val tx: Int, val ty: Int, tileType: String) extends Table {
  private var neighbors: Array[Tile] = _
  private val sprite: Sprite = createSprite

  initialize()



  private def initialize(): Unit = {
    if (isTraversible) {
      this.setTouchable(Touchable.enabled)
      this.addListener(new ClickListener() {
        override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean = {
          if (CrewOperations.crewmateSelected) {
            CrewOperations.moveCrewmate(getThisTile)
            true
          } else {
            false
          }
        }
      })

      // Start all player crewmates in their saved assigned subsystem
      // Start unassigned player crewmates in Medbay subsystem
      for (crewmate: Crewmate <- PlayerData.getCrew) {
        if (crewmate.getAssignmentName == tileType) {
          crewmate.setAssignment(getThisTile)
        }
      }
    }

    createIcon()
  }

  private def createSprite: Sprite = {
      tileType match {
        case "empty" => null
        case "ladder-both" => Resources.atlas.createSprite("tile-ladder-both")
        case "ladder-up" => Resources.atlas.createSprite("tile-ladder-up")
        case "ladder-down" => Resources.atlas.createSprite("tile-ladder-down")
        case _ => Resources.atlas.createSprite("tile-empty")
      }
  }

  private def createIcon(): Unit = {
    var image: Image = null
    tileType match {
      case "Turret" => image = new Image(Resources.atlas.createSprite("icon_weapon"))
      case "Engine" => image = new Image(Resources.atlas.createSprite("icon_engine"))
      case "Helm" => image = new Image(Resources.atlas.createSprite("icon_helm"))
      case "Shield" => image = new Image(Resources.atlas.createSprite("icon_shield"))
      case "Medbay" => image = new Image(Resources.atlas.createSprite("icon_medbay"))
      case "Life Support" => image = new Image(Resources.atlas.createSprite("icon_oxygen"))
      case "Generator" => image = new Image(Resources.atlas.createSprite("icon_battery"))
      case "Sensor" => image = new Image(Resources.atlas.createSprite("icon_sensor"))
      case "Sleep Pod" => image = new Image(Resources.atlas.createSprite("icon_sleeppod"))
      case _ => Unit
    }

    if (image != null) {
      addActor(image)
      image.toBack()
      image.setSize(32, 32)
      image.setPosition(2 + getWidth / 2 + image.getWidth / 2, getHeight / 2 + image.getHeight / 2)
    }
  }


  /********************
    *     Getters     *
    ********************/
  def getTileType: String = {
    tileType
  }

  def isLadderUp: Boolean = {
    tileType.equals("ladder-both") || tileType.equals("ladder-up")
  }

  def isLadderDown: Boolean = {
    tileType.equals("ladder-both") || tileType.equals("ladder-down")
  }

  private def getThisTile: Tile = {
    this
  }

  def isTraversible: Boolean = {
    tileType != "empty"
  }

  def getNeighbors: Array[Tile] = {
    neighbors
  }

  def getActorCoordinates: Vector2 = {
    new Vector2(
      Constants.TILE_WIDTH * tx,                                    // Tile grid x * tile width
      Constants.INTERIOR_HEIGHT - Constants.TILE_HEIGHT * (ty + 1)  // Ship interior height - Tile grid (y + 1) * tile height
    )
  }



  /********************
    *     Setters     *
    ********************/
  def setNeighbors(rooms: Array[Tile]): Unit = {
    neighbors = rooms
  }



  /**********************
    *     Rendering     *
    **********************/
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (isTraversible) {
      batch.draw(sprite, getX, getY, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)
      super.draw(batch, parentAlpha)
    }
  }
}
