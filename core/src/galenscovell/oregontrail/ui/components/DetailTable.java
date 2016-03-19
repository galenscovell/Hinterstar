package galenscovell.oregontrail.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        this.setBackground(ResourceManager.np_test4);
        this.day = 1;
        this.month = 1;
        this.year = 2500;

        Table labelTable = new Table();
        this.dateLabel = new Label(dateAsString(), ResourceManager.label_mediumStyle);
        labelTable.add(dateLabel).expand().fill();

        this.add(labelTable).expand().fill().padTop(10).padLeft(10);
    }

    public void updateDate() {
        if (day < 12) {
            day++;
        } else {
            day = 1;
            if (month < 12) {
                month++;
            } else {
                month = 1;
                year++;
            }
        }
        dateLabel.setText(dateAsString());
    }

    private String dateAsString() {
        return String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
    }
}
