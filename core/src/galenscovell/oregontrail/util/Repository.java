package galenscovell.oregontrail.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import galenscovell.oregontrail.map.*;
import galenscovell.oregontrail.processing.pathfinding.Pathfinder;
import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.ui.components.GameStage;
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

    public static void clearPath() {
        if (pathPoints != null && pathPoints.size() > 0) {
            setDistanceToSelection("Distance: 0.0 AU");
            pathPoints.clear();
        }
    }


    /**************************
     * Called from GameScreen
     */
    public static void setup(GameScreen game) {
        gameScreen = game;
        shapeRenderer = new ShapeRenderer();
        pathfinder = new Pathfinder();
    }

    public static void drawPath() {
        if (pathPoints != null && pathPoints.size() > 0) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f);
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


    /**************************
     * Called from Navmap
     */
    public static void setTiles(Tile[][] tiles) {
        grid = tiles;
    }

    public static boolean selectionIsValid() {
        Location selection = getCurrentSelection();
        return (selection != null && selection != currentLocation);
    }

    public static void travelToSelection() {
        Location selection = getCurrentSelection();
        if (selection != null) {
            clearPath();
            currentLocation.getTile().becomeExplored();
            currentLocation = selection;
            selection.getTile().becomeCurrent();
            selection.enter();
        }
    }


    /**************************
     * Called from Tile
     */
    public static void setPath(Tile start, Tile end) {
        pathPoints = pathfinder.findPath(start, end, grid);
    }

    public static Location getCurrentSelection() {
        for (Location location : locations) {
            if (location.getTile().isSelected()) {
                return location;
            }
        }
        return null;
    }

    public static void clearSelection() {
        clearPath();
        for (Location location : locations) {
            location.getTile().disableSelected();
        }
    }


    /**************************
     * Called from MapGenerator
     */
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


    /**************************
     * Called from Pathfinder
     */
    public static void setDistanceToSelection(String d) {
        GameStage gameStage = (GameStage) gameScreen.getGameStage();
        gameStage.updateDistanceLabel(d);
    }
}
