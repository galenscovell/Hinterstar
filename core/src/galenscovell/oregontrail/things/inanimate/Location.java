package galenscovell.oregontrail.things.inanimate;

import galenscovell.oregontrail.map.Sector;
import galenscovell.oregontrail.things.inanimate.Events.Event;
import galenscovell.oregontrail.util.Repository;

import java.util.*;

public class Location {
    public final int x, y, size;
    private Sector sector;
    private ArrayList<Event> events;
    private String[] details;

    public Location(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.details = new String[]{"Location Title", "Location Detail"};
    }

    public void setSector(Sector sector) {
        this.sector = sector;
        sector.becomeUnexplored();
    }

    public Sector getSector() {
        return sector;
    }

    public void enter() {
        generateEvents(new Random());
    }

    public String[] getDetails() {
        return details;
    }

    private void generateEvents(Random random) {
        // Each Location has between 1 and 4 events
        this.events = new ArrayList<Event>();
        int numberOfEvents = random.nextInt(4) + 1;
        for (int event = 0; event < numberOfEvents; event++) {

        }
        // Eventually background should be consistent and not created here
        createBackground(random);
    }

    private void createBackground(Random random) {
        // Background depends on number and type of events generated
        // eg many planet events = background has planets
        // eg no planet events = background has no planets
        int num = random.nextInt(4);
        String layerName;
        if (num == 0) {
            layerName = "blue_bg";
        } else if (num == 1) {
            layerName = "purple_bg";
        } else if (num == 2) {
            layerName = "green_bg";;
        } else {
            layerName = "";
        }
        Repository.gameScreen.setBackground(layerName, "bg1", "bg2", layerName, "bg1_blur", "bg2_blur");
    }
}
