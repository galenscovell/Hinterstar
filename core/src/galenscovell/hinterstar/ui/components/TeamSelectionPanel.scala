package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager

import scala.collection.mutable.ArrayBuffer


class TeamSelectionPanel extends Table {
  private val teamMates: ArrayBuffer[String] = new ArrayBuffer[String]()

  construct()


  private def construct(): Unit = {
    // LEFT TABLE
    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)
    // Display created teammates and their details
    val teamTable: Table = new Table

    val removeTeammateButton: TextButton = new TextButton("Remove", ResourceManager.buttonMenuStyle)
    leftTable.add(teamTable).expand.width(340).height(350)
    leftTable.row
    leftTable.add(removeTeammateButton).expand.width(340).height(50)


    // RIGHT TABLE
    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)
    // Display teammate options: Name, profession, age?, gender?
    val optionTable: Table = new Table
    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Name: ", ResourceManager.labelMenuStyle)
    nameLabel.setAlignment(Align.left)
    val nameInput: TextField = new TextField("", ResourceManager.textFieldStyle)
    nameInput.setMaxLength(16)
    nameTable.add(nameLabel).width(50).height(40).pad(8)
    nameTable.add(nameInput).width(260).height(40).pad(8)

    val professionTable: Table = new Table
    val engineerButton: TextButton = new TextButton("Engineer", ResourceManager.buttonMenuStyle)
    val medicButton: TextButton = new TextButton("Medic", ResourceManager.buttonMenuStyle)
    val militaryButton: TextButton = new TextButton("Military", ResourceManager.buttonMenuStyle)
    val researchButton: TextButton = new TextButton("Researcher", ResourceManager.buttonMenuStyle)
    val pilotButton: TextButton = new TextButton("Pilot", ResourceManager.buttonMenuStyle)
    val artistButton: TextButton = new TextButton("Artist", ResourceManager.buttonMenuStyle)
    professionTable.add(engineerButton).width(160).height(50).pad(2)
    professionTable.add(medicButton).width(160).height(50).pad(2)
    professionTable.row
    professionTable.add(militaryButton).width(160).height(50).pad(2)
    professionTable.add(researchButton).width(160).height(50).pad(2)
    professionTable.row
    professionTable.add(pilotButton).width(160).height(50).pad(2)
    professionTable.add(artistButton).width(160).height(50).pad(2)

    optionTable.add(nameTable).expand.width(320).height(60).pad(8)
    optionTable.row
    optionTable.add(professionTable).expand.width(320).height(180).pad(4)

    val addTeammateButton: TextButton = new TextButton("Add", ResourceManager.buttonMenuStyle)
    rightTable.add(optionTable).expand.fill.width(340).height(350)
    rightTable.row
    rightTable.add(addTeammateButton).width(340).height(50)


    val nextButton: TextButton = new TextButton(">", ResourceManager.buttonMenuStyle)

    add(leftTable).width(340).height(400).pad(4)
    add(rightTable).width(340).height(400).pad(4)
    add(nextButton).width(80).height(400).pad(4)
  }

  def getTeammates: ArrayBuffer[String] = {
    teamMates
  }
}
