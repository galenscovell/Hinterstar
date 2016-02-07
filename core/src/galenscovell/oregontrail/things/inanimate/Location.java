package galenscovell.oregontrail.things.inanimate;

import galenscovell.oregontrail.map.Tile;
import galenscovell.oregontrail.things.inanimate.Events.Event;
import galenscovell.oregontrail.util.Repository;

import java.util.*;

public class Location {
    public int x, y, size;
    private Tile tile;
    private ArrayList<Event> events;

    public Location(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        tile.becomeUnexplored();
    }

    public Tile getTile() {
        return tile;
    }

    public void enter() {
        Random random = new Random();
        generateEvents(random);
        createBackground(random);
    }

    private void generateEvents(Random random) {
        // Each Location has between 1 and 4 events
        this.events = new ArrayList<Event>();
        int numberOfEvents = random.nextInt(4) + 1;
        for (int e = 0; e < numberOfEvents; e++) {

        }
    }

    private void createBackground(Random random) {
        // Background depends on number and type of events generated
        // eg many planet events = background has planets
        // eg no planet events = background has no planets
        Repository.gameScreen.setBackground("blue_bg", "bg1", "bg2", "blue_bg", "bg1_blur", "bg2_blur");
    }
}
