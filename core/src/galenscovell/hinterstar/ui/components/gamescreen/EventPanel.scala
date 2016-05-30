package galenscovell.hinterstar.ui.components.gamescreen

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.processing.EventContainer
import galenscovell.hinterstar.util.ResourceManager


class EventPanel(gameStage: GameStage, eventContainer: EventContainer) extends Table {
  private final val root: GameStage = gameStage
  private final val container: EventContainer = eventContainer

  construct()

  // Event handling --
  //  Location entered: events generated
  //  Next animation completed: event panel created using parsed event data
  //  Event choice selected: event resolved, location moves event pointer to next event


  def construct(): Unit = {
    this.setFillParent(true)
    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.npTest4)

    val titleTable: Table = createTitleTable(container.name)

    val centerTable: Table = new Table
    val descriptionTable: Table = createDescriptionTable(container.description)
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

    for (choice <- container.choices) {
      val choiceText: String = choice.get("choice-text") match {
        case Some(a) => s"$a"
        case None    => "ERROR: Choice text not found"
      }
      val choiceButton: TextButton = new TextButton(choiceText, ResourceManager.buttonEventStyle)
      choiceButton.getLabel.setAlignment(Align.left, Align.left)
      choiceButton.getLabel.setWrap(true)
      choiceButton.getLabelCell.padLeft(20).padRight(20)
      optionTable.add(choiceButton).width(700).height(60).expand.fill.top.padTop(2).padBottom(2)
      optionTable.row
    }

    optionTable
  }
}
