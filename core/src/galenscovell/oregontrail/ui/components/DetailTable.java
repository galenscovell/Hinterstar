package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import galenscovell.oregontrail.util.ResourceManager;

public class DetailTable extends Table {
    private GameStage gameStage;
    private int day, month, year;
    private Label dateLabel;

    public DetailTable(GameStage gameStage) {
        this.gameStage = gameStage;
        construct();
    }

    private void construct() {
        this.setBackground(ResourceManager.buttonUp);
        this.day = 1;
        this.month = 1;
        this.year = 2500;

        Table dateTable = new Table();
        this.dateLabel = new Label(dateAsString(), ResourceManager.label_mediumStyle);
        dateLabel.setAlignment(Align.left, Align.left);
        dateTable.add(dateLabel).expand().fill();

        this.add(dateTable).height(20).expand().fill().top().padTop(5).padLeft(5);
    }

    private String dateAsString() {
        return String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
    }
}
