package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager

import scala.util.Random


class TeamSelectionPanel extends Table {
  private val teamMates: Array[String] = Array[String]("", "", "", "", "", "")
  private val nameInput: TextField = new TextField("", ResourceManager.textFieldStyle)
  private var currentTeamMember: Int = 0
  private var currentTeamButton: TextButton = null
  private var currentProfessionButton: TextButton = null

  private var engineerButton: TextButton = null
  private var physicianButton: TextButton = null
  private var soldierButton: TextButton = null
  private var researchButton: TextButton = null
  private var pilotButton: TextButton = null
  private var artistButton: TextButton = null
  private var psychiatristButton: TextButton = null
  private var linguistButton: TextButton = null

  nameInput.setMaxLength(18)
  randomizeStartingTeamNames()
  construct()


  def getTeammates: Array[String] = {
    teamMates
  }

  private def construct(): Unit = {
    val professionTable: Table = constructProfessionTable

    // LEFT TABLE
    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)
    val teamTable: Table = constructTeamTable
    leftTable.add(teamTable).expand.fill


    // RIGHT TABLE
    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)

    val optionTable: Table = new Table
    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Name", ResourceManager.labelMenuStyle)
    nameLabel.setAlignment(Align.center)
    nameTable.add(nameLabel).expand.fill.height(40).padBottom(16)
    nameTable.row
    nameTable.add(nameInput).expand.fill.height(40).pad(4)

    optionTable.add(nameTable).expand.width(320).height(50).pad(4)
    optionTable.row
    optionTable.add(professionTable).expand.width(320).height(220).pad(4)

    val modifyTeammateButton: TextButton = new TextButton("Modify", ResourceManager.greenButtonStyle)
    modifyTeammateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        val inputName: String = nameInput.getText
        val inputProfession: String = currentProfessionButton.getLabel.getText.toString
        teamMates(currentTeamMember) = f"$inputName\t$inputProfession"
        val newTeamTable: Table = constructTeamTable
        leftTable.clear()
        leftTable.add(newTeamTable).expand.width(340).height(400)
      }
    })

    rightTable.add(optionTable).expand.fill.width(340).height(350)
    rightTable.row
    rightTable.add(modifyTeammateButton).width(320).height(50).padBottom(10).center

    add(leftTable).width(340).height(400).left.padRight(10)
    add(rightTable).width(340).height(400).right
  }

  def constructTeamTable: Table = {
    val teamTable: Table = new Table
    for (teammate <- teamMates.indices) {
      val teamEntry: String = teamMates(teammate)
      val splitString: Array[String] = teamEntry.split("\t")
      val name: String = splitString(0)
      val profession: String = splitString(1)

      val memberTable: Table = new Table
      val memberButton: TextButton = new TextButton("", ResourceManager.toggleButtonStyle)
      memberButton.setText(f"$name\n$profession")
      val iconTable: Table = new Table  // Icon for profession
      iconTable.setBackground(ResourceManager.blueButtonNp0)

      if (currentTeamButton == null) {
        currentTeamButton = memberButton
        currentTeamButton.setChecked(true)
        nameInput.setText(name)
      }
      if (currentProfessionButton == null) {
        profession match {
          case "Engineer" => currentProfessionButton = engineerButton
          case "Physician" => currentProfessionButton = physicianButton
          case "Soldier" => currentProfessionButton = soldierButton
          case "Researcher" => currentProfessionButton = researchButton
          case "Pilot" => currentProfessionButton = pilotButton
          case "Artist" => currentProfessionButton = artistButton
          case "Psychiatrist" => currentProfessionButton = psychiatristButton
          case "Linguist" => currentProfessionButton = linguistButton
        }
        currentProfessionButton.setChecked(true)
      }

      memberButton.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          currentTeamMember = teammate
          currentTeamButton.setChecked(false)
          if (currentTeamButton != memberButton) {
            currentTeamButton = memberButton
            nameInput.setText(name)
          }
          currentTeamButton.setChecked(true)
          currentProfessionButton.setChecked(false)
          if (currentProfessionButton.getText.toString != profession) {
            profession match {
              case "Engineer" => currentProfessionButton = engineerButton
              case "Physician" => currentProfessionButton = physicianButton
              case "Soldier" => currentProfessionButton = soldierButton
              case "Researcher" => currentProfessionButton = researchButton
              case "Pilot" => currentProfessionButton = pilotButton
              case "Artist" => currentProfessionButton = artistButton
              case "Psychiatrist" => currentProfessionButton = psychiatristButton
              case "Linguist" => currentProfessionButton = linguistButton
            }
          }
          currentProfessionButton.setChecked(true)
        }
      })

      memberTable.add(memberButton).width(256).height(62).pad(2)
      memberTable.add(iconTable).width(70).height(62).pad(2)

      teamTable.add(memberTable).width(330).height(64).pad(1)
      teamTable.row
    }
    teamTable
  }

  private def constructProfessionTable: Table = {
    val professionTable: Table = new Table
    engineerButton = new TextButton("Engineer", ResourceManager.toggleButtonStyle)
    engineerButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(engineerButton)
      }
    })
    physicianButton = new TextButton("Physician", ResourceManager.toggleButtonStyle)
    physicianButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(physicianButton)
      }
    })
    soldierButton = new TextButton("Soldier", ResourceManager.toggleButtonStyle)
    soldierButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(soldierButton)
      }
    })
    researchButton = new TextButton("Researcher", ResourceManager.toggleButtonStyle)
    researchButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(researchButton)
      }
    })
    pilotButton = new TextButton("Pilot", ResourceManager.toggleButtonStyle)
    pilotButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(pilotButton)
      }
    })
    artistButton = new TextButton("Artist", ResourceManager.toggleButtonStyle)
    artistButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(artistButton)
      }
    })
    psychiatristButton = new TextButton("Psychiatrist", ResourceManager.toggleButtonStyle)
    psychiatristButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(psychiatristButton)
      }
    })
    linguistButton = new TextButton("Linguist", ResourceManager.toggleButtonStyle)
    linguistButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        refreshProfessionButtons(linguistButton)
      }
    })
    professionTable.add(engineerButton).width(150).height(50).pad(4)
    professionTable.add(physicianButton).width(150).height(50).pad(4)
    professionTable.row
    professionTable.add(soldierButton).width(150).height(50).pad(4)
    professionTable.add(researchButton).width(150).height(50).pad(4)
    professionTable.row
    professionTable.add(pilotButton).width(150).height(50).pad(4)
    professionTable.add(artistButton).width(150).height(50).pad(4)
    professionTable.row
    professionTable.add(psychiatristButton).width(150).height(50).pad(4)
    professionTable.add(linguistButton).width(150).height(50).pad(4)
    professionTable
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
      "Engineer", "Physician", "Soldier", "Researcher", "Pilot",
      "Artist", "Psychiatrist", "Linguist", "Botanist", ""
    )
    val random: Random = new Random
    for (x <- 0 until 6) {
      val randomNameIndex: Int = random.nextInt(names.length - 1)
      val randomName: String = names(randomNameIndex)
      val defaultProfession: String = professions(x)
      teamMates(x) = f"$randomName\t$defaultProfession"
    }
  }

  private def refreshProfessionButtons(textButton: TextButton): Unit = {
    currentProfessionButton.setChecked(false)
    if (currentProfessionButton != null && currentProfessionButton != textButton) {
      currentProfessionButton = textButton
    }
    currentProfessionButton.setChecked(true)
  }
}
