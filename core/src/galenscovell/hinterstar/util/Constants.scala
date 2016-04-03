package galenscovell.hinterstar.util


object Constants {

  // Sector Types
  val SECTOR_EMPTY: Short = 0
  val SECTOR_CURRENT: Short = 1
  val SECTOR_EXPLORED: Short = 2
  val SECTOR_UNEXPLORED: Short = 3

  // State Types
  val ACTION_STATE: Short = 0
  val EVENT_STATE: Short = 1

  // Lighting masks
  val BIT_LIGHT: Short = 1
  val BIT_WALL: Short = 2
  val BIT_GROUP: Short = 5

  // Custom screen dimension units
  val SCREEN_X: Int = 200
  val SCREEN_Y: Int = 120

  // Exact pixel dimensions
  val EXACT_X: Int = 800
  val EXACT_Y: Int = 480

  // Map dimensions (1 Tile = 1 AU)
  val MAPWIDTH: Int = 50
  val MAPHEIGHT: Int = 28
  val SECTORSIZE: Int = 16

  // Distance Units
  // ~8min to travel one AU at speed of light
  // From Sun to Uranus: 19.2 AU
  // From Sun to Neptune: 30 AU
  // From Sun to Pluto: 39.4 AU
  // To nearest star (Proxima Centauri): 271,000 AU
  val SPEED_OF_LIGHT: Double = 1.86 * Math.pow(10, 3)     // miles per second
  val ASTRONOMICAL_UNIT: Double = 9.29 * Math.pow(10, 7)  // in miles
}
