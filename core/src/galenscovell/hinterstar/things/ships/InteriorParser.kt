package galenscovell.hinterstar.things.ships

import com.badlogic.gdx.Gdx
import galenscovell.hinterstar.generation.interior.Tile
import java.io.BufferedReader


class InteriorParser(private val ship: Ship) {
    private val targetShipName: String = when (ship.isPlayerShip()) {
        true -> ship.getName()
        else -> ship.getName() + "-reversed"
    }

    private val tiles: MutableList<List<Tile>> = mutableListOf()
    private var width: Int = 0
    private var height: Int = 0
    var tileSize: Int = 0



    fun getTiles(): List<List<Tile>> {
        return tiles.toList()
    }

    fun parse(): Unit {
        val reader: BufferedReader = Gdx.files.internal("data/ship_interiors.txt").reader(1024)

        var shipFound: Boolean = false
        var searching: Boolean = true
        var y = 0

        var line: String? = reader.readLine()
        while (line != null && searching) {
            if (shipFound && line == "END") {
                searching = false
            } else if (shipFound) {
                val subsystemRow: MutableList<Tile> = mutableListOf()

                for (x in 0 until line.length) {
                    val createdTile: Tile = when (line[x]) {
                        'W' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "Weapon Control", true, ship.isPlayerShip(), true)
                        'E' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "Engine Room", false, ship.isPlayerShip(), true)
                        'H' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "Helm", false, ship.isPlayerShip(), true)
                        'S' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "Shield Control", false, ship.isPlayerShip(), true)
                        'M' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "Medbay", false, ship.isPlayerShip(), true)
                        '-' -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "none", false, ship.isPlayerShip(), true)
                        else -> Tile(x.toFloat(), y.toFloat(), tileSize.toFloat(), height, "none", false, ship.isPlayerShip(), false)
                    }
                    subsystemRow.add(createdTile)
                }

                tiles.add(subsystemRow.toList())
                y += 1
            }

            if (line == targetShipName) {
                shipFound = true
                val dimLine: String = reader.readLine()
                val splitLine: List<String> = dimLine.split(",")
                width = Integer.valueOf(splitLine[0])
                height = Integer.valueOf(splitLine[1])
                tileSize = Integer.valueOf(splitLine[2])
            }

            line = reader.readLine()
        }
        reader.close()

        setTileNeighbors()
    }

    private fun setTileNeighbors(): Unit {
        for (row: List<Tile> in getTiles()) {
            for (tile: Tile in row) {
                val neighbors: MutableList<Tile> = mutableListOf()

                for (dx in -1..1) {
                    val newX: Int = tile.getTx().toInt() + dx
                    if (!isOutOfBounds(newX, tile.getTy().toInt())) {
                        val neighbor: Tile = tiles[tile.getTy().toInt()][newX]
                        neighbors.add(neighbor)
                    }
                }

                for (dy in -1..1) {
                    val newY: Int = tile.getTy().toInt() + dy
                    if (!isOutOfBounds(tile.getTx().toInt(), newY)) {
                        val neighbor: Tile = tiles[newY][tile.getTx().toInt()]
                        neighbors.add(neighbor)
                    }
                }

                tile.setNeighbors(neighbors.toList())
            }
        }
    }

    private fun isOutOfBounds(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= width || y >= height)
    }
}
