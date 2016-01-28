package galenscovell.oregontrail.map;

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

    public Tile(int x, int y, final MapRepository repo) {
        this.x = x;
        this.y = y;
        this.frames = 0;
        this.glowUp = true;
        becomeEmpty();

        this.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isEmpty()) {
                    repo.disableDestinationSelection();
                    selected = true;
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

    public boolean isSelected() {
        return selected;
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
        sprite = new Sprite(ResourceManager.uiAtlas.createSprite("map_diamond"));
        type = TileType.UNEXPLORED;
    }

    public void disableSelected() {
        selected = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isEmpty()) {
            return;
        }

        // Glow animation
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
        batch.setColor(0.3f, 0.8f, 1, frameAlpha);
        batch.draw(
            ResourceManager.mapGlow,
            (x + 11) * Constants.TILESIZE - 6,
            Gdx.graphics.getHeight() - (y + 5) * Constants.TILESIZE + 1,
            Constants.TILESIZE + 10,
            Constants.TILESIZE + 10
        );
        batch.setColor(1, 1, 1, 1);

        // Selected graphics
        if (selected) {
            batch.draw(
                ResourceManager.mapSelect,
                (x + 12) * Constants.TILESIZE - 4,
                Gdx.graphics.getHeight() - (y + 4) * Constants.TILESIZE,
                Constants.TILESIZE - 2,
                Constants.TILESIZE - 2
            );
        }

        // Current location graphics
        if (isCurrent()) {
            batch.setColor(0.5f, 1.0f, 0.6f, 1.0f);
        }

        // Explored location graphics
        if (isExplored()) {
            batch.setColor(0.5f, 0.6f, 1.0f, 1.0f);
        }

        // Map pointer graphics
        batch.draw(
            sprite,
            (x + 11) * Constants.TILESIZE,
            Gdx.graphics.getHeight() - (y + 5) * Constants.TILESIZE + 5,
            Constants.TILESIZE,
            Constants.TILESIZE
        );
        batch.setColor(1, 1, 1, 1);
    }
}
