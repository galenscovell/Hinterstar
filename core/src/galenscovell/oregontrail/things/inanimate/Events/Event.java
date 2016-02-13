package galenscovell.oregontrail.things.inanimate.Events;

public interface Event {
    public void start();
    public void end();

    public void setDistanceTo(int distance);
    public int getDistanceTo(int distance);
}
