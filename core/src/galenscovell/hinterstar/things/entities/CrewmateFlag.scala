package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable


class CrewmateFlag(name: String) {
  private var position: Vector2 = _
  private var destination: Vector2 = _
  private var lastTile: Tile = _

  private val sprite: Sprite = new Sprite(Resources.atlas.createSprite("icon_crewmate_flag"))

  private var active: Boolean = false
  private var frames: Int = 0
  private var path: mutable.Stack[Tile] = mutable.Stack()



  def isActive: Boolean = {
    active
  }

  def getFrames: Int = {
    frames
  }

  def getNextDestination: Tile = {
    val nextTile: Tile = path.pop()
    lastTile = nextTile
    nextTile
  }

  def getCurrentTile: Tile = {
    lastTile
  }

  def getCurrentPosition: Vector2 = {
    position
  }

  def hasPath: Boolean = {
    path != null && path.nonEmpty
  }

  def finished: Boolean = {
    if (position != null && destination != null) {
      val diffX: Float = Math.abs(destination.x - position.x)
      val diffY: Float = Math.abs(destination.y - position.y)
      diffX <= 8 && diffY <= 8
    } else {
      true
    }
  }



  def setActive(): Unit = {
    active = true
  }

  def setInactive(): Unit = {
    active = false
  }

  def setPath(startVector: Vector2, p: mutable.Stack[Tile]): Unit = {
    path = p
    position = startVector
    setDestination(getNextDestination)
  }

  def setPath(startTile: Tile, p: mutable.Stack[Tile]): Unit = {
    setPath(startTile.getActorCoordinates, p)
  }

  def setDestination(dest: Tile): Unit = {
    destination = dest.getActorCoordinates
  }

  def setFrames(f: Int): Unit = {
    frames = f
  }

  def decrementFrames(): Unit = {
    frames -= 1
  }



  def draw(delta: Float, spriteBatch: Batch): Unit = {
    position.x += (destination.x - position.x) * 0.5f * delta
    position.y += (destination.y - position.y) * 0.5f * delta

    spriteBatch.draw(sprite, position.x - 6, position.y - 6, 24, 24)
  }
}
