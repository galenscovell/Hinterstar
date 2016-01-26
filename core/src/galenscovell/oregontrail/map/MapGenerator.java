package galenscovell.oregontrail.map;

import galenscovell.oregontrail.util.*;

import java.util.*;

public class MapGenerator {
    private Tile[][] grid;
    private ArrayList<Destination> destinations;
    private MapRepository repo;

    public MapGenerator(int numDestinations) {
        this.grid = new Tile[Constants.MAPHEIGHT][Constants.MAPWIDTH];
        this.repo = new MapRepository();

        build(numDestinations);
        setTileNeighbors();
        removeAdjacentDestinations();
        // setDestinationTypes();
        repo.setDestinations(destinations);
    }

    public Tile[][] getTiles() {
        return grid;
    }

    public MapRepository getRepo() {
        return repo;
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
    }

    private void build(int numDestinations) {
        // Construct Tile[MAPHEIGHT][MAPWIDTH] grid of all empty tiles
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                grid[y][x] = new Tile(x, y, repo);
            }
        }
        int destinationCount = getRandom(numDestinations - 4, numDestinations + 4);
        placeDestinations(destinationCount);
    }

    private void placeDestinations(int destinationCount) {
        // Place random Destinations, ensuring that they do not collide
        this.destinations = new ArrayList<Destination>();
        for (int i = 0; i < destinationCount; i++) {
            int randomX = getRandom(1, Constants.MAPWIDTH);
            int randomY = getRandom(1, Constants.MAPHEIGHT);
            this.destinations.add(new Destination(randomX, randomY, grid[randomY][randomX]));
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
