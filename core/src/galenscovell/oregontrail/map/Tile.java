package galenscovell.oregontrail.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import galenscovell.oregontrail.util.*;

import java.util.List;

public class Tile extends Actor {
    public final int x, y;
    private int frames;
    private TileType type;
    private List<Point> neighborTilePoints;
    private Sprite sprite;
    private boolean selected, glowUp;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.frames = 60;
        this.glowUp = true;
        becomeEmpty();

        this.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isEmpty() && !isExplored()) {
                    selected = true;
                    Repository.setSelection();
                }
            }
        });
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
        ResourceManager.currentMarker.setTarget(
                x  * Constants.TILESIZE - Constants.TILESIZE,
                Gdx.graphics.getHeight() - (y * Constants.TILESIZE) - (4 * Constants.TILESIZE)
        );
        type = TileType.CURRENT;
    }

    public void becomeExplored() {
        type = TileType.EXPLORED;
    }

    public void becomeUnexplored() {
        sprite = ResourceManager.sp_test0;
        type = TileType.UNEXPLORED;
    }

    public void disableSelected() {
        selected = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isEmpty()) {
            drawGlow(batch);
            batch.draw(
                    sprite,
                    x * Constants.TILESIZE,
                    Gdx.graphics.getHeight() - (y * Constants.TILESIZE) - (3 * Constants.TILESIZE),
                    Constants.TILESIZE,
                    Constants.TILESIZE
            );
            if (isCurrent()) {
                ResourceManager.currentMarker.render(batch);
            }
        }
    }

    private void drawGlow(Batch batch) {
        if (glowUp) {
            frames++;
        } else {
            frames -= 2;
        }

        if (frames == 120) {
            glowUp = false;
        } else if (frames == 40) {
            glowUp = true;
        }

        float frameAlpha = (frames / 120.0f);

        if (isSelected()) {
            batch.setColor(0.95f, 0.61f, 0.07f, frameAlpha);
        } else if (isExplored()) {
            batch.setColor(0.4f, 0.4f, 1.0f, frameAlpha);
        } else {
            batch.setColor(0.4f, 1.0f, 0.4f, frameAlpha);
        }

        batch.draw(
                ResourceManager.mapGlow,
                x * Constants.TILESIZE - Constants.TILESIZE,
                Gdx.graphics.getHeight() - (y * Constants.TILESIZE) - (4 * Constants.TILESIZE),
                Constants.TILESIZE * 3,
                Constants.TILESIZE * 3
        );
        batch.setColor(1, 1, 1, 1);
    }
}
