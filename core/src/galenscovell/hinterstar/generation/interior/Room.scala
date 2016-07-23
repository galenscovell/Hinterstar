package galenscovell.hinterstar.generation.interior

import scala.util.Random


class Room(topLeftX: Int, topLeftY: Int, w: Int, h: Int) {
  val x: Int = topLeftX
  val y: Int = topLeftY
  var width: Int = w
  var height: Int = h
  var leftChild, rightChild: Room = _

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


  def split(): Boolean = {
    val random: Random = new Random()
    val minSize: Int = 9

    // Already split
    if (leftChild != null || rightChild != null) {
      return false
    }

    // Direction of split
    var horizontal: Boolean = random.nextBoolean()
    if (width > height && width / height >= 1.25) {
      horizontal = false
    } else if (height > width && height / width >= 1.25) {
      horizontal = true
    }

    // Max height/width to split off
    val maxSize: Int = {
      if (horizontal) {
        height - minSize
      } else {
        width - minSize
      }
    }

    // Area too small
    if (maxSize < minSize) {
      return false
    }

    // Generate split point
    val split: Int = minSize + random.nextInt((maxSize - minSize) + 1)
    if (split < minSize) {
      return false
    }

    if (horizontal) {
      // Top horizontal child
      leftChild = new Room(x, y, width, split)
      // Bottom horizontal child
      rightChild = new Room(x, y + split, width, height - split)
    } else {
      // Left vertical child
      leftChild = new Room(x, y, split, height)
      // Right vertical child
      rightChild = new Room(x + split, y, width - split, height)
    }

    // Split successful
    true
  }
}
