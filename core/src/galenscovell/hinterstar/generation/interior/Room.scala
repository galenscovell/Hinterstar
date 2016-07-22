package galenscovell.hinterstar.generation.interior


class Room(topLeftX: Int, topLeftY: Int, w: Int, h: Int) {
  val x: Int = topLeftX
  val y: Int = topLeftY
  var width: Int = w
  var height: Int = h

  var tiles: Array[Tile] = _
  var subsystem: String = _


  def setTiles(tiles: Array[Tile]): Unit = {
    this.tiles = tiles
  }

  def setSubsystem(subsystem: String): Unit = {
    this.subsystem = subsystem
  }

  def getSubsystem(): String = {
    subsystem
  }
}
