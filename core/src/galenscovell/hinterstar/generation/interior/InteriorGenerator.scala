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

  build()
  partition(numberOfSplits)
  debugPrint()
  connectRooms(partitions(0))
  debugPrint()


  private def build(): Unit = {
    // Construct Tile[ROWS][COLUMNS] grid of all TileType.EMPTY
    for (row <- 0 until rows) {
      for (col <- 0 until columns) {
        tiles(col)(row) = new Tile(col, row)
      }
    }
  }

  private def partition(splits: Int): Unit = {
    // Binary space partitioning
    val random: Random = new Random()
    val rootRoom: Room = new Room(0, 0, columns, rows, null)
    partitions.append(rootRoom)

    var attempts: Int = 300
    var splitAmount: Int = 0
    while (splitAmount < desiredSplits && attempts > 0) {
      val chosenPartition: Int = random.nextInt(partitions.size)
      val toSplit: Room = partitions(chosenPartition)
      if (toSplit.split()) {
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
    for (row <- room.y until (room.y + room.height)) {
      for (col <- room.x until (room.x + room.width)) {
        if (row == room.y || col == room.x ||
            row == (room.y + room.height - 1) ||
            col == (room.x + room.width - 1)) {
          tiles(col)(row).becomeWall()
        } else {
          tiles(col)(row).becomeFloor()
        }
        roomTiles.append(tiles(col)(row))
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
        tiles(r1.centerX)(y).becomeFloor()
      }
    } else if (yDelta == 0) {
      for (x <- r1.centerX to r2.centerX) {
        tiles(x)(r1.centerY).becomeFloor()
      }
    }

    r1.setConnected()
    r2.setConnected()
  }

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || y < 0 || x >= columns || y >= rows
  }

  private def debugPrint(): Unit = {
    println()
    for (row: Array[Tile] <- tiles) {
      println()
      for (tile: Tile <- row) {
        print(tile.draw())
      }
    }
  }
}
