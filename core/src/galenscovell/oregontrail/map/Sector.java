package galenscovell.oregontrail.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import galenscovell.oregontrail.util.*;

public class Sector extends Actor {
    public final int x, y;
    private int frames;
    private SectorType type;
    private Sprite sprite;
    private boolean glowUp;

    public Sector(int x, int y) {
        this.x = x;
        this.y = y;
        this.frames = 60;
        this.glowUp = true;
        becomeEmpty();

        this.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!isEmpty() && !isExplored()) {
                    Repository.setSelection(getThisSector());
                }
            }
        });
    }

    public Sector getThisSector() {
        return this;
    }

    public boolean isEmpty() {
        return type == SectorType.EMPTY;
    }

    public boolean isCurrent() {
        return type == SectorType.CURRENT;
    }

    public boolean isExplored() {
        return type == SectorType.EXPLORED;
    }

    public boolean isUnexplored() {
        return type == SectorType.UNEXPLORED;
    }

    public void becomeEmpty() {
        type = SectorType.EMPTY;
    }

    public void becomeCurrent() {
        ResourceManager.currentMarker.setTarget(
                x  * Constants.SECTORSIZE - Constants.SECTORSIZE,
                Gdx.graphics.getHeight() - (y * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE)
        );
        type = SectorType.CURRENT;
    }

    public void becomeExplored() {
        type = SectorType.EXPLORED;
    }

    public void becomeUnexplored() {
        sprite = ResourceManager.sp_test0;
        type = SectorType.UNEXPLORED;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isEmpty()) {
            drawGlow(batch);
            batch.draw(
                    sprite,
                    x * Constants.SECTORSIZE,
                    Gdx.graphics.getHeight() - (y * Constants.SECTORSIZE) - (3 * Constants.SECTORSIZE),
                    Constants.SECTORSIZE,
                    Constants.SECTORSIZE
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

        if (isExplored()) {
            batch.setColor(0.4f, 0.4f, 1.0f, frameAlpha);
        } else {
            batch.setColor(0.4f, 1.0f, 0.4f, frameAlpha);
        }

        batch.draw(
                ResourceManager.mapGlow,
                x * Constants.SECTORSIZE - Constants.SECTORSIZE,
                Gdx.graphics.getHeight() - (y * Constants.SECTORSIZE) - (4 * Constants.SECTORSIZE),
                Constants.SECTORSIZE * 3,
                Constants.SECTORSIZE * 3
        );
        batch.setColor(1, 1, 1, 1);
    }
}
