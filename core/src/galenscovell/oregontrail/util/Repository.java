package galenscovell.oregontrail.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import galenscovell.oregontrail.map.Sector;
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
    public static Sector[][] sectors;
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
            double squareDist = Math.pow(currentLocation.getSector().x - location.getSector().x, 2) + Math.pow(currentLocation.getSector().y - location.getSector().y, 2);

            if (squareDist <= Math.pow(playerRange, 2)) {
                locationsInRange.add(location);
            }
        }
    }

    public static void drawShapes() {
        float radius = playerRange * Constants.SECTORSIZE;
        float centerX = currentLocation.getSector().x * Constants.SECTORSIZE + (Constants.SECTORSIZE / 2);
        float centerY = Gdx.graphics.getHeight() - (currentLocation.getSector().y * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2);

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
                            (currentLocation.getSector().x * Constants.SECTORSIZE) + (Constants.SECTORSIZE / 2),
                            Gdx.graphics.getHeight() - (currentLocation.getSector().y * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2),
                            (location.getSector().x * Constants.SECTORSIZE) + (Constants.SECTORSIZE / 2),
                            Gdx.graphics.getHeight() - (location.getSector().y * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2)
                    );
            }
        }
        // Selection rendering
        if (currentSelection != null) {
            shapeRenderer.setColor(0.18f, 0.8f, 0.44f, 0.6f);
            float selectionX = currentSelection.getSector().x * Constants.SECTORSIZE + (Constants.SECTORSIZE / 2);
            float selectionY = Gdx.graphics.getHeight() - (currentSelection.getSector().y * Constants.SECTORSIZE) - (2 * Constants.SECTORSIZE) - (Constants.SECTORSIZE / 2);
            shapeRenderer.circle(selectionX, selectionY, 20);
        }
        shapeRenderer.end();
    }


    /**************************
     * Called from MapPanel
     */
    public static void setTiles(Sector[][] sectors) {
        Repository.sectors = sectors;
    }

    public static boolean travelToSelection() {
        if (currentSelection != null && locationsInRange.contains(currentSelection)) {
            currentLocation.getSector().becomeExplored();
            currentLocation = currentSelection;
            currentSelection.getSector().becomeCurrent();
            currentSelection.enter();
            setSelection(null);
            return true;
        }
        return false;
    }


    /**************************
     * Called from Tile
     */
    public static void setSelection(Sector selection) {
        if (selection == null) {
            GameStage gameStage = (GameStage) gameScreen.getGameStage();
            gameStage.updateDistanceLabel("Distance: 0.0 AU");
            return;
        }

        for (Location location : locations) {
            if (location.getSector() == selection) {
                if (!(location == currentLocation)) {
                    currentSelection = location;

                    double distance = Math.sqrt(Math.pow(currentLocation.getSector().x - selection.x, 2) + Math.pow(currentLocation.getSector().y - selection.y, 2)) * 4;
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
        currentLocation.getSector().becomeCurrent();
    }
}
