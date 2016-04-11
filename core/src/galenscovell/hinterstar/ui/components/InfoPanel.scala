package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table


class InfoPanel(detailTable: Table) extends Table {
  private final val root: Table = detailTable

  construct()


  def construct(): Unit = {
    this.setFillParent(true)
  }
}
