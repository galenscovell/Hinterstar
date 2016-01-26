package galenscovell.oregontrail.map;

import java.util.ArrayList;

public class MapRepository {
    private ArrayList<Destination> destinations;

    public MapRepository() {

    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }

    public void disableDestinationSelection() {
        for (Destination destination : destinations) {
            destination.getTile().disableSelected();
        }
    }

    public void travelToDestination() {
        for (Destination destination : destinations) {
            if (destination.getTile().isSelected()) {

            }
        }
    }
}
