package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.*
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util.*


class ShipStatsPanel(interfaceStage: InterfaceStage) : Table() {
    private val shipStatsTable: Table = Table()
    private val statIcons: List<Image> = listOf(
        Image(Sprite(Resources.atlas.createSprite("icon_shield"))),
        Image(Sprite(Resources.atlas.createSprite("icon_engine")))
    )

    init {
        this.add(shipStatsTable).expand().left().padLeft(4f)
        refresh()
    }



    fun refresh(): Unit {
        shipStatsTable.clear()

        val stats: FloatArray = PlayerData.getShipStats()

        for (i in stats.indices) {
            val statTable: Table = Table()
            statTable.background = Resources.npTest4

            val statKeyTable: Table = Table()
            statKeyTable.add(statIcons[i]).expand().fill().width(32f).height(32f).center().left().pad(4f)

            val statValueTable: Table = Table()
            val statValueLabel: Label = Label(stats[i].toString(), Resources.labelTinyStyle)
            statValueTable.add(statValueLabel).expand().center().right().pad(4f)

            statTable.add(statKeyTable).expand().left()
            statTable.add(statValueTable).expand().fill().center()

            shipStatsTable.add(statTable).width(80f).height(32f).center().padRight(4f)
        }
    }
}
