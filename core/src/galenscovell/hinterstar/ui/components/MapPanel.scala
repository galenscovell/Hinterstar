package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.map._
import galenscovell.hinterstar.util._


class MapPanel(stage: GameStage) extends Table {
  private final val gameStage: GameStage = stage
  private var distanceLabel: Label = null
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
    this.add(mapGroup).width(Constants.EXACT_X).height(Constants.EXACT_Y - (Constants.SECTORSIZE * 2)).padTop(Constants.SECTORSIZE * 2)
  }

  private def createMapTable: Table = {
    val mapTable: Table = new Table
    mapTable.setBackground(ResourceManager.np_test4)
    generateMap(mapTable)
    mapTable.setFillParent(true)
    mapTable
  }

  private def generateMap(container: Table): Unit =  {
    // TODO: Each new map has randomized sector layout (depending on difficulty)
    val mapGenerator: MapGenerator = new MapGenerator(16, 4)
    val sectors: Array[Array[Sector]] = mapGenerator.getSectors
    Repository.setSectors(sectors)
    for (row <- sectors) {
      for (sector <- row) {
        container.add(sector).width(Constants.SECTORSIZE).height(Constants.SECTORSIZE)
      }
      container.row
    }
  }

  private def createInfoTable: Table = {
    val infoTable: Table = new Table
    infoTable.setSize(Constants.EXACT_X, 50)
    infoTable.align(Align.center)
    val travelButton: TextButton = new TextButton("Travel", ResourceManager.button_menuStyle)
    travelButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float) {
        travelToLocation()
      }
    })
    this.distanceLabel = new Label("Distance: 0.0 AU", ResourceManager.label_menuStyle)
    infoTable.add(distanceLabel).expand.fill.left.padLeft(20)
    infoTable.add(travelButton).width(150).height(50).expand.fill.right
    infoTable
  }

  private def travelToLocation(): Unit = {
    if (Repository.travelToSelection) {
      gameStage.getNavButtons.getMapButton.setChecked(false)
      gameStage.togglePanel(0)
      gameStage.gameScreen.beginTravel()
      gameStage.hideUIElements()
    }
  }

  def updateDistanceLabel(d: String): Unit = {
    distanceLabel.setText(d)
  }
}
