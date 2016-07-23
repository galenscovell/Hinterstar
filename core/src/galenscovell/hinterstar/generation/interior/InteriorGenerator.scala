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
    val rootRoom: Room = new Room(0, 0, columns, rows)
    partitions.append(rootRoom)

    var splitAmount: Int = 0
    while (splitAmount < desiredSplits) {
      val chosenPartition: Int = random.nextInt(partitions.size)
      val toSplit: Room = partitions(chosenPartition)
      if (toSplit.split()) {
        partitions.append(toSplit.leftChild)
        partitions.append(toSplit.rightChild)
        splitAmount += 1
      }
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

  private def isOutOfBounds(x: Int, y: Int): Boolean = {
    x < 0 || y < 0 || x >= columns || y >= rows
  }

  private def debugPrint(): Unit = {
    for (row: Array[Tile] <- tiles) {
      println()
      for (tile: Tile <- row) {
        print(tile.draw())
      }
    }
  }
}
