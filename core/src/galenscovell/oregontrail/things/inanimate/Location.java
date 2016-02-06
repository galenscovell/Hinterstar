package galenscovell.oregontrail.things.inanimate;

import galenscovell.oregontrail.map.Tile;
import galenscovell.oregontrail.things.inanimate.Events.Event;

import java.util.*;

public class Location {
    public int x, y;
    private Tile tile;
    private boolean explored, current;
    private ArrayList<Event> events;

    public Location(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        tile.becomeUnexplored();

        generateEvents();
    }

    private void generateEvents() {
        Random random = new Random();
        // Each Location has between 1 and 4 events
        int numberOfEvents = random.nextInt(4) + 1;
    }

    public Tile getTile() {
        return tile;
    }

    public void explore() {
        this.explored = true;
    }

    public void toggleCurrent() {
        this.current = !current;
    }
}
