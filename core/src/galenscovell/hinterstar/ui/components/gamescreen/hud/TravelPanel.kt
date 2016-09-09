package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import galenscovell.hinterstar.generation.sector.*
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util.*


class TravelPanel(private val interfaceStage: InterfaceStage) : Table() {
    lateinit private var distanceLabel: Label

    init {
        this.setFillParent(true)

        val topTable: Table = createTopTable()
        val mapTable: Table = createMapTable()
        val bottomTable: Table = createBottomTable()

        this.add(topTable).width(Constants.EXACT_X).height(48f)
        this.row()
        this.add(mapTable).width(Constants.EXACT_X).height(Constants.EXACT_Y - 96)
        this.row()
        this.add(bottomTable).width(Constants.EXACT_X).height(48f)
    }



    private fun createTopTable(): Table {
        val table: Table = Table()
        table.background = Resources.npTest1

        val closeButton: TextButton = TextButton("Close", Resources.blueButtonStyle)
        closeButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                interfaceStage.closeTravelPanel()
            }
        })
        table.add(closeButton).expand().width(96f).height(40f).right().padRight(10f)

        return table
    }

    private fun createMapTable(): Table {
        val mapTable: Table = Table()
        generateMap(mapTable)
        mapTable.background = Resources.npTest4
        return mapTable
    }

    private fun generateMap(container: Table): Unit {
        // TODO: Each new map has randomized SystemMarker layout (depending on difficulty)
        val sectorGenerator: SectorGenerator = SectorGenerator(16, 2)
        val systemMarkers: Array<Array<SystemMarker?>> = sectorGenerator.getSystemMarkers()

        // container.setDebug(true)
        for (row: Array<SystemMarker?> in systemMarkers) {
            for (systemMarker: SystemMarker? in row) {
                container.add(systemMarker!!).width(Constants.SYSTEMMARKER_SIZE).height(Constants.SYSTEMMARKER_SIZE)
            }
            container.row()
        }
    }

    private fun createBottomTable(): Table {
        val table: Table = Table()
        table.background = Resources.npTest1

        val travelButton: TextButton = TextButton("Warp", Resources.blueButtonStyle)
        travelButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                travelToSystem()
            }
        })
        distanceLabel = Label("Distance: 0 AU", Resources.labelLargeStyle)
        table.add(distanceLabel).expand().fill().left().padLeft(10f)
        table.add(travelButton).width(96f).height(40f).expand().fill().right().padRight(10f)

        return table
    }

    private fun travelToSystem(): Unit {
        if (SystemOperations.travelToSelection()) {
            interfaceStage.closeTravelPanel()
            interfaceStage.disableTravelButton()
            interfaceStage.getGameScreen().beginTravel()
            interfaceStage.hideUI()
        }
    }

    fun updateDistanceLabel(d: String): Unit {
        distanceLabel.setText(d)
    }
}
