package galenscovell.oregontrail.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import galenscovell.oregontrail.util.*;

import java.util.List;

public class Tile extends Actor {
    public int x, y;
    private int frames;
    private TileType type;
    private List<Point> neighborTilePoints;
    private Sprite sprite;
    private boolean selected, glowUp;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.frames = 0;
        glowUp = true;
        becomeEmpty();

        this.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isEmpty()) {
                    selected = !selected;
                }
            }
        });
    }

    public Sprite getSprite() {
        return sprite;
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
        sprite = new Sprite(ResourceManager.uiAtlas.createSprite("map_diamond_unexplored"));
        type = TileType.UNEXPLORED;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isEmpty()) {
            return;
        }

        if (glowUp) {
            frames++;
        } else {
            frames -= 2;
        }
        if (frames == 120) {
            glowUp = false;
        } else if (frames == 0) {
            glowUp = true;
        }
        float frameAlpha = (frames / 120.0f);
        batch.setColor(0.3f, 0.3f, 1, frameAlpha);
        batch.draw(
                ResourceManager.mapDiamondGlow,
                (x + 11) * Constants.TILESIZE - 5,
                Gdx.graphics.getHeight() - (y + 5) * Constants.TILESIZE,
                Constants.TILESIZE + 10,
                Constants.TILESIZE + 10
        );
        batch.setColor(1, 1, 1, 1);

        if (selected) {
            batch.draw(
                    ResourceManager.mapDiamondSelect,
                    (x + 11) * Constants.TILESIZE,
                    Gdx.graphics.getHeight() - (y + 5) * Constants.TILESIZE + 5,
                    Constants.TILESIZE,
                    Constants.TILESIZE
            );
        } else {
            batch.draw(
                    sprite,
                    (x + 11) * Constants.TILESIZE,
                    Gdx.graphics.getHeight() - (y + 5) * Constants.TILESIZE + 5,
                    Constants.TILESIZE,
                    Constants.TILESIZE
            );
        }
    }
}
