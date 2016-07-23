package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.Color


/**
  * Stores values used throughout application numerous times.
  */
object Constants {

  // SystemMarker Types
  val SYSTEMMARKER_EMPTY: Short = 0
  val SYSTEMMARKER_CURRENT: Short = 1
  val SYSTEMMARKER_EXPLORED: Short = 2
  val SYSTEMMARKER_UNEXPLORED: Short = 3

  // Lighting masks
  val BIT_LIGHT: Short = 1
  val BIT_WALL: Short = 2
  val BIT_GROUP: Short = 5

  // Exact pixel dimensions
  val EXACT_X: Int = 800
  val EXACT_Y: Int = 480

  // Ship interior dimensions
  val TILE_SIZE: Int = 24

  // Custom screen dimension units
  val SCREEN_X: Int = 200
  val SCREEN_Y: Int = 120

  // Map dimensions (1 Tile = 1 AU)
  val MAP_WIDTH: Int = 50
  val MAP_HEIGHT: Int = 26
  val SYSTEMMARKER_SIZE: Int = 16

  // Distance Units
  // ~8min to travel one AU at speed of light
  // From Sun to Uranus: 19.2 AU
  // From Sun to Neptune: 30 AU
  // From Sun to Pluto: 39.4 AU
  // To nearest star (Proxima Centauri): 271,000 AU
  val SPEED_OF_LIGHT: Double = 1.86 * Math.pow(10, 3)     // miles per second
  val ASTRONOMICAL_UNIT: Double = 9.29 * Math.pow(10, 7)  // in miles

  // UI Colors
  val NORMAL_UI_COLOR: Color = new Color(0.5f, 0.5f, 0.5f, 1.0f)
  val FLASH_UI_COLOR: Color = new Color(1.0f, 1.0f, 1.0f, 1.0f)
}
