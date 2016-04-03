package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager


class LocationPanel(title: String, detail: String) extends Table {
  construct(title, detail)


  private def construct(title: String, detail: String) {
    this.setFillParent(true)
    val labelTable: Table = new Table
    val titleLabel: Label = new Label(title, ResourceManager.label_titleStyle)
    titleLabel.setAlignment(Align.center, Align.right)
    val detailLabel: Label = new Label(detail, ResourceManager.label_menuStyle)
    detailLabel.setAlignment(Align.center, Align.right)
    labelTable.add(titleLabel).width(800).height(60).expand.fill.bottom
    labelTable.row
    labelTable.add(detailLabel).width(800).height(30).expand.fill.top
    this.add(labelTable).expand.fill.width(800).height(90).top
  }
}
