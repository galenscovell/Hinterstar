package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager

import scala.util.Random


class TeamSelectionPanel extends Table {
  private val teamMates: Array[String] = Array[String]("", "", "", "", "", "")
  private var nameInput: TextField = null
  private var currentTeamMember: Int = 0
  private var currentButton: TextButton = null

  construct()


  private def construct(): Unit = {
    randomizeStartingTeamNames

    // LEFT TABLE
    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)
    val teamTable: Table = constructTeamTable
    leftTable.add(teamTable).expand.width(340).height(400)


    // RIGHT TABLE
    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)

    val optionTable: Table = new Table
    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Name: ", ResourceManager.labelMenuStyle)
    nameLabel.setAlignment(Align.left)
    nameInput = new TextField("", ResourceManager.textFieldStyle)
    nameInput.setMaxLength(16)
    nameTable.add(nameLabel).width(50).height(40).pad(8)
    nameTable.add(nameInput).width(260).height(40).pad(8)

    val professionTable: Table = new Table
    val engineerButton: TextButton = new TextButton("Engineer", ResourceManager.toggleButtonStyle)
    val medicButton: TextButton = new TextButton("Medic", ResourceManager.toggleButtonStyle)
    val militaryButton: TextButton = new TextButton("Military", ResourceManager.toggleButtonStyle)
    val researchButton: TextButton = new TextButton("Researcher", ResourceManager.toggleButtonStyle)
    val pilotButton: TextButton = new TextButton("Pilot", ResourceManager.toggleButtonStyle)
    val artistButton: TextButton = new TextButton("Artist", ResourceManager.toggleButtonStyle)
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

    val modifyTeammateButton: TextButton = new TextButton("Modify", ResourceManager.buttonMenuStyle)
    modifyTeammateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        teamMates(currentTeamMember) = nameInput.getText
        val newTeamTable: Table = constructTeamTable
        leftTable.clear()
        leftTable.add(newTeamTable).expand.width(340).height(400)
      }
    })

    rightTable.add(optionTable).expand.fill.width(340).height(350)
    rightTable.row
    rightTable.add(modifyTeammateButton).width(340).height(50)


    val nextButton: TextButton = new TextButton(">", ResourceManager.buttonMenuStyle)

    add(leftTable).width(340).height(400).pad(4)
    add(rightTable).width(340).height(400).pad(4)
    add(nextButton).width(80).height(400).pad(4)
  }

  def getTeammates: Array[String] = {
    teamMates
  }

  def constructTeamTable: Table = {
    val teamTable: Table = new Table
    for (teammate <- teamMates.indices) {
      val teamEntry: String = teamMates(teammate)
      val memberTable: Table = new Table
      val memberButton: TextButton = new TextButton("", ResourceManager.toggleButtonStyle)
      val iconTable: Table = new Table  // Icon for profession
      iconTable.setBackground(ResourceManager.npTest3)
      if (teamEntry.length > 0) {
        val splitString: Array[String] = teamEntry.split("\t")
        val name: String = splitString(0)
        val profession: String = splitString(1)  // Set profession icon
        memberButton.setText(f"$name\n$profession")
      }
      memberTable.add(memberButton).width(256).height(62).pad(2)
      memberTable.add(iconTable).width(70).height(62).pad(2)

      teamTable.add(memberTable).width(330).height(64).pad(1)
      teamTable.row

      memberButton.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          currentTeamMember = teammate
          val memberName: String = memberButton.getText.toString.split("\n")(0)
          nameInput.setText(memberName)
          if (currentButton != null) {
            currentButton.setChecked(false)
          }
          currentButton = memberButton
          currentButton.setChecked(true)
        }
      })
    }
    teamTable
  }

  private def randomizeStartingTeamNames(): Unit = {
    val names: List[String] = List(
      "Jack", "James", "Benjamin", "Joshua", "Ryan", "Patrick", "Samuel",
      "William", "Kenji", "Ei", "Ren", "Ken", "Daniel", "Ethan", "Michael",
      "Tobias", "Alexander", "Noah", "Nathan", "Olivia", "Emily", "Jessica",
      "Ashley", "Isabella", "Riko", "Nanami", "Misaki", "Jennifer", "Magnus",
      "Lucas", "Adam", "Robert", "Sarah", "Hannah", "Ruby", "Chloe", "Lily"
    )
    val professions: List[String] = List(
      "Engineer", "Medic", "Military", "Research", "Pilot", "Artist"
    )
    val random: Random = new Random
    for (x <- 0 until 6) {
      val randomNameIndex: Int = random.nextInt(names.length - 1)
      val randomName: String = names(randomNameIndex)
      val defaultProfession: String = professions(x)
      teamMates(x) = f"$randomName\t$defaultProfession"
    }
  }
}
