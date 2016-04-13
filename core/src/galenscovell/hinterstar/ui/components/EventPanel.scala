package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager


class EventPanel(gameStage: GameStage) extends Table {
  private final val root: GameStage = gameStage

  construct()


  def construct(): Unit = {
    this.setFillParent(true)

    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.np_test4)

    val titleTable: Table = createTitleTable("Event Name")

    val centerTable: Table = new Table
    val descriptionTable: Table = createDescriptionTable(
      """This is the event text describing exactly in painstaking detail
        |what this event is all about. It can be multiple lines and sentences long
        |which should be taken into account. Scala has some convenient ways of
        |dealing with such long strings, and libGDX shouldn't have any problem
        |with it.""".stripMargin.replaceAll("\n", " ")
    )
    val optionTable: Table = createOptionTable

    centerTable.add(descriptionTable).height(100).expand.fill.top
    centerTable.row
    centerTable.add(optionTable).width(700).height(270).expand.fill

    mainTable.add(titleTable).height(50).expand.fill.top
    mainTable.row
    mainTable.add(centerTable).height(370).expand.fill.top

    this.add(mainTable).width(700).height(420).expand.fill.center
  }

  private def createTitleTable(eventTitle: String): Table = {
    val titleTable: Table = new Table
    titleTable.setBackground(ResourceManager.np_test1)
    val titleLabel: Label = new Label(eventTitle, ResourceManager.label_titleStyle)
    titleLabel.setAlignment(Align.center, Align.center)
    titleTable.add(titleLabel)
    titleTable
  }

  private def createDescriptionTable(eventDescription: String): Table = {
    val descriptionTable: Table = new Table
    val descriptionLabel: Label = new Label(eventDescription, ResourceManager.label_detailStyle)
    descriptionLabel.setWrap(true)
    descriptionLabel.setAlignment(Align.center, Align.left)
    descriptionTable.add(descriptionLabel).width(680)
    descriptionTable
  }

  private def createOptionTable: Table = {
    val optionTable: Table = new Table
    optionTable.setBackground(ResourceManager.np_test1)
    val debugButton1: TextButton = new TextButton(
      "Choose to continue the game -- pick this (you have a brain)",
      ResourceManager.button_eventStyle
    )
    debugButton1.getLabel.setWrap(true)
    val debugButton2: TextButton = new TextButton(
      "Don't choose to continue the game (useless button)",
      ResourceManager.button_eventStyle
    )
    debugButton2.getLabel.setWrap(true)
    val debugButton3: TextButton = new TextButton(
      "Don't choose to continue the game (useless button)",
      ResourceManager.button_eventStyle
    )
    debugButton3.getLabel.setWrap(true)
    val debugButton4: TextButton = new TextButton(
      "Don't choose to continue the game (useless button)",
      ResourceManager.button_eventStyle
    )
    debugButton4.getLabel.setWrap(true)
    val debugButton5: TextButton = new TextButton(
      "Don't choose to continue the game (useless button)",
      ResourceManager.button_eventStyle
    )
    debugButton5.getLabel.setWrap(true)

    optionTable.add(debugButton1).width(700).height(50).expand.fill.top.padTop(2).padBottom(2)
    optionTable.row
    optionTable.add(debugButton2).width(700).height(50).expand.fill.top.padTop(2).padBottom(2)
    optionTable.row
    optionTable.add(debugButton3).width(700).height(50).expand.fill.top.padTop(2).padBottom(2)
    optionTable.row
    optionTable.add(debugButton4).width(700).height(50).expand.fill.top.padTop(2).padBottom(2)
    optionTable.row
    optionTable.add(debugButton5).width(700).height(50).expand.fill.top.padTop(2).padBottom(2)
    optionTable
  }
}
