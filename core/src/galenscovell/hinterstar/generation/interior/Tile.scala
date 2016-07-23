package galenscovell.hinterstar.generation.interior

import galenscovell.hinterstar.generation.interior.TileType.TileType


class Tile(gridX: Int, gridY: Int) {
  val x: Int = gridX
  val y: Int = gridY
  var tileType: TileType = TileType.EMPTY



  def becomeEmpty(): Unit = {
    tileType = TileType.EMPTY
  }

  def becomeFloor(): Unit = {
    tileType = TileType.FLOOR
  }

  def becomeWall(): Unit = {
    tileType = TileType.WALL
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



  def draw(): String = {
    tileType match {
      case TileType.EMPTY => "~"
      case TileType.FLOOR => " "
      case TileType.WALL => "#"
      case _ => "!"
    }
  }
}
