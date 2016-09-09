package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.*
import galenscovell.hinterstar.util.Resources


class SubsystemInfo(private val name: String, private val subsystemIcon: Sprite, occupancy: Int,
                    private val rootX: Float, private val rootY: Float) {
    private val maxOccupancy: Int = occupancy
    private var currentOccupancy: Int = 0
    private var occupancySprite: Sprite? = null

    init {
        updateOccupancy(0)
    }


    fun updateOccupancy(amount: Int): Unit {
        currentOccupancy += amount
        val spriteName: String = "$currentOccupancy of $maxOccupancy"
        occupancySprite = Sprite(Resources.atlas!!.createSprite(spriteName))
    }

    fun draw(batch: Batch, parentAlpha: Float): Unit {
        batch.draw(subsystemIcon, rootX - 8, rootY + 8, 32f, 32f)
        batch.draw(occupancySprite, rootX + 32, rootY + 8, 32f, 32f)
    }
}
