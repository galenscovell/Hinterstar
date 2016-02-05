package galenscovell.oregontrail.graphics;

import com.badlogic.gdx.graphics.g2d.*;
import galenscovell.oregontrail.util.*;

public class CurrentLocationAnimation {
    private TextureRegion sprite;
    private float frame, size;
    private int x, y;

    public CurrentLocationAnimation() {
        this.sprite = ResourceManager.uiAtlas.createSprite("current_marker");
        this.size = Constants.TILESIZE * 3;
        this.frame = 0.0f;
    }

    public void setTarget(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Batch batch) {
        batch.draw(sprite, x, y, size / 2, size / 2, size, size, 1, 1, -frame);
        if (frame < 360) {
            frame += 0.5f;
        } else {
            frame = 0;
        }
    }
}
