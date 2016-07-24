package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent}
import galenscovell.hinterstar.generation.interior.TileType.TileType
import galenscovell.hinterstar.util._


class Tile(x: Int, y: Int) extends Actor {
  val sx: Int = x
  val sy: Int = y
  var tileType: TileType = TileType.EMPTY
  private var sprite: Sprite = _
  private var selected: Boolean = false
  private var neighborTiles: Array[Tile] = _
  private var bitmask: Int = 0
  var possibleDoor: Boolean = false


  this.addListener(new ActorGestureListener() {
    override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (isFloor()) {
        toggleSelection()
      }
    }

    override def touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit = {
      if (isFloor()) {
        toggleSelection()
      }
    }
  })



  def setBitmask(value: Int): Unit = {
    bitmask = value
  }

  def getBitmask(): Int = {
    bitmask
  }

  def setSprite(): Unit = {
    tileType match {
      case TileType.EMPTY => sprite = Resources.tileAtlas.createSprite("empty")
      case TileType.FLOOR => sprite = Resources.tileAtlas.createSprite("floor" + bitmask)
      case TileType.WALL => sprite = Resources.tileAtlas.createSprite("wall" + bitmask)
      case TileType.DOOR => sprite = Resources.tileAtlas.createSprite("door" + bitmask)
      case _ => sprite = Resources.tileAtlas.createSprite("empty")
    }
  }

  def setNeighbors(tiles: Array[Tile]): Unit = {
    neighborTiles = tiles
  }

  def getNeighbors(): Array[Tile] = {
    neighborTiles
  }

  def getDoorNeighbors(): Int = {
    var count: Int = 0

    for (tile: Tile <- neighborTiles) {
      if (tile.isDoor()) {
        count += 1
      }
    }
    count
  }

  def getWallNeighbors(): Int = {
    var count: Int = 0

    for (tile: Tile <- neighborTiles) {
      if (tile.isWall()) {
        count += 1
      }
    }
    count
  }



  def toggleSelection(): Unit = {
    selected = !selected
  }



  def becomeEmpty(): Unit = {
    tileType = TileType.EMPTY
  }

  def becomeFloor(): Unit = {
    tileType = TileType.FLOOR
  }

  def becomeWall(): Unit = {
    tileType = TileType.WALL
  }

  def becomeDoor(): Unit = {
    tileType = TileType.DOOR
  }



  def isEmpty(): Boolean = {
    tileType == TileType.EMPTY
  }

  def isFloor(): Boolean = {
    tileType == TileType.FLOOR
  }

  def isWall(): Boolean = {
    tileType == TileType.WALL
  }

  def isDoor(): Boolean = {
    tileType == TileType.DOOR
  }



  def debugDraw(): String = {
    tileType match {
      case TileType.EMPTY => "+"
      case TileType.FLOOR => " "
      case TileType.WALL => "#"
      case TileType.DOOR => "D"
      case _ => "!"
    }
  }

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (selected) {
      batch.setColor(0.5f, 0.9f, 0.5f, 1f)
    } else {
      batch.setColor(1, 1, 1, 1)
    }

    batch.draw(
      sprite,
      sx * Constants.TILE_SIZE + (2f * Constants.TILE_SIZE),
      Gdx.graphics.getHeight - (sy * Constants.TILE_SIZE) - (4.5f * Constants.TILE_SIZE),
      Constants.TILE_SIZE, Constants.TILE_SIZE
    )

    batch.setColor(1, 1, 1, 1)
  }
}
