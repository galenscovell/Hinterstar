package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Image, Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.{ClickListener, TextureRegionDrawable}
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent}
import com.badlogic.gdx.utils.{Align, Scaling}
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.{Ship, ShipParser}
import galenscovell.hinterstar.util._


class ShipSelectPanel extends Table {
  private val allShips: Array[Ship] = new ShipParser().parseAll.toArray
  private var currentShipIndex: Int = 0

  private val shipDisplay: Table = new Table
  private val shipImage: Image = new Image
  private val shipDetail: Table = new Table

  shipImage.setScaling(Scaling.fillY)
  shipDetail.setBackground(Resources.npTest1)
  shipDetail.setColor(Constants.NORMAL_UI_COLOR)

  construct()


  def getShip: Ship = {
    allShips(currentShipIndex)
  }

  private def construct(): Unit = {
    val topTable: Table = createTopTable
    val bottomTable: Table = new Table

    bottomTable.add(shipDetail).expand.fill

    add(topTable).width(780).height(160).center
    row
    add(bottomTable).width(780).height(220).padTop(10).padBottom(10).center
  }

  private def createTopTable: Table = {
    val topTable: Table = new Table

    val scrollLeftButton: TextButton = new TextButton("<", Resources.blueButtonStyle)
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
    val scrollRightButton: TextButton = new TextButton(">", Resources.blueButtonStyle)
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

    topTable.add(scrollLeftButton).width(60).height(140).expand.fill.left
    topTable.add(shipDisplay).expand.fill
    topTable.add(scrollRightButton).width(60).height(140).expand.fill.right

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
          Actions.moveBy(0, 6, 5.0f),
          Actions.moveBy(0, -6, 5.0f)
        )
      )
    ))
  }

  def updateShipDetails(): Unit = {
    shipDetail.clear()

    val shipDetailTop: Table = new Table
    shipDetailTop.setBackground(Resources.npTest1)
    val shipName: String = allShips(currentShipIndex).getName
    val shipDesc: String = allShips(currentShipIndex).getDescription
    val shipNameLabel: Label = new Label(shipName, Resources.labelMenuStyle)
    shipNameLabel.setAlignment(Align.center, Align.center)
    val shipDescLabel: Label = new Label(shipDesc, Resources.labelMediumStyle)
    shipDescLabel.setAlignment(Align.top, Align.center)
    shipDescLabel.setWrap(true)

    shipDetailTop.add(shipNameLabel).expand.fill.height(20).pad(5)
    shipDetailTop.row
    shipDetailTop.add(shipDescLabel).expand.fill.height(60).pad(5)

    val shipDetailBottom: Table = new Table

    val shipWeaponTable: Table = new Table
    for (weapon: Weapon <- allShips(currentShipIndex).getWeapons) {
      val weaponTable: Table = new Table
      weaponTable.setBackground(Resources.npTest4)

      val topBarTable: Table = new Table
      topBarTable.setBackground(Resources.npTest3)
      val iconTable = new Table
      iconTable.setBackground(Resources.npTest0)
      val damageLabel: Label = new Label(weapon.getDamage.toString, Resources.labelDetailStyle)
      damageLabel.setAlignment(Align.center)

      topBarTable.add(iconTable).expand.fill.width(20).height(20)
      topBarTable.add(damageLabel).expand.fill.width(80).height(20)

      val weaponImage: Table = new Table

      val bottomWeaponTable: Table = new Table
      bottomWeaponTable.setBackground(Resources.npTest1)
      val weaponLabel: Label = new Label(weapon.getName, Resources.labelDetailStyle)
      weaponLabel.setAlignment(Align.center)

      bottomWeaponTable.add(weaponLabel).expand.fill

      weaponTable.add(topBarTable).expand.fill.height(20)
      weaponTable.row
      weaponTable.add(weaponImage).expand.fill.height(50)
      weaponTable.row
      weaponTable.add(bottomWeaponTable).expand.fill.height(20)

      shipWeaponTable.add(weaponTable).width(100).pad(5)
    }

    val shipSubsystemTable: Table = new Table
    for (subsystem: String <- allShips(currentShipIndex).getSubsystems) {
      val subsystemTable: Table = new Table
      subsystemTable.setBackground(Resources.npTest4)

      val subsystemLabel: Label = new Label(subsystem, Resources.labelTinyStyle)
      subsystemLabel.setAlignment(Align.center)
      subsystemTable.add(subsystemLabel).expand.fill.pad(5)

      shipSubsystemTable.add(subsystemTable).width(120).pad(5)
    }

    shipDetailBottom.add(shipWeaponTable).expand.fill.height(95).pad(4)
    shipDetailBottom.row
    shipDetailBottom.add(shipSubsystemTable).expand.fill.height(30).pad(4)

    shipDetail.add(shipDetailTop).expand.fill.height(95).top.pad(5)
    shipDetail.row
    shipDetail.add(shipDetailBottom).expand.fill.height(125).top.pad(5)

    shipDetail.addAction(Actions.sequence(
      Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
      Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine)
    ))
  }



  /**
    * Custom Scene2D Actions
    */
  private[startscreen] var updateShipDisplayAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      val shipName: String = allShips(currentShipIndex).getName
      shipImage.setDrawable(new TextureRegionDrawable(Resources.shipAtlas.findRegion(shipName)))

      if (!shipImage.hasParent) {
        shipDisplay.add(shipImage).height(130)
      }
      true
    }
  }
}
