package galenscovell.oregontrail.map;

import galenscovell.oregontrail.util.Repository;
import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.util.Constants;

import java.util.*;

public class MapGenerator {
    private Tile[][] grid;
    private ArrayList<Location> locations;

    public MapGenerator(int numberOfLocations) {
        this.grid = new Tile[Constants.MAPHEIGHT][Constants.MAPWIDTH];

        build(numberOfLocations);
        setTileNeighbors();
        removeAdjacentLocations();
        Repository.setLocations(locations);
    }

    public Tile[][] getTiles() {
        return grid;
    }

    private void build(int numberOfLocations) {
        // Construct Tile[MAPHEIGHT][MAPWIDTH] grid of all empty tiles
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
        int locationAmount = getRandom(numberOfLocations - 2, numberOfLocations + 2);
        placeDestinations(locationAmount);
    }

    private void placeDestinations(int locationAmount) {
        // Place random Locations, ensuring that they do not collide
        this.locations = new ArrayList<Location>();
        for (int i = 0; i < locationAmount; i++) {
            boolean placed = false;
            Location location = null;
            while (!placed) {
                int randomX = getRandom(2, Constants.MAPWIDTH - 2);
                int randomY = getRandom(2, Constants.MAPHEIGHT - 2);

                location = new Location(randomX, randomY, grid[randomY][randomX]);

                boolean adjacentLocation = false;
                for (Location d : locations) {
                    if (location.x == d.x && location.y == d.y) {
                        adjacentLocation = true;
                    }
                }
                if (!adjacentLocation) {
                    placed = true;
                }
            }
            locations.add(location);
        }
    }

    private void removeAdjacentLocations() {
        ArrayList<Location> removed = new ArrayList<Location>();
        for (Location location : locations) {
            for (Point point : grid[location.y][location.x].getNeighbors()) {
                boolean adjacentLocation = false;
                for (Location d : locations) {
                    if (point.x == d.x && point.y == d.y) {
                        adjacentLocation = true;
                    }
                }
                if (adjacentLocation) {
                    removed.add(location);
                    continue;
                }
            }
        }
        for (Location location : removed) {
            locations.remove(location);
            grid[location.y][location.x].becomeEmpty();
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
