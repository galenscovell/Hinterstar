package galenscovell.oregontrail.map;

import java.util.ArrayList;

public class MapRepository {
    private ArrayList<Destination> destinations;
    private Destination currentLocation;

    public MapRepository() {

    }

    public Destination getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentDestination(Destination destination) {
        this.currentLocation = destination;
    }

    public Destination getCurrentSelection() {
        for (Destination destination : destinations) {
            if (destination.getTile().isSelected()) {
                return destination;
            }
        }
        return null;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
        Destination mostLeftDestination = null;
        for (Destination destination : destinations) {
            if (mostLeftDestination == null || destination.x < mostLeftDestination.x) {
                mostLeftDestination = destination;
            }
        }
        this.currentLocation = mostLeftDestination;
        currentLocation.getTile().becomeCurrent();
    }

    public void disableDestinationSelection() {
        for (Destination destination : destinations) {
            destination.getTile().disableSelected();
        }
    }

    public void travelToSelection() {
        Destination selection = getCurrentSelection();
        if (selection != null) {
            currentLocation.getTile().becomeExplored();
            currentLocation = selection;
            selection.getTile().becomeCurrent();
        }
    }
}
