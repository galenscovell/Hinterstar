package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui.Table


class InfoPanel(detailTable: Table) extends Table {
  private val root: Table = detailTable

  construct()


  def construct(): Unit = {
    this.setFillParent(true)
  }
}
