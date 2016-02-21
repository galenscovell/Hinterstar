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

        for (Location location : locations) {
            double squareDist = Math.pow(currentLocation.getTile().x - location.getTile().x, 2) + Math.pow(currentLocation.getTile().y - location.getTile().y, 2);

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
        // Circle rendering
        shapeRenderer.setColor(0.95f, 0.61f, 0.07f, 0.6f);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.circle(centerX, centerY, 20);

        // Path rendering
        if (locationsInRange != null && locationsInRange.size() > 0) {
            shapeRenderer.setColor(0.93f, 0.94f, 0.95f, 0.6f);
            for (Location location : locationsInRange) {
                shapeRenderer.line(
                            (currentLocation.getTile().x * Constants.TILESIZE) + (Constants.TILESIZE / 2),
                            Gdx.graphics.getHeight() - (currentLocation.getTile().y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2),
                            (location.getTile().x * Constants.TILESIZE) + (Constants.TILESIZE / 2),
                            Gdx.graphics.getHeight() - (location.getTile().y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2)
                    );
            }
        }
        // Selection rendering
        if (currentSelection != null) {
            shapeRenderer.setColor(0.18f, 0.8f, 0.44f, 0.6f);
            float selectionX = currentSelection.getTile().x * Constants.TILESIZE + (Constants.TILESIZE / 2);
            float selectionY = Gdx.graphics.getHeight() - (currentSelection.getTile().y * Constants.TILESIZE) - (2 * Constants.TILESIZE) - (Constants.TILESIZE / 2);
            shapeRenderer.circle(selectionX, selectionY, 20);
        }
        shapeRenderer.end();
    }


    /**************************
     * Called from MapPanel
     */
    public static void setTiles(Tile[][] tiles) {
        grid = tiles;
    }

    public static boolean travelToSelection() {
        if (currentSelection != null && locationsInRange.contains(currentSelection)) {
            currentLocation.getTile().becomeExplored();
            currentLocation = currentSelection;
            currentSelection.getTile().becomeCurrent();
            currentSelection.enter();
            setSelection(null);
            return true;
        }
        return false;
    }


    /**************************
     * Called from Tile
     */
    public static void setSelection(Tile selection) {
        if (selection == null) {
            GameStage gameStage = (GameStage) gameScreen.getGameStage();
            gameStage.updateDistanceLabel("Distance: 0.0 AU");
            return;
        }

        for (Location location : locations) {
            if (location.getTile() == selection) {
                if (!(location == currentLocation)) {
                    currentSelection = location;

                    double distance = Math.sqrt(Math.pow(currentLocation.getTile().x - selection.x, 2) + Math.pow(currentLocation.getTile().y - selection.y, 2)) * 4;
                    GameStage gameStage = (GameStage) gameScreen.getGameStage();
                    gameStage.updateDistanceLabel("Distance: " + String.format("%.1f", distance) + " AU");
                }
            }
        }
    }


    /**************************
     * Called from MapGenerator
     */
    public static void populateLocations(ArrayList<Location> locationsToSet) {
        locations = locationsToSet;
        currentLocation = null;
        for (Location location : locations) {
            if (currentLocation == null || location.x < currentLocation.x) {
                currentLocation = location;
            }
        }
        currentLocation.getTile().becomeCurrent();
    }
}
