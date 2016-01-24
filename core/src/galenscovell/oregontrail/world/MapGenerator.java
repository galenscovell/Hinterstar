package galenscovell.oregontrail.world;

import galenscovell.oregontrail.util.Constants;

import java.util.*;

public class MapGenerator {
    private Tile[][] grid;
    private ArrayList<Destination> destinations;

    public MapGenerator(int numDestinations) {
        this.grid = new Tile[Constants.MAPHEIGHT][Constants.MAPWIDTH];
        build(numDestinations);
        setTileNeighbors();
        removeAdjacentDestinations();
    }

    public Map<Integer, Tile> getTiles() {
        // Translate Tile[][] grid to HashMap
        Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                int key = x * Constants.MAPWIDTH + y;
                tiles.put(key, grid[y][x]);
            }
        }
        return tiles;
    }

    public void print() {
        // Debug method: print built map to console and exit
        for (Tile[] row : grid) {
            System.out.println();
            for (Tile tile : row) {
                if (tile.isUnexplored()) {
                    System.out.print('O');
                } else {
                    System.out.print(' ');
                }
            }
        }
        System.exit(1);
    }

    private void build(int numDestinations) {
        // Construct Tile[MAPHEIGHT][MAPWIDTH] grid of all empty tiles
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
        int destinationCount = getRandom(numDestinations - 4, numDestinations + 4);
        placeDestinations(destinationCount);

        // Set Tiles within each Destination as Unexplored
        for (int i = 0; i < destinationCount; i++) {
            Destination destination = this.destinations.get(i);
            this.grid[destination.y][destination.x].becomeUnexplored();
        }
    }

    private void placeDestinations(int destinationCount) {
        // Place random Destinations, ensuring that they do not collide
        this.destinations = new ArrayList<Destination>();
        for (int i = 0; i < destinationCount; i++) {
            int x = getRandom(1, Constants.MAPWIDTH);
            int y = getRandom(1, Constants.MAPHEIGHT);
            Destination destination = new Destination(x, y);
            this.destinations.add(destination);
        }
    }

    private void removeAdjacentDestinations() {
        ArrayList<Destination> removed = new ArrayList<Destination>();
        for (Destination destination : destinations) {
            for (Point point : grid[destination.y][destination.x].getNeighbors()) {
                boolean adjacentDestination = false;
                for (Destination d : destinations) {
                    if (point.x == d.x && point.y == d.y) {
                        adjacentDestination = true;
                    }
                }
                if (adjacentDestination) {
                    removed.add(destination);
                    continue;
                }
            }
        }
        for (Destination destination : removed) {
            destinations.remove(destination);
            grid[destination.y][destination.x].becomeEmpty();
        }
    }

    private int getRandom(int lo, int hi) {
        // Return random int between low and high
        return (int)(Math.random() * (hi - lo)) + lo;
    }

    private void setTileNeighbors() {
        // Set each tiles neighboring points
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                List<Point> points = new ArrayList<Point>();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (tile.x + dx == tile.x && tile.y + dy == tile.y || isOutOfBounds(tile.x + dx, tile.y + dy)) {
                            continue;
                        }
                        points.add(new Point(tile.x + dx, tile.y + dy));
                    }
                }
                tile.setNeighbors(points);
            }
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= Constants.MAPWIDTH || y >= Constants.MAPWIDTH);
    }
}