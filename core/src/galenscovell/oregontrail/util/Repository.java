package galenscovell.oregontrail.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.processing.pathfinding.Pathfinder;
import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.ui.screens.GameScreen;

import java.util.*;

public class Repository {
    public static GameScreen gameScreen;
    public static ArrayList<Location> locations;
    public static Location currentLocation;
    public static Tile[][] grid;
    public static ShapeRenderer shapeRenderer;
    public static List<Point> pathPoints;
    public static Pathfinder pathfinder;

    private Repository() {}

    public static void setGameScreen(GameScreen game) {
        gameScreen = game;
        shapeRenderer = new ShapeRenderer();
        pathfinder = new Pathfinder();
    }

    public static void setTiles(Tile[][] tiles) {
        grid = tiles;
    }

    public static void setPath(Tile start, Tile end) {
        pathPoints = pathfinder.findPath(start, end, grid);
    }

    public static void drawPath() {
        if (pathPoints != null && pathPoints.size() > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (int i = 1; i < pathPoints.size(); i++) {
                Point p = pathPoints.get(i - 1);
                Point n = pathPoints.get(i);
                shapeRenderer.line(
                    (p.x + 2) * Constants.TILESIZE - 8 + (Constants.TILESIZE / 2),
                    Gdx.graphics.getHeight() - (p.y + 4) * Constants.TILESIZE + 12 + (Constants.TILESIZE / 2),
                    (n.x + 2) * Constants.TILESIZE - 8 + (Constants.TILESIZE / 2),
                    Gdx.graphics.getHeight() - (n.y + 4) * Constants.TILESIZE + 12 + (Constants.TILESIZE / 2)
                );
            }
            shapeRenderer.end();
        }
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
