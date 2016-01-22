package galenscovell.oregontrail.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ParallaxLayer {
    public TextureRegion region;
    public Vector2 parallaxRatio, startPosition, padding;

    public ParallaxLayer(TextureRegion region, Vector2 parallaxRatio, Vector2 padding) {
        this.region  = region;
        this.parallaxRatio = parallaxRatio;
        this.startPosition = new Vector2(0, 0);
        this.padding = padding;
    }

    public ParallaxLayer(TextureRegion region, Vector2 parallaxRatio, Vector2 startPosition, Vector2 padding) {
        this.region  = region;
        this.parallaxRatio = parallaxRatio;
        this.startPosition = startPosition;
        this.padding = padding;
    }
}
