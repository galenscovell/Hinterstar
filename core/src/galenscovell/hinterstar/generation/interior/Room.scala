package galenscovell.hinterstar.generation.interior

import scala.util.Random


class Room(topLeftX: Int, topLeftY: Int, w: Int, h: Int, p: Room) {
  val x: Int = topLeftX
  val y: Int = topLeftY
  val width: Int = w
  val height: Int = h
  val centerX: Int = topLeftX + (width / 2)
  val centerY: Int = topLeftY + (height / 2)

  val parent: Room = p
  var sister, leftChild, rightChild: Room = _
  var connected: Boolean = false

  var tiles: Array[Tile] = _
  var subsystem: String = _


  def setTiles(tiles: Array[Tile]): Unit = {
    this.tiles = tiles
  }

  def setSister(r: Room): Unit = {
    sister = r
  }

  def isConnected(): Boolean = {
    connected
  }

  def setConnected(): Unit = {
    connected = true
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
      leftChild = new Room(x, y, width, split, this)
      // Bottom horizontal child
      rightChild = new Room(x, y + split, width, height - split, this)
    } else {
      // Left vertical child
      leftChild = new Room(x, y, split, height, this)
      // Right vertical child
      rightChild = new Room(x + split, y, width - split, height, this)
    }

    leftChild.setSister(rightChild)
    rightChild.setSister(leftChild)

    // Split successful
    true
  }
}
