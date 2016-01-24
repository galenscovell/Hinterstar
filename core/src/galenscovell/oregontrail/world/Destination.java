package galenscovell.oregontrail.world;

public class Destination {
    public int x, y;
    private Tile tile;
    private boolean explored, current;

    public Destination(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        tile.becomeUnexplored();
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
