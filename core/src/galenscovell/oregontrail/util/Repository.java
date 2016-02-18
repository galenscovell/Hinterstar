package galenscovell.oregontrail.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import galenscovell.oregontrail.map.Tile;
import galenscovell.oregontrail.things.inanimate.Location;
import galenscovell.oregontrail.ui.components.GameStage;
import galenscovell.oregontrail.ui.screens.GameScreen;

import java.util.*;

public class Repository {
    public static GameScreen gameScreen;
    public static ArrayList<Location> locations;
    public static List<Location> locationsInRange;
    public static Location currentLocation;
    public static Location currentSelection;
    public static Tile[][] grid;
    public static ShapeRenderer shapeRenderer;
    public static int playerRange;

    private Repository() {}


    /**************************
     * Called from GameScreen
     */
    public static void setup(GameScreen game) {
        gameScreen = game;
        shapeRenderer = new ShapeRenderer();
        playerRange = 12;
    }

    public static void setTargetsInRange() {
        locationsInRange = new ArrayList<Location>();
        Tile currentTile = currentLocation.getTile();

        for (Location location : locations) {
            Tile locationTile = location.getTile();
            double squareDist = Math.pow(currentTile.x - locationTile.x, 2) + Math.pow(currentTile.y - locationTile.y, 2);
            if (squareDist <= Math.pow(playerRange, 2)) {
                locationsInRange.add(location);
            }
        }
    }

    public static void drawShapes() {
        float radius = playerRange * Constants.TILESIZE;
        float centerX = currentLocation.getTile().x * Constants.TILESIZE + (Constants.TILESIZE / 2);
        float centerY = Gdx.graphics.getHeight() - (currentLocation.getTile().y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f);

        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.circle(centerX, centerY, 20);

        if (locationsInRange != null && locationsInRange.size() > 0) {
            Tile currentTile = currentLocation.getTile();
            for (Location location : locationsInRange) {
                Tile locationTile = location.getTile();
                shapeRenderer.line(
                            (currentTile.x * Constants.TILESIZE) + (Constants.TILESIZE / 2),
                            Gdx.graphics.getHeight() - (currentTile.y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2),
                            (locationTile.x * Constants.TILESIZE) + (Constants.TILESIZE / 2),
                            Gdx.graphics.getHeight() - (locationTile.y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2)
                    );
            }
        }
        shapeRenderer.end();
    }


    /**************************
     * Called from Navmap
     */
    public static void setTiles(Tile[][] tiles) {
        grid = tiles;
    }

    public static boolean selectionIsValid() {
        return (currentSelection != null && currentSelection != currentLocation && locationsInRange.contains(currentSelection));
    }

    public static void travelToSelection() {
        if (currentSelection != null) {
            currentLocation.getTile().becomeExplored();
            currentLocation = currentSelection;
            currentSelection.getTile().becomeCurrent();
            currentSelection.enter();
        }
    }


    /**************************
     * Called from Tile
     */
    public static void setSelection() {
        if (currentSelection != null) {
            currentSelection.getTile().disableSelected();
        }

        currentSelection = getCurrentSelection();
        Tile currentTile = currentLocation.getTile();
        Tile locationTile = currentSelection.getTile();
        double distance = (Math.pow(currentTile.x - locationTile.x, 2) + Math.pow(currentTile.y - locationTile.y, 2)) / Constants.TILESIZE;
        String stringDistance = "Distance: " + String.format("%.1f", distance) + " AU";

        GameStage gameStage = (GameStage) gameScreen.getGameStage();
        gameStage.updateDistanceLabel(stringDistance);
    }

    public static Location getCurrentSelection() {
        for (Location location : locations) {
            if (location.getTile().isSelected()) {
                return location;
            }
        }
        return null;
    }


    /**************************
     * Called from MapGenerator
     */
    public static void populateLocations(ArrayList<Location> locationsToSet) {
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
}
