package galenscovell.oregontrail.processing.pathfinding;

import galenscovell.oregontrail.map.*;

import java.util.*;

public class Pathfinder {
    private final Tile[][] grid;
    private List<Node> openList, closedList;
    private Node startNode, endNode;
    private final boolean diagonal = true;

    public Pathfinder(Tile startTile, Tile endTile, Tile[][] grid) {
        this.grid = grid;
        this.openList = new ArrayList<Node>();
        this.closedList = new ArrayList<Node>();
        this.startNode = new Node(startTile);
        this.endNode = new Node(endTile);

        startNode.setCostFromStart(0);
        startNode.setTotalCost(startNode.getCostFromStart() + heuristic(startNode, endNode));
        openList.add(startNode);
    }

    public Stack<Point> findPath() {
        if (!openList.isEmpty()) {
            Node current = getBestNode();

            if (current.getTile() == endNode.getTile()) {
                endNode = current;
                return tracePath();
            }

            openList.remove(current);
            closedList.add(current);

            for (Point point : current.getTile().getNeighbors()) {
                Tile neighborTile = grid[point.y][point.x];

                if (!diagonal) {
                    Tile currentTile = current.getTile();
                    int diffX = Math.abs(currentTile.x - neighborTile.x);
                    int diffY = Math.abs(currentTile.y - neighborTile.y);

                    if (diffX > 0 && diffY > 0) {
                        continue;
                    }
                }

                if (neighborTile != null && neighborTile.isEmpty()) {
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

//        return manhattan(dx, dy);
        return euclidean(dx, dy);
    }

    public Stack<Point> tracePath() {
        // Returns ordered stack of points along movement path
        Stack<Point> path = new Stack<Point>();
        // Chase parent of node until start point reached
        Node node = endNode;
        while (node.getParent() != null) {
            path.push(new Point(node.getTile().x, node.getTile().y));
            node = node.getParent();
        }
        return path;
    }


    public double manhattan(double dx, double dy) {
        return Math.abs(dx) + Math.abs(dy);
    }

    public double euclidean(double dx, double dy) {
        return Math.sqrt(dx * dx + dy * dy);
    }
}
