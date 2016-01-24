package galenscovell.oregontrail.world;

import galenscovell.oregontrail.util.Constants;

import java.util.*;

public class MapGenerator {
    private Tile[][] grid;
    private ArrayList<Destination> destinations;
    private int numDestinations;

    public MapGenerator(int numDestinations) {
        this.grid = new Tile[Constants.MAPHEIGHT][Constants.MAPWIDTH];
        this.numDestinations = numDestinations;
        build();
    }

    private void build() {
        // Construct Tile[MAPHEIGHT][MAPWIDTH] grid of all empty tiles
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                grid[y][x] = new Tile(x, y);
            }
        }
        int destinationCount = getRandom(numDestinations - 4, numDestinations + 4);
        placeDestinations(destinationCount);
        squashDestinations();

        // Set all Tiles within each Destination as Active
        for (int i = 0; i < destinationCount; i++) {
            Destination destination = this.destinations.get(i);
            for (int x = destination.x; x < destination.x + destination.width; x++) {
                for (int y = destination.y; y < destination.y + destination.height; y++) {
                    this.grid[y][x].becomeActive();
                }
            }
        }
    }

    private void placeDestinations(int destinationCount) {
        // Place random Rooms, ensuring that they do not collide
        // Minus one from width and height at end so rooms are separated
        this.destinations = new ArrayList<Destination>();
        for (int i = 0; i < destinationCount; i++) {
            int x = getRandom(1, Constants.MAPWIDTH - 2);
            int y = getRandom(1, Constants.MAPHEIGHT - 2);
            System.out.println("1");
            Destination destination = new Destination(x, y, 2, 2);
            if (doesCollide(destination, -1)) {
                System.out.println("2");
                i--;
                continue;
            }
            destination.width--;
            destination.height--;
            this.destinations.add(destination);
        }
    }

    private void squashDestinations() {
        // Shift each Room towards upper left corner to reduce distance between Rooms
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < this.destinations.size(); j++) {
                Destination destination = this.destinations.get(j);
                while (true) {
                    int oldX = destination.x;
                    int oldY = destination.y;
                    if (destination.x > 1) {
                        destination.x--;
                    }
                    if (destination.y > 1) {
                        destination.y--;
                    }
                    if ((destination.x == 1) && (destination.y == 1)) {
                        break;
                    }
                    if (doesCollide(destination, j)) {
                        destination.x = oldX;
                        destination.y = oldY;
                        break;
                    }
                }
            }
        }
    }

    private boolean doesCollide(Destination destination, int ignore) {
        // Return if target Room overlaps already placed Room
        for (int i = 0; i < this.destinations.size(); i++) {
            if (i == ignore) {
                continue;
            }
            Destination check = this.destinations.get(i);
            if (!((destination.x + destination.width < check.x - 2) || (destination.x - 2 > check.x + check.width) || (destination.y + destination.height < check.y - 2) || (destination.y - 2 > check.y + check.height))) {
                return true;
            }
        }
        return false;
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
        // Debug method: print built dungeon to console and exit
        for (Tile[] row : grid) {
            System.out.println();
            for (Tile tile : row) {
                if (tile.isActive()) {
                    System.out.print('.');
                } else {
                    System.out.print(' ');
                }
            }
        }
    }
}
