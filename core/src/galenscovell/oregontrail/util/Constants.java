package galenscovell.oregontrail.util;

public class Constants {
    private Constants() {}

    // Lighting masks
    public static final short BIT_LIGHT = 1;
    public static final short BIT_WALL = 2;
    public static final short BIT_GROUP = 5;

    // Custom screen dimension units
    public static final int SCREEN_X = 200;
    public static final int SCREEN_Y = 120;

    // Exact pixel dimensions
    public static final int EXACT_X = 800;
    public static final int EXACT_Y = 480;

    // Map dimensions (1 Tile = 1 AU)
    public static final int MAPWIDTH = 50;  // 800px @ 16px
    public static final int MAPHEIGHT = 30; // 480px @ 16px
    public static final int TILESIZE = 16;

    // Distance Units
    // ~8min to travel one AU at speed of light
    // From Sun to Uranus: 19.2 AU
    // From Sun to Neptune: 30 AU
    // From Sun to Pluto: 39.4 AU
    // To nearest star (Proxima Centauri): 271,000 AU
    public static final double SPEED_OF_LIGHT = 1.86 * Math.pow(10, 3);     // miles per second
    public static final double ASTRONOMICAL_UNIT = 9.29 * Math.pow(10, 7);  // in miles
}
