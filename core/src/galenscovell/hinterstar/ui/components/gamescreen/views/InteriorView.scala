package galenscovell.hinterstar.ui.components.gamescreen.views

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.generation.interior._
import galenscovell.hinterstar.ui.components.gamescreen.GameStage
import galenscovell.hinterstar.util._


class InteriorView(stage: GameStage) extends Table {
  val gen: InteriorGenerator = new InteriorGenerator(22, 12, 12)
  private val gameStage: GameStage = stage

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    // this.setDebug(true)
    val interiorTable: Table = createInteriorTable
    this.add(interiorTable).expand.fill.center
  }

  private def createInteriorTable: Table = {
    val interiorTable: Table = new Table
    generateInterior(interiorTable)
    interiorTable.setFillParent(true)
    interiorTable
  }

  private def generateInterior(container: Table): Unit =  {
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
