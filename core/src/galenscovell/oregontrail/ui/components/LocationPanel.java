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

        Table labelTable = new Table();

        Label titleLabel = new Label(title, ResourceManager.label_titleStyle);
        titleLabel.setAlignment(Align.center, Align.center);
        Label detailLabel = new Label(detail, ResourceManager.label_menuStyle);
        detailLabel.setAlignment(Align.center, Align.center);

        labelTable.add(titleLabel).width(360).height(60).expand().fill().bottom();
        labelTable.row();
        labelTable.add(detailLabel).width(360).height(30).expand().fill().top();

        this.add(labelTable).expand().fill().width(400).height(100).center().padBottom(80);;
    }
}
