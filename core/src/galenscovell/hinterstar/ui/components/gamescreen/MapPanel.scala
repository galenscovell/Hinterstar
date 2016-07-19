package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.map._
import galenscovell.hinterstar.util._


class MapPanel(stage: GameStage) extends Table {
  private val gameStage: GameStage = stage
  private var distanceLabel: Label = _

  construct()


  private def construct(): Unit = {
    this.setFillParent(true)
    val mapGroup: Group = new Group
    val mapTable: Table = createMapTable
    val infoTable: Table = createInfoTable
    mapGroup.addActor(mapTable)
    mapGroup.addActor(infoTable)
    mapTable.setPosition(0, 0)
    infoTable.setPosition(0, 0)
    this.add(mapGroup).width(Constants.EXACT_X)
      .height(Constants.EXACT_Y - (Constants.SYSTEMMARKER_SIZE * 4))
      .padTop(Constants.SYSTEMMARKER_SIZE * 2)
      .padBottom(Constants.SYSTEMMARKER_SIZE * 2)
  }

  private def createMapTable: Table = {
    val mapTable: Table = new Table
    mapTable.setBackground(ResourceManager.npTest4)
    generateMap(mapTable)
    mapTable.setFillParent(true)
    mapTable
  }

  private def generateMap(container: Table): Unit =  {
    // TODO: Each new map has randomized SystemMarker layout (depending on difficulty)
    val mapGenerator: MapGenerator = new MapGenerator(16, 4)
    val systemMarkers: Array[Array[SystemMarker]] = mapGenerator.getSystemMarkers

    for (row <- systemMarkers) {
      for (systemMarker <- row) {
        container.add(systemMarker)
          .width(Constants.SYSTEMMARKER_SIZE)
          .height(Constants.SYSTEMMARKER_SIZE)
      }
      container.row
    }
  }

  private def createInfoTable: Table = {
    val infoTable: Table = new Table
    infoTable.setSize(Constants.EXACT_X, 50)
    infoTable.align(Align.center)
    val travelButton: TextButton = new TextButton("Travel", ResourceManager.greenButtonStyle)
    travelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        travelToSystem()
      }
    })
    this.distanceLabel = new Label("Distance: 0.0 AU", ResourceManager.labelMenuStyle)
    infoTable.add(distanceLabel).expand.fill.left.padLeft(20)
    infoTable.add(travelButton).width(150).height(50).expand.fill.right.padRight(8).padBottom(12)
    infoTable
  }

  private def travelToSystem(): Unit = {
    if (SystemRepo.travelToSelection) {
      gameStage.getNavButtons.getMapButton.setChecked(false)
      gameStage.togglePanel(0)
      gameStage.gameScreen.beginTravel()
      gameStage.hideNavButtons()
    }
  }
  
  def updateDistanceLabel(d: String): Unit = {
    distanceLabel.setText(d)
  }
}
