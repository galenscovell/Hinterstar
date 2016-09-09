package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.generation.interior.Tile



class InteriorOverlay(ship: Ship) : Table() {
    private val interiorParser: InteriorParser = InteriorParser(ship)
    private val tiles: List<List<Tile>> = setup()
    private val subsystems: MutableMap<String, Tile> = createSubsystemMap()



    private fun setup(): List<List<Tile>> {
        interiorParser.parse()
        val t: List<List<Tile>> = interiorParser.getTiles()

        // this.setDebug(true)
        for (row: List<Tile> in t) {
            for (tile: Tile in row) {
                this.add(tile).width(interiorParser.tileSize.toFloat()).height(interiorParser.tileSize.toFloat())
            }
            this.row()
        }

        return t
    }

    private fun createSubsystemMap(): MutableMap<String, Tile> {
        val subsystemMap: MutableMap<String, Tile> = mutableMapOf()
        for (row: List<Tile> in tiles) {
            for (tile: Tile in row) {
                if (tile.isSubsystem()) {
                    subsystemMap.put(tile.getSubsystemName(), tile)
                }
            }
        }
        return subsystemMap
    }

    fun getSubsystemMap(): MutableMap<String, Tile> {
        return subsystems
    }
}
