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
    mainTable.setBackground(ResourceManager.npTest4)

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
    titleTable.setBackground(ResourceManager.npTest1)
    val titleLabel: Label = new Label(eventTitle, ResourceManager.labelTitleStyle)
    titleLabel.setAlignment(Align.center, Align.center)
    titleTable.add(titleLabel)
    titleTable
  }

  private def createDescriptionTable(eventDescription: String): Table = {
    val descriptionTable: Table = new Table
    val descriptionLabel: Label = new Label(eventDescription, ResourceManager.labelDetailStyle)
    descriptionLabel.setWrap(true)
    descriptionLabel.setAlignment(Align.center, Align.left)
    descriptionTable.add(descriptionLabel).width(680)
    descriptionTable
  }

  private def createOptionTable: Table = {
    val optionTable: Table = new Table
    optionTable.setBackground(ResourceManager.npTest1)
    val debugButton1: TextButton = new TextButton(
      "Choose to continue (pick this)",
      ResourceManager.buttonEventStyle
    )
    debugButton1.getLabel.setWrap(true)
    val debugButton2: TextButton = new TextButton(
      "Don't choose to continue (useless button)",
      ResourceManager.buttonEventStyle
    )
    debugButton2.getLabel.setWrap(true)
    val debugButton3: TextButton = new TextButton(
      "Don't choose to continue (useless button)",
      ResourceManager.buttonEventStyle
    )
    debugButton3.getLabel.setWrap(true)
    val debugButton4: TextButton = new TextButton(
      "Don't choose to continue (useless button)",
      ResourceManager.buttonEventStyle
    )
    debugButton4.getLabel.setWrap(true)
    val debugButton5: TextButton = new TextButton(
      "Don't choose to continue (useless button)",
      ResourceManager.buttonEventStyle
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
