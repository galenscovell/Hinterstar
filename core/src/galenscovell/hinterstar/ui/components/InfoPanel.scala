package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.inanimate.Location
import galenscovell.hinterstar.util.{Constants, Repository, ResourceManager}


class InfoPanel(detailTable: Table) extends Table {
  private final val root: Table = detailTable
  private final val location: Location = Repository.currentLocation
  private var playerIcon: Table = null

  construct()


  def construct(): Unit = {
    this.setFillParent(true)

    val eventTable: Table  = new Table
    for (event <- location.getEvents) {
      val eventIcon: Table = new Table
      val padLeft: Float = event.getDistance * 0.7f
      val padRight: Float = event.getDistance * 0.3f
      eventIcon.setBackground(ResourceManager.np_test3)
      eventTable.add(eventIcon).width(3).height(44).expand.fill.padLeft(padLeft).padRight(padRight)
    }

    val playerTable: Table = new Table
    this.playerIcon = new Table
    playerIcon.setBackground(ResourceManager.np_test0)
    playerTable.add(playerIcon).width(4).height(20).expand.fill.left

    this.add(eventTable).width(Constants.PROGRESS_PANEL_WIDTH).height(44).expand.fill
    this.row
    this.add(playerTable).width(Constants.PROGRESS_PANEL_WIDTH).height(20).expand.fill
  }
}
