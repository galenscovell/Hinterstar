package galenscovell.oregontrail.map;

import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.util.*;

import java.util.*;

public class MapGenerator {
    private Tile[][] grid;
    private ArrayList<Location> locations;

    public MapGenerator() {
        this.grid = new Tile[Constants.MAPHEIGHT][Constants.MAPWIDTH];
        build();
    }

    public Tile[][] getTiles() {
        return grid;
    }

    private void build() {
        // Construct Tile[MAPHEIGHT][MAPWIDTH] grid of all empty tiles
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
        placeDestinations();
        setTileNeighbors();
        Repository.setLocations(locations);
    }

    private void placeDestinations() {
        // Place random Locations, ensuring that they do not collide
        final int attempts = 480;
        final int maxLocations = 8;
        final int padsize = 3;
        this.locations = new ArrayList<Location>();

        for (int i = 0; i < attempts; i++) {
            if (locations.size() == maxLocations) {
                return;
            }
            int x = getRandom(1, Constants.MAPWIDTH - padsize - 1);
            int y = getRandom(1, Constants.MAPHEIGHT - padsize - 1);
            Location location = new Location(x, y, padsize);

            if (doesCollide(location, -1)) {
                continue;
            }

            int centerX = (location.size / 2) + location.x;
            int centerY = (location.size / 2) + location.y;
            location.setTile(grid[centerY][centerX]);
            this.locations.add(location);
        }
    }

    private boolean doesCollide(Location location, int ignore) {
        // Return if target location overlaps already placed location
        for (int i = 0; i < this.locations.size(); i++) {
            if (i == ignore) {
                continue;
            }
            Location check = this.locations.get(i);
            if (!((location.x + location.size < check.x - 2) ||
                    (location.x - 2 > check.x + check.size) ||
                    (location.y + location.size < check.y - 2) ||
                    (location.y - 2 > check.y + check.size))) {
                return true;
            }
        }
        return false;
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

    private int getRandom(int lo, int hi) {
        // Return random int between low and high
        return (int)(Math.random() * (hi - lo)) + lo;
    }
}
