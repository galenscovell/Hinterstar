package galenscovell.oregontrail.map;

import java.util.ArrayList;

public class MapRepository {
    private ArrayList<Destination> destinations;
    private Destination currentDestination;

    public MapRepository() {

    }

    public Destination getCurrentDestination() {
        return currentDestination;
    }

    public void setCurrentDestination(Destination destination) {
        this.currentDestination = destination;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
        Destination mostLeftDestination = null;
        for (Destination destination : destinations) {
            if (mostLeftDestination == null || destination.x < mostLeftDestination.x) {
                mostLeftDestination = destination;
            }
        }
        this.currentDestination = mostLeftDestination;
        currentDestination.getTile().becomeCurrent();
    }

    public void disableDestinationSelection() {
        for (Destination destination : destinations) {
            destination.getTile().disableSelected();
        }
    }

    public void travelToDestination() {
        for (Destination destination : destinations) {
            if (destination.getTile().isSelected()) {
                currentDestination.getTile().becomeExplored();
                currentDestination = destination;
                destination.getTile().becomeCurrent();
            }
        }
    }
}
