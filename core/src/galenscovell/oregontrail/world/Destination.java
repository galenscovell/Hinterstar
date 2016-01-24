package galenscovell.oregontrail.world;

public class Destination {
    public int x, y;
    private boolean explored, current;

    public Destination(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void explore() {
        this.explored = true;
    }

    public void toggleCurrent() {
        this.current = !current;
    }
}
