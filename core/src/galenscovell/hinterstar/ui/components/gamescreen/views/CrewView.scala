package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.{Group, InputEvent}
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.interior.{InteriorGenerator, Tile}
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.util._


class CrewView(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  val gen: InteriorGenerator = new InteriorGenerator(22, 12, 12)

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mapGroup: Group = new Group
    val interiorTable: Table = createInteriorTable
    val infoTable: Table = createInfoTable
    mapGroup.addActor(interiorTable)
    mapGroup.addActor(infoTable)
    interiorTable.setPosition(0, 0)
    infoTable.setPosition(0, 0)

    this.add(mapGroup).width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - (Constants.SYSTEMMARKER_SIZE * 4))
      .padTop(Constants.SYSTEMMARKER_SIZE * 2)
      .padBottom(Constants.SYSTEMMARKER_SIZE * 2)
  }

  private def createInteriorTable: Table = {
    val interiorTable: Table = new Table
    generateInterior(interiorTable)
    interiorTable.setFillParent(true)
    interiorTable.setBackground(Resources.npTest3)
    interiorTable
  }

  private def createInfoTable: Table = {
    val infoTable: Table = new Table
    infoTable.setSize(Constants.EXACT_X, 50)
    infoTable.align(Align.center)
    infoTable
  }

  private def generateInterior(container: Table): Unit = {
    val interiorTiles: Array[Array[Tile]] = gen.getTiles()

    // container.setDebug(true)
    for (row: Array[Tile] <- interiorTiles) {
      for (tile: Tile <- row) {
        container.add(tile)
          .width(Constants.TILE_SIZE)
          .height(Constants.TILE_SIZE)
      }
      container.row
    }
  }
}
