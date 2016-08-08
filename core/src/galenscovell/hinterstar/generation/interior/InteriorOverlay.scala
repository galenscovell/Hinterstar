package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.scenes.scene2d.ui.Table


/**
  * The actor grid of subsystems generated from InteriorParser.
  * Displayed at player will, crewmates can be sent to the subsystem selected.
  */
class InteriorOverlay(shipName: String) extends Table {
  private val interiorParser: InteriorParser = new InteriorParser(shipName)
  private val subsystems: Array[Array[Subsystem]] = interiorParser.getSubsystems

  construct()


  private def construct(): Unit = {
    // this.setDebug(true)

    for (row: Array[Subsystem] <- subsystems) {
      for (subsystem: Subsystem <- row) {
        this.add(subsystem)
          .width(interiorParser.subsystemSize)
          .height(interiorParser.subsystemSize)
      }
      this.row
    }
  }
}
