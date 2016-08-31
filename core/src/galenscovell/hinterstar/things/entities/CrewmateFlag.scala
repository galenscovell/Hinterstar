package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.util.Resources


class CrewmateFlag(name: String) {
  private var position: Vector2 = _
  private var destination: Vector2 = _

  private val sprite: Sprite = new Sprite(Resources.atlas.createSprite("icon_crewmate_flag"))

  private var active: Boolean = false
  private var frames: Int = 0
  private var path: Array[Tile] = _
  private var index: Int = 0



  /********************
    *     Getters     *
    ********************/
  def isActive: Boolean = {
    active
  }

  def getPreviousTile: Tile = {
    if (index > 1) {
      path(index - 2)
    } else {
      path(0)
    }
  }

  def getCurrentTile: Tile = {
    path(index - 1)
  }

  def getCurrentPosition: Vector2 = {
    position
  }

  def hasPath: Boolean = {
    path != null && (index < path.length)
  }



  /********************
    *     Setters     *
    ********************/
  def setPath(startVector: Vector2, p: Array[Tile]): Unit = {
    path = p
    position = startVector
    index = 0
    setNextDestination()
  }

  def setPath(p: Array[Tile]): Unit = {
    setPath(p(0).getActorCoordinates, p)
  }

  def setNextDestination(): Unit = {
    index += 1

    if (index < path.length) {
      destination = path(index).getActorCoordinates.cpy()
    }
  }



  /*******************
    *    Updating    *
    *******************/
  def start(f: Int): Unit = {
    frames = f
    active = true
  }

  def step: Boolean = {
    frames -= 1
    if (frames == 0) {
      true
    } else {
      false
    }
  }

  def stop(): Unit = {
    active = false
  }



  /********************
    *    Rendering    *
    ********************/
  def drawing: Boolean = {
    if (position != null && destination != null) {
      val diffX: Float = Math.abs(destination.x - position.x)
      val diffY: Float = Math.abs(destination.y - position.y)
      diffX > 12 && diffY > 12
    } else {
      false
    }
  }

  def draw(delta: Float, spriteBatch: Batch): Unit = {
    position.x += (destination.x - position.x) * 0.5f * delta
    position.y += (destination.y - position.y) * 0.5f * delta

    spriteBatch.draw(sprite, position.x - 6, position.y - 6, 24, 24)
  }
}
