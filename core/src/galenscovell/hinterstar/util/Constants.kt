package galenscovell.hinterstar.util

import com.badlogic.gdx.graphics.Color


object Constants {

    // Lighting masks
    const val BIT_LIGHT: Short = 1
    const val BIT_WALL: Short = 2
    const val BIT_GROUP: Short = 5

    // Exact pixel dimensions
    const val EXACT_X: Float = 800f
    const val EXACT_Y: Float = 480f

    // Custom screen dimension units
    const val SCREEN_X: Float = 200f
    const val SCREEN_Y: Float = 120f

    // Map dimensions
    const val MAP_WIDTH: Int = 31
    const val MAP_HEIGHT: Int = 15
    const val SYSTEMMARKER_SIZE: Float = 24f
    const val SYSTEM_MARKER_X: Float = SYSTEMMARKER_SIZE + 4
    const val SYSTEM_MARKER_Y: Float = -SYSTEMMARKER_SIZE * 4 + (SYSTEMMARKER_SIZE / 2)
    const val SYSTEM_MARKER_CENTER_X: Float = SYSTEMMARKER_SIZE + (SYSTEMMARKER_SIZE / 2) + 4
    const val SYSTEM_MARKER_CENTER_Y: Float = -SYSTEMMARKER_SIZE * 3

    // UI Colors
    val NORMAL_UI_COLOR: Color = Color(0.5f, 0.5f, 0.5f, 1.0f)
    val FLASH_UI_COLOR: Color = Color(1.0f, 1.0f, 1.0f, 1.0f)
}