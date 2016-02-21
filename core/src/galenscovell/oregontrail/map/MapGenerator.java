package galenscovell.oregontrail.map;

import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.util.*;

import java.util.*;

public class MapGenerator {
    private final Sector[][] sectors;
    private ArrayList<Location> locations;

    public MapGenerator() {
        this.sectors = new Sector[Constants.MAPHEIGHT][Constants.MAPWIDTH];
        build();
        Repository.populateLocations(locations);
    }

    public Sector[][] getSectors() {
        return sectors;
    }

    private void build() {
        // Construct Sector[MAPHEIGHT][MAPWIDTH] sectors of all empty Sectors
        for (int x = 0; x < Constants.MAPWIDTH; x++) {
            for (int y = 0; y < Constants.MAPHEIGHT; y++) {
                sectors[y][x] = new Sector(x, y);
            }
        }
        placeDestinations();
    }

    private void placeDestinations() {
        // Place random Locations, ensuring that they are distanced apart
        final int attempts = 480;
        final int maxLocations = 12;
        final int padsize = 4;
        this.locations = new ArrayList<Location>();

        for (int i = 0; i < attempts; i++) {
            if (locations.size() == maxLocations) {
                return;
            }
            int x = getRandom(1, Constants.MAPWIDTH - padsize - 1);
            int y = getRandom(1, Constants.MAPHEIGHT - padsize - 1);
            if (x == 0 || x == Constants.MAPWIDTH || y == 0 || y == Constants.MAPHEIGHT) {
                continue;
            }
            Location location = new Location(x, y, padsize);

            if (doesCollide(location, -1)) {
                continue;
            }

            int centerX = (location.size / 2) + location.x;
            int centerY = (location.size / 2) + location.y;
            location.setSector(sectors[centerY][centerX]);
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

    private int getRandom(int lo, int hi) {
        return (int)(Math.random() * (hi - lo)) + lo;
    }
}
