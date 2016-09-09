package galenscovell.hinterstar.processing

import galenscovell.hinterstar.generation.interior.Tile


class Pathfinder {
    private val openList: MutableList<Node> = mutableListOf()
    private val closedList: MutableList<Node> = mutableListOf()



    fun findPath(startTile: Tile, endTile: Tile): List<Tile>? {
        openList.clear()
        closedList.clear()

        val startNode: Node = Node(startTile)
        val endNode: Node = Node(endTile)

        startNode.setCostFromStart(0.0)
        startNode.setTotalCost(startNode.getCostFromStart() + heuristic(startNode, endNode))
        openList.add(startNode)

        while (openList.isNotEmpty()) {
            val current: Node = getBestNode(endNode)

            if (current.getTile() == endNode.getTile()) {
                return tracePath(current)
            }

            openList.remove(current)
            closedList.remove(current)

            for (neighborTile: Tile in current.getTile().getNeighbors()) {
                if (neighborTile.isTraversible()) {
                    val neighborNode: Node = Node(neighborTile)

                    if (!inList(neighborTile, closedList)) {
                        neighborNode.setTotalCost(current.getCostFromStart() + heuristic(neighborNode, endNode))

                        if (!inList(neighborTile, openList)) {
                            neighborNode.setParent(current)
                            openList.add(neighborNode)
                        } else {
                            if (neighborNode.getCostFromStart() < current.getCostFromStart()) {
                                neighborNode.setCostFromStart(neighborNode.getCostFromStart())
                                neighborNode.setParent(neighborNode.getParent()!!)
                            }
                        }
                    }
                }
            }
        }
        return null
    }

    private fun heuristic(start: Node, end: Node): Double {
        val xs: Double = (start.getTile().getTx() - end.getTile().getTx()).toDouble() *
                (start.getTile().getTx() - end.getTile().getTx())
        val ys: Double = (start.getTile().getTy() - end.getTile().getTy()).toDouble() *
                (start.getTile().getTy() - end.getTile().getTy())
        return Math.sqrt(xs + ys)
    }

    private fun inList(nodeTile: Tile, nodeList: MutableList<Node>): Boolean {
        for (n: Node in nodeList) {
            if (n.getTile() == nodeTile) {
                return true
            }
        }

        return false
    }

    private fun getBestNode(endNode: Node): Node {
        var minCost: Double = Double.POSITIVE_INFINITY
        var bestNode: Node = endNode

        for (n: Node in openList) {
            val totalCost: Double = n.getCostFromStart() + heuristic(n, endNode)
            if (minCost > totalCost) {
                minCost = totalCost
                bestNode = n
            }
        }

        return bestNode
    }

    private fun tracePath(n: Node): List<Tile> {
        val path: MutableList<Tile> = mutableListOf()
        var node: Node? = n

        while (node != null) {
            path.add(node.getTile())
            node = node.getParent()
        }

        return path.toList().reversed()
    }
}
