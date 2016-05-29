package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.{Ship, ShipParser}
import galenscovell.hinterstar.util.ResourceManager

import scala.collection.mutable.Map


class ShipSelectionPanel extends Table {
  private val allShips: Array[Ship] = new ShipParser().parseAll
  private var currentShipIndex: Int = 0
  private val selectedShip: Ship = null
  private val shipDetails: Table = new Table

  updateShipDetails()
  construct()


  def getShip: Ship = {
    selectedShip
  }

  private def construct(): Unit = {
    // TOP TABLE
    val topTable: Table = new Table
    topTable.setBackground(ResourceManager.npTest1)

    val scrollRightButton: TextButton = new TextButton(">", ResourceManager.blueButtonStyle)
    scrollRightButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentShipIndex += 1
        if (currentShipIndex > allShips.length - 1) {
          currentShipIndex = 0
        }
        updateShipDetails()
      }
    })
    val scrollLeftButton: TextButton = new TextButton("<", ResourceManager.blueButtonStyle)
    scrollLeftButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentShipIndex -= 1
        if (currentShipIndex < 0) {
          currentShipIndex = allShips.length - 1
        }
        updateShipDetails()
      }
    })
    val shipTable: Table = new Table

    topTable.add(scrollLeftButton).width(80).expand.fill.left
    topTable.add(shipTable).width(500).expand.fill
    topTable.add(scrollRightButton).width(80).expand.fill.right

    // BOTTOM TABLE
    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)

    bottomTable.add(shipDetails).expand.fill

    // FINALIZE
    add(topTable).width(690).height(240).center
    row
    add(bottomTable).width(690).height(150).padTop(10).center
  }

  private def updateShipDetails(): Unit = {
    shipDetails.clear()

    val shipDetailTop: Table = new Table
    val shipName: String = allShips(currentShipIndex).getName
    val shipDesc: String = allShips(currentShipIndex).getDescription
    val shipNameLabel: Label = new Label(shipName, ResourceManager.labelMenuStyle)
    shipNameLabel.setAlignment(Align.center, Align.center)
    val shipDescLabel: Label = new Label(shipDesc, ResourceManager.labelMediumStyle)
    shipDescLabel.setAlignment(Align.center, Align.center)

    shipDetailTop.add(shipNameLabel).pad(6)
    shipDetailTop.row
    shipDetailTop.add(shipDescLabel)

    val shipDetailBottom: Table = new Table
    val shipPointMap: Map[String, Int] = allShips(currentShipIndex).getInstallPoints
    for ((k, v) <- shipPointMap) {
      val pointTable: Table = new Table
      pointTable.setBackground(ResourceManager.npTest4)
      val pointKey: Label = new Label(k, ResourceManager.labelMediumStyle)
      pointKey.setAlignment(Align.center)
      val pointVal: Label = new Label(v.toString, ResourceManager.labelMediumStyle)
      pointVal.setAlignment(Align.center)

      pointTable.add(pointKey).expand.fill.top
      pointTable.row
      pointTable.add(pointVal).expand.fill.bottom

      shipDetailBottom.add(pointTable).width(100).height(70).pad(4)
    }

    shipDetails.add(shipDetailTop).expand.height(50).top.pad(4)
    shipDetails.row
    shipDetails.add(shipDetailBottom).expand.height(80).top.pad(4)
  }
}
