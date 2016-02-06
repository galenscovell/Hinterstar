package galenscovell.oregontrail.map;

import galenscovell.oregontrail.things.inanimate.Location;

import java.util.ArrayList;

public class MapRepository {
    private ArrayList<Location> locations;
    private Location currentLocation;

    public MapRepository() {

    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public Location getCurrentSelection() {
        for (Location location : locations) {
            if (location.getTile().isSelected()) {
                return location;
            }
        }
        return null;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
        Location mostLeftLocation = null;
        for (Location location : locations) {
            if (mostLeftLocation == null || location.x < mostLeftLocation.x) {
                mostLeftLocation = location;
            }
        }
        this.currentLocation = mostLeftLocation;
        currentLocation.getTile().becomeCurrent();
    }

    public void disableLocationSelection() {
        for (Location location : locations) {
            location.getTile().disableSelected();
        }
    }

    public void travelToSelection() {
        Location selection = getCurrentSelection();
        if (selection != null) {
            currentLocation.getTile().becomeExplored();
            currentLocation = selection;
            selection.getTile().becomeCurrent();
        }
    }
}
