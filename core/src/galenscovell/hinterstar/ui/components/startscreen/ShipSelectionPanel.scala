package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.{Ship, ShipParser}
import galenscovell.hinterstar.util.ResourceManager


class ShipSelectionPanel extends Table {
  private val allShips: Array[Ship] = new ShipParser().parseAll
  private var currentShipIndex: Int = 0
  private val selectedShip: Ship = null
  private val shipLabel: Label = new Label("", ResourceManager.labelMenuStyle)

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
        currentShipIndex -= 1
        if (currentShipIndex < 0) {
          currentShipIndex = allShips.length - 1
        }
        updateShipDetails()
      }
    })
    val scrollLeftButton: TextButton = new TextButton("<", ResourceManager.blueButtonStyle)
    scrollLeftButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentShipIndex += 1
        if (currentShipIndex > allShips.length - 1) {
          currentShipIndex = 0
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

    bottomTable.add(shipLabel).expand.fill


    // FINALIZE
    add(topTable).width(690).height(240).center
    row
    add(bottomTable).width(690).height(150).padTop(10).center
  }

  private def updateShipDetails(): Unit = {
    val shipName: String = allShips(currentShipIndex).getName
    val shipDesc: String = allShips(currentShipIndex).getDescription
    shipLabel.setText(s"$shipName\n$shipDesc")
    shipLabel.setAlignment(Align.center, Align.center)
  }
}
