package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

import galenscovell.oregontrail.util.ResourceManager;

public class LocationPanel extends Table {

    public LocationPanel(String title, String detail) {
        construct(title, detail);
    }

    private void construct(String title, String detail) {
        this.setFillParent(true);

        Label titleLabel = new Label(title, ResourceManager.label_titleStyle);
        titleLabel.setAlignment(Align.center, Align.center);
        Label detailLabel = new Label(detail, ResourceManager.label_menuStyle);
        detailLabel.setAlignment(Align.center, Align.center);

        this.add(titleLabel).width(340).height(60).expand().fill().bottom();
        this.row();
        this.add(detailLabel).width(340).height(30).expand().fill().top();
        this.padBottom(80);
    }
}
