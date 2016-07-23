package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.Actor
import galenscovell.hinterstar.generation.interior.TileType.TileType
import galenscovell.hinterstar.util._


class Tile(x: Int, y: Int) extends Actor {
  val sx: Int = x
  val sy: Int = y
  var tileType: TileType = TileType.EMPTY
  private var sprite: Sprite = _



  def becomeEmpty(): Unit = {
    tileType = TileType.EMPTY
    sprite = Resources.spTest0
  }

  def becomeFloor(): Unit = {
    tileType = TileType.FLOOR
    sprite = Resources.spTest1
  }

  def becomeWall(): Unit = {
    tileType = TileType.WALL
    sprite = Resources.spTest2
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



  def debugDraw(): String = {
    tileType match {
      case TileType.EMPTY => "+"
      case TileType.FLOOR => " "
      case TileType.WALL => "#"
      case _ => "!"
    }
  }


  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(
      sprite,
      sx * Constants.TILE_SIZE + (1.5f * Constants.TILE_SIZE) + 4,
      Gdx.graphics.getHeight - (sy * Constants.TILE_SIZE) - (3 * Constants.TILE_SIZE),
      Constants.TILE_SIZE,
      Constants.TILE_SIZE
    )
  }
}
