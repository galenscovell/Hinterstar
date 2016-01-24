package galenscovell.oregontrail.world;

import java.util.ArrayList;

public class Destination {
    public int x, y, width, height;

    public Destination(int topLeftX, int topLeftY, int width, int height) {
        this.x = topLeftX;
        this.y = topLeftY;
        this.width = width;
        this.height = height;
    }
}
