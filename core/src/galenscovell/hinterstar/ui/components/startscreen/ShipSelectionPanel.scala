package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, TextureRegionDrawable}
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent}
import com.badlogic.gdx.utils.{Align, Scaling}
import galenscovell.hinterstar.things.{Ship, ShipParser}
import galenscovell.hinterstar.util.{Constants, ResourceManager}

import scala.collection.mutable.Map


class ShipSelectionPanel extends Table {
  private val allShips: Array[Ship] = new ShipParser().parseAll
  private var currentShipIndex: Int = 0

  private val shipDisplay: Table = new Table
  private val shipImage: Image = new Image
  private val shipDetail: Table = new Table

  shipImage.setScaling(Scaling.fillX)
  shipDetail.setBackground(ResourceManager.npTest1)
  shipDetail.setColor(Constants.normalColor)

  construct()


  def getShip: Ship = {
    allShips(currentShipIndex)
  }


  private def construct(): Unit = {
    val topTable: Table = createTopTable
    val bottomTable: Table = new Table

    bottomTable.add(shipDetail).expand.fill

    add(topTable).width(690).height(220).center
    row
    add(bottomTable).width(690).height(170).padTop(10).center
  }


  private def createTopTable: Table = {
    val topTable: Table = new Table

    val scrollLeftButton: TextButton = new TextButton("<", ResourceManager.blueButtonStyle)
    scrollLeftButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentShipIndex -= 1
        if (currentShipIndex < 0) {
          currentShipIndex = allShips.length - 1
        }
        updateShipDisplay(false)
        updateShipDetails()
      }
    })
    val scrollRightButton: TextButton = new TextButton(">", ResourceManager.blueButtonStyle)
    scrollRightButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentShipIndex += 1
        if (currentShipIndex > allShips.length - 1) {
          currentShipIndex = 0
        }
        updateShipDisplay(true)
        updateShipDetails()
      }
    })

    topTable.add(scrollLeftButton).width(80).expand.fill.left
    topTable.add(shipDisplay).width(500).expand.fill
    topTable.add(scrollRightButton).width(80).expand.fill.right

    topTable
  }


  def updateShipDisplay(transitionRight: Boolean): Unit = {
    var amount: Int = 100
    var origin: Int = 0
    if (!transitionRight) {
      origin = amount * 2
      amount = -amount
    }

    shipDisplay.clearActions()
    shipDisplay.addAction(Actions.sequence(
      Actions.parallel(
        Actions.fadeOut(0.2f, Interpolation.sine),
        Actions.moveBy(amount, 0, 0.2f, Interpolation.sine)
      ),
      updateShipDisplayAction,
      Actions.moveTo(origin, 0),
      Actions.parallel(
        Actions.fadeIn(0.2f, Interpolation.sine),
        Actions.moveBy(amount, 0, 0.2f, Interpolation.sine)
      ),
      Actions.forever(
        Actions.sequence(
          Actions.moveBy(0, 8, 4.0f),
          Actions.moveBy(0, -8, 4.0f)
        )
      )
    ))
  }


  def updateShipDetails(): Unit = {
    shipDetail.clear()

    val shipDetailTop: Table = new Table
    val shipName: String = allShips(currentShipIndex).getName
    val shipDesc: String = allShips(currentShipIndex).getDescription
    val shipNameLabel: Label = new Label(shipName, ResourceManager.labelMenuStyle)
    shipNameLabel.setAlignment(Align.center, Align.center)
    val shipDescLabel: Label = new Label(shipDesc, ResourceManager.labelMediumStyle)
    shipDescLabel.setAlignment(Align.center, Align.center)
    shipDescLabel.setWrap(true)

    shipDetailTop.add(shipNameLabel).expand.width(660).height(30)
    shipDetailTop.row
    shipDetailTop.add(shipDescLabel).expand.width(660).height(50)

    val shipDetailBottom: Table = new Table
    val shipPointMap: Map[String, Int] = allShips(currentShipIndex).getInstallPoints
    for ((k, v) <- shipPointMap) {
      val pointTable: Table = new Table
      pointTable.setBackground(ResourceManager.greenButtonNp0)
      val pointKey: Label = new Label(k, ResourceManager.labelMediumStyle)
      pointKey.setAlignment(Align.center)
      val pointVal: Label = new Label(v.toString, ResourceManager.labelMenuStyle)
      pointVal.setAlignment(Align.center)

      pointTable.add(pointKey).expand.fill.top
      pointTable.row
      pointTable.add(pointVal).expand.fill.bottom

      shipDetailBottom.add(pointTable).width(100).height(70).pad(4)
    }

    shipDetail.add(shipDetailTop).expand.height(80).top.pad(4)
    shipDetail.row
    shipDetail.add(shipDetailBottom).expand.height(80).top.pad(4)

    shipDetail.addAction(Actions.sequence(
      Actions.color(Constants.flashColor, 0.25f, Interpolation.sine),
      Actions.color(Constants.normalColor, 0.25f, Interpolation.sine)
    ))
  }



  /**
    * Custom Scene2D Actions
    */
  private[startscreen] var updateShipDisplayAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      val shipName: String = allShips(currentShipIndex).getName
      shipImage.setDrawable(new TextureRegionDrawable(ResourceManager.shipAtlas.findRegion(shipName)))

      if (!shipImage.hasParent) {
        shipDisplay.add(shipImage).pad(60)
      }
      true
    }
  }
}
