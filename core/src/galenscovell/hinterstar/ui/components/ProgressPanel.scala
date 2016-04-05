package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.inanimate.Location
import galenscovell.hinterstar.util.{Repository, ResourceManager}


class ProgressPanel(detailTable: Table) extends Table {
  private final val root: Table = detailTable
  private final val location: Location = Repository.currentLocation

  construct()

  def construct(): Unit = {
    this.setFillParent(true)
//    this.setBackground(ResourceManager.np_test3)

    for (event <- location.getEvents) {
      val eventTable: Table = new Table
      val padLeft: Float = event.getDistance() * 0.75f
      val padRight: Float = event.getDistance() * 0.25f
      eventTable.setBackground(ResourceManager.np_test3)
      this.add(eventTable).width(3).height(64).padLeft(padLeft).padRight(padRight)
    }
  }
}
