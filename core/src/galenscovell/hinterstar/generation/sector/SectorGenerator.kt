package galenscovell.hinterstar.generation.sector

import galenscovell.hinterstar.util.*


class SectorGenerator(private val maxSystems: Int, private val padSize: Int) {
    private val systemMarkers: Array<Array<SystemMarker?>> = Array(Constants.MAP_HEIGHT, {Array<SystemMarker?>(Constants.MAP_WIDTH, {i -> null})})
    private val systems: MutableList<System> = mutableListOf()

    init {
        build()
        placeSystems()
        SystemOperations.populateSystems(systems)
    }



    fun getSystemMarkers(): Array<Array<SystemMarker?>> {
        return systemMarkers
    }

    private fun build(): Unit {
        for (x in 0 until Constants.MAP_WIDTH) {
            for (y in 0 until Constants.MAP_HEIGHT) {
                systemMarkers[y][x] = SystemMarker(x, y)
            }
        }

    }

    private fun placeSystems(): Unit {
        var attempts: Int = 240

        while (attempts > 0 && systems.size < maxSystems) {
            val x: Int = getRandom(1, Constants.MAP_WIDTH - padSize - 1)
            val y: Int = getRandom(1, Constants.MAP_HEIGHT - padSize - 1)
            if (!(x == 0 || x == Constants.MAP_WIDTH || y == 0 || y == Constants.MAP_HEIGHT)) {
                val system: System = System(x, y, padSize)

                if (!doesCollide(system)) {
                    val centerX: Int = (system.size / 2) + system.x
                    val centerY: Int = (system.size / 2) + system.y
                    val systemMarker: SystemMarker = systemMarkers[centerY][centerX]!!
                    system.setSystemMarker(systemMarker)
                    systemMarker.setSystem(system)
                    systems.add(system)
                }
            }
            attempts -= 1
        }
    }

    private fun doesCollide(system: System): Boolean {
        for (i in systems.indices) {
            val check: System = systems[i]

            if (!((system.x + system.size < check.x - 2) ||
                    (system.x - 2 > check.x + check.size) ||
                    (system.y + system.size < check.y - 2) ||
                    (system.y - 2 > check.y + check.size))) {
                return true
            }
        }
        return false
    }

    private fun getRandom(low: Int, high: Int): Int {
        return (Math.random() * (high - low)).toInt() + low
    }

    private fun debugPrint(): Unit {
        println()
        for (row: Array<SystemMarker?> in systemMarkers) {
            for (sm: SystemMarker? in row) {
                print(sm!!.debugDraw())
            }
            println()
        }
        println()
    }
}

