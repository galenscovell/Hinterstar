package galenscovell.hinterstar.processing

import galenscovell.hinterstar.generation.interior.Tile


class Node(tile: Tile) {
  private var parent: Node = _
  private var costFromStart, totalCost: Double = _



  /********************
    *     Getters     *
    ********************/
  def getCostFromStart: Double = {
    costFromStart
  }

  def getTotalCost: Double = {
    totalCost
  }

  def getTile: Tile = {
    tile
  }

  def getParent: Node = {
    parent
  }



  /********************
    *     Setters     *
    ********************/
  def setCostFromStart(cost: Double): Unit = {
    costFromStart = cost
  }

  def setTotalCost(cost: Double): Unit = {
    totalCost = cost
  }

  def setParent(node: Node): Unit = {
    parent = node
  }
}
