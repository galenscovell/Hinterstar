package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.{PlayerData, Resources}

import scala.collection.mutable.ArrayBuffer


class CrewPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage

  construct()


  private def construct(): Unit = {
    this.setDebug(true)
    val mainTable: Table = new Table
    this.setBackground(Resources.npTest2)

    this.add(mainTable).expand.fill
  }

  private def update(): Unit = {
    val crewData: ArrayBuffer[Crewmate] = PlayerData.getCrew()

  }

  private def constructCrewBox(crewStats: Map[String, String]): Table = {
    val crewBox: Table = new Table

    crewBox
  }
}
