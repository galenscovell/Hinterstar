package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.{Constants, ResourceManager}


class LocationPanel(title: String, detail: String) extends Table {
  construct(title, detail)


  private def construct(title: String, detail: String) {
    this.setFillParent(true)
    val labelTable: Table = new Table
    val titleLabel: Label = new Label(title, ResourceManager.labelTitleStyle)
    titleLabel.setAlignment(Align.center, Align.right)
    val detailLabel: Label = new Label(detail, ResourceManager.labelMenuStyle)
    detailLabel.setAlignment(Align.center, Align.right)
    labelTable.add(titleLabel).height(60).expand.fill.bottom
    labelTable.row
    labelTable.add(detailLabel).height(30).expand.fill.top
    this.add(labelTable).expand.fill.width(Constants.EXACT_X - 20).height(90).top
  }
}
