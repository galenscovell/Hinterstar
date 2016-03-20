package galenscovell.oregontrail.ui.components

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.oregontrail.util.ResourceManager


class LocationPanel(title: String, detail: String) extends Table {
  construct(title, detail)


  private def construct(title: String, detail: String) {
    this.setFillParent(true)
    val labelTable: Table = new Table
    val titleLabel: Label = new Label(title, ResourceManager.label_titleStyle)
    titleLabel.setAlignment(Align.center, Align.center)
    val detailLabel: Label = new Label(detail, ResourceManager.label_menuStyle)
    detailLabel.setAlignment(Align.center, Align.center)
    labelTable.add(titleLabel).width(360).height(60).expand.fill.bottom
    labelTable.row
    labelTable.add(detailLabel).width(360).height(30).expand.fill.top
    this.add(labelTable).expand.fill.width(400).height(100).center.padBottom(80)
  }
}
