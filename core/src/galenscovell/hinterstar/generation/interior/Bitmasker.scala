package galenscovell.hinterstar.generation.interior


/**
  *
  *    1         1       Total = (Sum of occupied values)
  *  8 * 2       * 2     ex total = (1 + 2) = 3
  *    4
  * Bitmask value range: 0, 15 (None occupied, all occupied)
  * Bitmask value determines sprite of Tile.
  */
object Bitmasker {

  def find(tile: Tile): Int = {
    var value: Int = 0
    val neighbors: Array[Tile] = tile.getNeighbors

    for (neighbor: Tile <- neighbors) {
      if (neighbor.isTraversible) {
        val dx: Int = tile.tx - neighbor.tx
        val dy: Int = tile.ty - neighbor.ty

        if (dx == -1 && dy == 0) {
          value += 2
        } else if (dx == 0) {
          if (dy == -1) {
            value += 4
          } else if (dy == 1) {
            value += 1
          }
        } else if (dx == 1 && dy == 0) {
          value += 8
        }
      }
    }
    value
  }
}