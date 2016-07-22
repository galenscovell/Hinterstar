package galenscovell.hinterstar.generation.interior

import scala.collection.mutable.ArrayBuffer


class InteriorGenerator(width: Int, height: Int, numberOfRooms: Int) {
  val rows: Int = width
  val columns: Int = height
  val roomCount: Int = numberOfRooms

  var tiles: Array[Array[Tile]] = Array.ofDim[Tile](columns, rows)
  var rooms: ArrayBuffer[Room] = ArrayBuffer()

  build()
  debugPrint()


  private def build(): Unit = {
    // Construct Tile[WIDTH][HEIGHT] grid of all TileType.EMPTY
    for (row <- 0 until rows) {
      for (col <- 0 until columns) {
        tiles(col)(row) = new Tile(col, row)
      }
    }

    placeRooms()
    squashRooms()

    // Set all Tiles within each Room as TileType.FLOOR
    for (i <- 0 until roomCount) {
      val roomTiles: ArrayBuffer[Tile] = ArrayBuffer()
      val room: Room = rooms(i)
      for (row <- room.y until (room.y + room.height)) {
        for (col <- room.x until (room.x + room.width)) {
          tiles(col)(row).becomeFloor()
          roomTiles.append(tiles(col)(row))
        }
      }
      room.setTiles(roomTiles.toArray)
    }
  }

  private def placeRooms(): Unit = {
    // Place random Rooms, ensuring that they do not collide
    // Minus one from width and height at end so rooms are separated
    val roomSize: Int = 6
    for (i <- 0 until roomCount) {
      val x: Int = getRandom(1, columns - roomSize - 1)
      val y: Int = getRandom(1, rows - roomSize - 1)
      val dim: Int = roomSize
      val newRoom: Room = new Room(x, y, dim, dim)
      if (!doesCollide(newRoom)) {
        newRoom.width -= 1
        newRoom.height -= 1
        rooms.append(newRoom)
      }

    }
  }

  private def doesCollide(room: Room): Boolean = {
    // Return if target Room overlaps already placed Room
    for (i <- rooms.indices) {
      val check: Room = rooms(i)
      !((room.x + room.width < check.x - 2) ||
        (room.x - 2 > check.x + check.width) ||
        (room.y + room.height < check.y - 2) ||
        (room.y - 2 > check.y + check.height))
    }
    false
  }

  private def squashRooms(): Unit = {

  }

  private def getRandom(lo: Int, hi: Int): Int = {
    ((Math.random() * (hi - lo)) + lo).toInt
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
