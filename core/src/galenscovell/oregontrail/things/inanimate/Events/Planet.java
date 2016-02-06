package galenscovell.oregontrail.things.inanimate.Events;

import java.util.Random;

public class Planet implements Event {
    private int resources, type, population;
    private int humidity, atmosphere, temperature, gravity;

    public Planet() {
        generate();
    }

    private void generate() {
        Random random = new Random();
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    @Override
    public void setDistanceTo(int distance) {

    }

    @Override
    public int getDistanceTo(int distance) {
        return 0;
    }
}
