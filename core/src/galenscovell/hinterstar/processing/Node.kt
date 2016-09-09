package galenscovell.hinterstar.processing

import galenscovell.hinterstar.generation.interior.Tile


class Node(private val tile: Tile) {
    private var parent: Node? = null
    private var costFromStart: Double = 0.0
    private var totalCost: Double = 0.0



    /********************
     *     Getters     *
     ********************/
    fun getCostFromStart(): Double {
        return costFromStart
    }

    fun getTotalCost(): Double {
        return totalCost
    }

    fun getTile(): Tile {
        return tile
    }

    fun getParent(): Node? {
        return parent
    }



    /********************
     *     Setters     *
     ********************/
    fun setCostFromStart(cost: Double): Unit {
        costFromStart = cost
    }

    fun setTotalCost(cost: Double): Unit {
        totalCost = cost
    }

    fun setParent(node: Node): Unit {
        parent = node
    }
}