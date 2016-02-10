package galenscovell.oregontrail.processing.pathfinding;

import galenscovell.oregontrail.map.*;

import java.util.*;

public class Pathfinder {
    private List<Node> openList, closedList;
    private Node startNode, endNode;

    public List<Point> findPath(Tile startTile, Tile endTile, Tile[][] grid) {
        this.openList = new ArrayList<Node>();
        this.closedList = new ArrayList<Node>();
        this.startNode = new Node(startTile);
        this.endNode = new Node(endTile);

        startNode.setCostFromStart(0);
        startNode.setTotalCost(startNode.getCostFromStart() + heuristic(startNode, endNode));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = getBestNode();

            if (current.getTile() == endNode.getTile()) {
                endNode = current;
                return tracePath();
            }

            openList.remove(current);
            closedList.add(current);

            for (Point point : current.getTile().getNeighbors()) {
                Tile neighborTile = grid[point.y][point.x];

                if (neighborTile != null && (neighborTile == endTile || neighborTile.isEmpty())) {
                    Node neighborNode = new Node(neighborTile);

                    if (!inList(neighborTile, closedList)) {
                        neighborNode.setTotalCost(current.getCostFromStart() + heuristic(neighborNode, endNode));

                        if (!inList(neighborTile, openList)) {
                            neighborNode.setParent(current);
                            openList.add(neighborNode);
                        } else {
                            if (neighborNode.getCostFromStart() < current.getCostFromStart()) {
                                neighborNode.setCostFromStart(neighborNode.getCostFromStart());
                                neighborNode.setParent(neighborNode.getParent());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean inList(Tile nodeTile, List<Node> nodeList) {
        for (Node node : nodeList) {
            if (node.getTile() == nodeTile) {
                return true;
            }
        }
        return false;
    }

    private Node getBestNode() {
        double minCost = Double.POSITIVE_INFINITY;
        Node bestNode = null;

        for (Node node : openList) {
            double totalCost = node.getCostFromStart() + heuristic(node, endNode);
            if (minCost > totalCost) {
                minCost = totalCost;
                bestNode = node;
            }
        }
        return bestNode;
    }

    private double heuristic(Node start, Node end) {
        double dx = start.getTile().x - end.getTile().x;
        double dy = start.getTile().y - end.getTile().y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private List<Point> tracePath() {
        // Returns ordered list of points along movement path
        List<Point> path = new ArrayList<Point>();
        // Chase parent of node until start point reached
        Node node = endNode;
        while (node.getParent() != null) {
            path.add(new Point(node.getTile().x, node.getTile().y));
            node = node.getParent();
        }
        path.add(new Point(startNode.getTile().x, startNode.getTile().y));
        System.out.println(node.getTotalCost());
        return path;
    }
}
