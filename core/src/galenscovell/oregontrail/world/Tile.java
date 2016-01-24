package galenscovell.oregontrail.world;

import com.badlogic.gdx.graphics.g2d.*;

import java.util.List;

public class Tile {
    public int x, y;
    private int currentFrame, frames;
    private TileType type;
    private List<Point> neighborTilePoints;
    private Sprite[] sprites;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = TileType.EMPTY;
        this.frames = 0;
        this.currentFrame = 0;
    }

    public Sprite getSprite() {
        return sprites[0];
    }

    public boolean isEmpty() {
        return type == TileType.EMPTY;
    }

    public boolean isCurrent() {
        return type == TileType.CURRENT;
    }

    public boolean isExplored() {
        return type == TileType.EXPLORED;
    }

    public boolean isUnexplored() {
        return type == TileType.UNEXPLORED;
    }

    public List<Point> getNeighbors() {
        return neighborTilePoints;
    }


    public void setNeighbors(List<Point> points) {
        this.neighborTilePoints = points;
    }

    public void becomeEmpty() {
        type = TileType.EMPTY;
    }

    public void becomeCurrent() {
        type = TileType.CURRENT;
    }

    public void becomeExplored() {
        type = TileType.EXPLORED;
    }

    public void becomeUnexplored() {
        type = TileType.UNEXPLORED;
    }


    public void draw(SpriteBatch batch, int tileSize) {
        if (frames == 60) {
            if (currentFrame == 0) {
                currentFrame++;
            } else if (currentFrame == 1) {
                currentFrame--;
            }
            frames -= frames;
        }
        batch.draw(sprites[currentFrame], x * tileSize, y * tileSize, tileSize, tileSize);
        frames++;
    }


}
