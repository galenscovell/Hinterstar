package galenscovell.hinterstar.util

import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.processing.Node

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


object Pathfinder {
  private val openList: ArrayBuffer[Node] = ArrayBuffer()
  private val closedList: ArrayBuffer[Node] = ArrayBuffer()



  def findPath(startTile: Tile, endTile: Tile): mutable.Stack[Tile] = {
    openList.clear()
    closedList.clear()

    val startNode: Node = new Node(startTile)
    val endNode: Node = new Node(endTile)

    startNode.setCostFromStart(0)
    startNode.setTotalCost(startNode.getCostFromStart + heuristic(startNode, endNode))
    openList.append(startNode)

    while (openList.nonEmpty) {
      val current: Node = getBestNode(endNode)

      if (current.getTile == endNode.getTile) {
        return tracePath(current)
      }

      openList.remove(openList.indexOf(current))
      closedList.append(current)

      for (neighborTile: Tile <- current.getTile.getNeighbors) {
        if (neighborTile.isTraversible) {
          val neighborNode: Node = new Node(neighborTile)

          if (!inList(neighborTile, closedList)) {
            neighborNode.setTotalCost(current.getCostFromStart + heuristic(neighborNode, endNode))

            if (!inList(neighborTile, openList)) {
              neighborNode.setParent(current)
              openList.append(neighborNode)
            } else {
              if (neighborNode.getCostFromStart < current.getCostFromStart) {
                neighborNode.setCostFromStart(neighborNode.getCostFromStart)
                neighborNode.setParent(neighborNode.getParent)
              }
            }
          }
        }
      }
    }
    null
  }

  private def heuristic(start: Node, end: Node): Double = {
    val xs: Double = (start.getTile.getTx - end.getTile.getTx) * (start.getTile.getTx - end.getTile.getTx)
    val ys: Double = (start.getTile.getTy - end.getTile.getTy) * (start.getTile.getTy - end.getTile.getTy)
    Math.sqrt(xs + ys)
  }

  private def inList(nodeTile: Tile, nodeList: ArrayBuffer[Node]): Boolean = {
    for (n: Node <- nodeList) {
      if (n.getTile == nodeTile) {
        return true
      }
    }

    false
  }

  private def getBestNode(endNode: Node): Node = {
    var minCost: Double = Double.PositiveInfinity
    var bestNode: Node = null

    for (n: Node <- openList) {
      val totalCost: Double = n.getCostFromStart + heuristic(n, endNode)
      if (minCost > totalCost) {
        minCost = totalCost
        bestNode = n
      }
    }

    bestNode
  }

  private def tracePath(n: Node): mutable.Stack[Tile] = {
    val path: mutable.Stack[Tile] = mutable.Stack()
    var node: Node = n

    while (node.getParent != null) {
      path.push(node.getTile)
      node = node.getParent
    }

    path
  }
}
