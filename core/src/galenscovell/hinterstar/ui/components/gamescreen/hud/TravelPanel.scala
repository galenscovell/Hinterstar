package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.sector._
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util._


class TravelPanel(stage: HudStage) extends Table {
  private val hudStage: HudStage = stage
  private var distanceLabel: Label = _

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)

    val topTable: Table = createTopTable
    val mapTable: Table = createMapTable
    val bottomTable: Table = createBottomTable

    this.add(topTable)
      .width(Constants.EXACT_X)
      .height(48)
    this.row
    this.add(mapTable)
      .width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - 96)
    this.row
    this.add(bottomTable)
      .width(Constants.EXACT_X)
      .height(48)
  }

  private def createTopTable: Table = {
    val table: Table = new Table
    table.setBackground(Resources.npTest1)

    val closeButton: TextButton = new TextButton("Close", Resources.blueButtonStyle)
    closeButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        hudStage.closeTravelPanel()
      }
    })
    table.add(closeButton).expand.width(96).height(40).right.padRight(10)

    table
  }

  private def createMapTable: Table = {
    val mapTable: Table = new Table
    generateMap(mapTable)
    mapTable.setBackground(Resources.npTest4)
    mapTable
  }

  private def generateMap(container: Table): Unit =  {
    // TODO: Each new map has randomized SystemMarker layout (depending on difficulty)
    val sectorGenerator: SectorGenerator = new SectorGenerator(16, 2)
    val systemMarkers: Array[Array[SystemMarker]] = sectorGenerator.getSystemMarkers

    // container.setDebug(true)
    for (row: Array[SystemMarker] <- systemMarkers) {
      for (systemMarker: SystemMarker <- row) {
        container.add(systemMarker)
          .width(Constants.SYSTEMMARKER_SIZE)
          .height(Constants.SYSTEMMARKER_SIZE)
      }
      container.row
    }
  }

  private def createBottomTable: Table = {
    val table: Table = new Table
    table.setBackground(Resources.npTest1)

    val travelButton: TextButton = new TextButton("Warp", Resources.blueButtonStyle)
    travelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        travelToSystem()
      }
    })
    distanceLabel = new Label("Distance: 0 AU", Resources.labelMenuStyle)
    table.add(distanceLabel).expand.fill.left.padLeft(10)
    table.add(travelButton).width(96).height(40).expand.fill.right.padRight(10)

    table
  }

  private def travelToSystem(): Unit = {
    if (SystemRepo.travelToSelection) {
      hudStage.closeTravelPanel()
      hudStage.disableTravelButton()
      hudStage.getGameScreen.beginTravel()
      hudStage.hideUI()
    }
  }
  
  def updateDistanceLabel(d: String): Unit = {
    distanceLabel.setText(d)
  }
}
