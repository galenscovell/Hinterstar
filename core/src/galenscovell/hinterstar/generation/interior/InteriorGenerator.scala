package galenscovell.hinterstar.generation.interior

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class InteriorGenerator(width: Int, height: Int, numberOfSplits: Int) {
  val rows: Int = width
  val columns: Int = height
  val desiredSplits: Int = numberOfSplits

  val tiles: Array[Array[Tile]] = Array.ofDim[Tile](columns, rows)
  val partitions: ArrayBuffer[Room] = ArrayBuffer()
  val rooms: ArrayBuffer[Room] = ArrayBuffer()

  val random: Random = new Random()
  var valid: Boolean = false

  build()


  def getTiles(): Array[Array[Tile]] = {
    tiles
  }

  private def build(): Unit = {
    while (!valid) {
      partitions.clear()
      rooms.clear()

      // Construct Tile[COLUMNS][ROWS] grid of all TileType.EMPTY
      for (x <- 0 until rows) {
        for (y <- 0 until columns) {
          tiles(y)(x) = new Tile(x, y)
        }
      }
      setNeighbors()
      partition(numberOfSplits)
      connectRooms(partitions(0))
      valid = check()
    }

    // debugPrint()

    val bitmasker: Bitmasker = new Bitmasker
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        tile.setBitmask(bitmasker.find(tile))
        tile.setSprite()
      }
    }
  }

  private def check(): Boolean = {
    // Ensures that generated interior is valid
    // (Has doors placed in valid locations)
    var valid: Boolean = true
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        if (tile.isDoor()) {
          val doorNeighbors: Int = tile.getDoorNeighbors()
          if (doorNeighbors > 0) {
            valid = false
          }
        }
      }
    }
    valid
  }

  private def partition(splits: Int): Unit = {
    // Binary space partitioning
    val rootRoom: Room = new Room(0, 0, columns, rows, null)
    partitions.append(rootRoom)

    var attempts: Int = 300
    var splitAmount: Int = 0
    while (splitAmount < desiredSplits && attempts > 0) {
      val chosenPartition: Int = random.nextInt(partitions.size)
      val toSplit: Room = partitions(chosenPartition)
      if (toSplit.split(random)) {
        partitions.append(toSplit.leftChild)
        partitions.append(toSplit.rightChild)
        splitAmount += 1
      }
      attempts -= 1
    }
    generateInterior(rootRoom)
  }

  private def generateInterior(room: Room): Unit = {
    // Create interior within lowest children
    if (room.leftChild == null) {
      rooms.append(room)
      createRoom(room)
    } else {
      generateInterior(room.leftChild)
      generateInterior(room.rightChild)
    }
  }

  private def createRoom(room: Room): Unit = {
    // Create floor tiled room filling room dimensions
    val roomTiles: ArrayBuffer[Tile] = ArrayBuffer()
    val bottomRightX: Int = room.topLeftY + room.height
    val bottomRightY: Int = room.topLeftX + room.width

    for (x <- room.topLeftY until bottomRightX) {
      for (y <- room.topLeftX until bottomRightY) {
        val tile: Tile = tiles(y)(x)

        if (x == room.topLeftY || y == room.topLeftX) {
          // Check if x and y are top left perimeter of room
          tile.becomeWall()
        } else if (x == (bottomRightX - 1) && y == (bottomRightY - 1)) {
          // Check if x and y are bottom right perimeter of room
          if (isOutOfBounds(bottomRightX, bottomRightY)) {
            tile.becomeWall()
          } else {
            tile.becomeFloor()
          }
        } else if (x == (bottomRightX - 1)) {
          // Check if x is right perimeter of room
          if (isOutOfBounds(bottomRightX, 0)) {
            tile.becomeWall()
          } else {
            tile.becomeFloor()
          }
        } else if (y == (bottomRightY - 1)) {
          // Check if y is bottom perimeter of room
          if (isOutOfBounds(0, bottomRightY)) {
            tile.becomeWall()
          } else {
            tile.becomeFloor()
          }
        } else {
          // Otherwise just make it a floor tile
          tile.becomeFloor()
        }

        roomTiles.append(tile)
      }
    }
    room.setTiles(roomTiles.toArray)
  }

  private def connectRooms(rootRoom: Room): Unit = {
    // Recursively connect each rooms children to each other with hallways
    if (rootRoom != null) {
      if (rootRoom.leftChild != null) {
        connectRooms(rootRoom.leftChild)
      }
      if (rootRoom.rightChild != null) {
        connectRooms(rootRoom.rightChild)
      }
    }

    if (rootRoom.sister != null && !rootRoom.isConnected()) {
      createHall(rootRoom, rootRoom.sister)
    }
  }

  private def createHall(r1: Room, r2: Room): Unit = {
    // Create hall between centerpoints of a pair of rooms
    val xDelta: Int = r1.centerX - r2.centerX
    val yDelta: Int = r1.centerY - r2.centerY

    if (xDelta == 0) {
      for (y <- r1.centerY to r2.centerY) {
        val tile: Tile = tiles(r1.centerX)(y)

        if (tile.isWall()) {
          tile.becomeDoor()
        }
      }
    } else if (yDelta == 0) {
      for (x <- r1.centerX to r2.centerX) {
        val tile: Tile = tiles(x)(r1.centerY)

        if (tile.isWall()) {
          tile.becomeDoor()
        }
      }
    }

    r1.setConnected()
    r2.setConnected()
  }

  private def setNeighbors(): Unit = {
    // Set each tiles neighboring points
    for (row: Array[Tile] <- tiles) {
      for (tile: Tile <- row) {
        val neighbors: ArrayBuffer[Tile] = ArrayBuffer()

        for (dx <- -1 to 1) {
          for (dy <- -1 to 1) {
            val testX: Int = tile.sx + dx
            val testY: Int = tile.sy + dy

            if (!(testX == tile.sx && testY == tile.sy) && !isOutOfBounds(testX, testY)) {
              neighbors.append(tiles(testY)(testX))
            }
          }
        }
        tile.setNeighbors(neighbors.toArray)
      }
    }
  }

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || y < 0 || x >= rows || y >= columns
  }

  private def debugPrint(): Unit = {
    for (row: Array[Tile] <- tiles) {
      println()
      for (tile: Tile <- row) {
        print(tile.debugDraw())
      }
    }
  }
}
