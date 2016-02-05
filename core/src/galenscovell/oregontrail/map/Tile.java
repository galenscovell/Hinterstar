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
        ResourceManager.currentMarker.setTarget(
            (x + 2) * Constants.TILESIZE - 32,
            Gdx.graphics.getHeight() - (y + 4) * Constants.TILESIZE - 12
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
            if (isExplored()) {
                batch.setColor(0.6f, 0.8f, 1.0f, 1.0f);
                batch.draw(
                        sprite,
                        (x + 2) * Constants.TILESIZE - 8,
                        Gdx.graphics.getHeight() - (y + 4) * Constants.TILESIZE,
                        Constants.TILESIZE,
                        Constants.TILESIZE
                );
                batch.setColor(1, 1, 1, 1);
            } else {
                batch.draw(
                        sprite,
                        (x + 2) * Constants.TILESIZE - 8,
                        Gdx.graphics.getHeight() - (y + 4) * Constants.TILESIZE + 12,
                        Constants.TILESIZE,
                        Constants.TILESIZE
                );
            }

            // Current
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
        if (frames == 160) {
            glowUp = false;
        } else if (frames == 60) {
            glowUp = true;
        }
        float frameAlpha = (frames / 160.0f);
        if (isSelected()) {
            batch.setColor(0.95f, 0.3f, 1, frameAlpha);
        } else {
            batch.setColor(0.3f, 0.95f, 1, frameAlpha);
        }
        batch.draw(
                ResourceManager.mapGlow,
                (x + 2) * Constants.TILESIZE - 20,
                Gdx.graphics.getHeight() - (y + 4) * Constants.TILESIZE,
                Constants.TILESIZE * 2,
                Constants.TILESIZE * 2
        );
        batch.setColor(1, 1, 1, 1);
    }
}
