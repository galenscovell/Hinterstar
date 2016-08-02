package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.{PlayerData, Resources}

import scala.collection.mutable


class AssignmentPanel(crewmateBoxRoot: CrewmateBox, c: Crewmate) extends Table {
  private val rootBox: CrewmateBox = crewmateBoxRoot
  private val crewmate: Crewmate = c

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)

    val contentTable: Table = new Table
    contentTable.setBackground(Resources.npTest1)

    val innerTable: Table = new Table
    val subsystemMap: mutable.Map[String, Int] = PlayerData.getOccupiedSubsystems

    for (subsystem: String <- subsystemMap.keys) {
      val subsystemTable: Table = new Table
      subsystemTable.setBackground(Resources.npTest4)

      subsystemTable.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          crewmate.setAssignment(subsystem)
          println(crewmate.getName, crewmate.getAssignment)
        }
      })

      val subsystemLabel: Label = new Label(subsystem, Resources.labelTinyStyle)
      subsystemLabel.setWrap(true)
      subsystemLabel.setAlignment(Align.center)

      val assignedCrewmatesLabel: Label = new Label(subsystemMap(subsystem).toString, Resources.labelTinyStyle)
      assignedCrewmatesLabel.setAlignment(Align.center)

      subsystemTable.add(subsystemLabel).expand.fill.pad(4)
      subsystemTable.row
      subsystemTable.add(assignedCrewmatesLabel).expand.fill

      innerTable.add(subsystemTable).width(80).height(60).pad(2).left
    }

    contentTable.add(innerTable).expand.left.padLeft(4)

    this.add(contentTable).expand.width(600).height(70).left.padLeft(10).bottom.padBottom(70)
  }
}
