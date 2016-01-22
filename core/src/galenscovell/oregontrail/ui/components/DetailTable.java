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
        this.setBackground(ResourceManager.buttonUp);
        this.day = 1;
        this.month = 1;
        this.year = 2500;

        Table dateTable = new Table();
        this.dateLabel = new Label(dateAsString(), ResourceManager.label_mediumStyle);

        dateTable.add(dateLabel);

        this.add(dateTable).height(20).expand().fill().top().padTop(5);
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
