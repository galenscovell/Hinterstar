package galenscovell.oregontrail.util;

import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.ui.screens.GameScreen;

import java.util.ArrayList;

public class Repository {
    public static GameScreen gameScreen;
    public static ArrayList<Location> locations;
    public static Location currentLocation;

    private Repository() {}

    public static void setGameScreen(GameScreen game) {
        gameScreen = game;
    }

    public static boolean selectionIsValid() {
        Location selection = getCurrentSelection();
        return (selection != null && selection != currentLocation);
    }

    public static Location getCurrentSelection() {
        for (Location location : locations) {
            if (location.getTile().isSelected()) {
                return location;
            }
        }
        return null;
    }

    public static void setLocations(ArrayList<Location> locationsToSet) {
        locations = locationsToSet;
        Location mostLeftLocation = null;
        for (Location location : locations) {
            if (mostLeftLocation == null || location.x < mostLeftLocation.x) {
                mostLeftLocation = location;
            }
        }
        currentLocation = mostLeftLocation;
        currentLocation.getTile().becomeCurrent();
    }

    public static void resetSelection() {
        for (Location location : locations) {
            location.getTile().disableSelected();
        }
    }

    public static void travelToSelection() {
        Location selection = getCurrentSelection();
        if (selection != null) {
            currentLocation.getTile().becomeExplored();
            currentLocation = selection;
            selection.getTile().becomeCurrent();
            selection.enter();
        }
    }
}
