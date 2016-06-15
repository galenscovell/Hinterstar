package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.{Constants, ResourceManager}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class TeamSelectionPanel extends Table {
  private val teamMates: Array[String] = randomizeStartingTeamNames

  private var currentTeammate: Int = 0
  private var currentTeamButton: TextButton = null
  private var currentProfessionButton: TextButton = null
  private val nameInput: TextField = new TextField("", ResourceManager.textFieldStyle)
  nameInput.setAlignment(Align.left)
  nameInput.setMaxLength(20)

  private var engineerButton: TextButton = null
  private var physicianButton: TextButton = null
  private var soldierButton: TextButton = null
  private var researchButton: TextButton = null
  private var pilotButton: TextButton = null
  private var artistButton: TextButton = null
  private var psychiatristButton: TextButton = null
  private var linguistButton: TextButton = null

  private val professionTable: Table = constructProfessionTable
  private val leftTable: Table = new Table
  private val rightTable: Table = new Table

  construct()


  def getTeammates: Array[String] = {
    teamMates
  }

  private def construct(): Unit = {
    leftTable.setBackground(ResourceManager.npTest1)
    leftTable.setColor(Constants.normalColor)
    rightTable.setBackground(ResourceManager.npTest1)
    rightTable.setColor(Constants.normalColor)

    updateLeftTable()
    updateRightTable()

    add(leftTable).width(385).height(400).left.padRight(10)
    add(rightTable).width(385).height(400).right
  }

  private def updateLeftTable(): Unit = {
    leftTable.clear()

    var teamTable: Table = null
    if (currentProfessionButton == null) {
      // If profession button hasn't been set, initialize teamTable for the first time
      teamTable = constructTeamTable
    } else {
      // Otherwise create new team table using updated selection info
      val inputName: String = nameInput.getText
      val inputProfession: String = currentProfessionButton.getLabel.getText.toString
      teamMates(currentTeammate) = s"$inputName\t$inputProfession"
      teamTable = constructTeamTable
    }

    leftTable.add(teamTable).expand.fill
  }

  private def updateRightTable(): Unit = {
    val optionTable: Table = new Table
    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Teammate", ResourceManager.labelMenuStyle)
    nameLabel.setAlignment(Align.center)

    nameTable.add(nameLabel).expand.fill.height(40).padBottom(16)
    nameTable.row
    nameTable.add(nameInput).expand.fill.height(40).pad(10)

    optionTable.add(nameTable).expand.fill.height(50).pad(4)
    optionTable.row
    optionTable.add(professionTable).expand.fill.height(220).pad(4)

    val modifyTeammateButton: TextButton = new TextButton("Update Teammate", ResourceManager.greenButtonStyle)
    modifyTeammateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        leftTable.addAction(Actions.sequence(
          Actions.color(Constants.flashColor, 0.25f, Interpolation.sine),
          updateLeftTableAction
        ))
      }
    })

    rightTable.add(optionTable).expand.fill.height(350)
    rightTable.row
    rightTable.add(modifyTeammateButton).expand.fill.height(50).pad(10)
  }

  private def constructTeamTable: Table = {
    val teamTable: Table = new Table
    for (teammate <- teamMates.indices) {
      val teamEntry: String = teamMates(teammate)
      val splitString: Array[String] = teamEntry.split("\t")
      val name: String = splitString(0)
      val profession: String = splitString(1)

      val memberTable: Table = new Table
      val memberButton: TextButton = new TextButton("", ResourceManager.toggleButtonStyle)
      memberButton.setText(s"$name\n$profession")
      val iconTable: Table = new Table  // Icon for profession
      iconTable.setBackground(ResourceManager.greenButtonNp0)

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
          rightTable.addAction(Actions.sequence(
            Actions.color(Constants.flashColor, 0.25f, Interpolation.sine),
            Actions.color(Constants.normalColor, 0.25f, Interpolation.sine)
          ))

          currentTeammate = teammate
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

      memberTable.add(memberButton).width(252).height(58).pad(4)
      memberTable.add(iconTable).width(70).height(58).pad(4)

      teamTable.add(memberTable).expand.fill.height(64).pad(1)
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

    professionTable.add(engineerButton).width(155).height(50).pad(4)
    professionTable.add(physicianButton).width(155).height(50).pad(4)
    professionTable.row
    professionTable.add(soldierButton).width(155).height(50).pad(4)
    professionTable.add(researchButton).width(155).height(50).pad(4)
    professionTable.row
    professionTable.add(pilotButton).width(155).height(50).pad(4)
    professionTable.add(artistButton).width(155).height(50).pad(4)
    professionTable.row
    professionTable.add(psychiatristButton).width(155).height(50).pad(4)
    professionTable.add(linguistButton).width(155).height(50).pad(4)

    professionTable
  }

  private def randomizeStartingTeamNames: Array[String] = {
    val tempTeammates: ArrayBuffer[String] = ArrayBuffer()
    val names: List[String] = List(
      "Jack", "James", "Benjamin", "Joshua", "Ryan", "Patrick", "Samuel",
      "William", "Kenji", "Ei", "Ren", "Ken", "Daniel", "Ethan", "Michael",
      "Tobias", "Alexander", "Noah", "Nathan", "Magnus", "Lucas", "Adam",
      "Robert", "Ryou",
      "Olivia", "Emily", "Jessica", "Ashley", "Isabella", "Riko", "Nanami",
      "Misaki", "Jennifer", "Sarah", "Hannah", "Ruby", "Chloe", "Lily",
      "Sophia", "Maria", "Madison", "Chelsea", "Shelby", "Rachel", "Sakura",
      "Nadia", "Amelia"
    )
    val professions: List[String] = List(
      "Engineer", "Physician", "Soldier", "Researcher", "Pilot",
      "Artist", "Psychiatrist", "Linguist", "Botanist"
    )
    val random: Random = new Random

    while (tempTeammates.length < 6) {
      val randomNameIndex: Int = random.nextInt(names.length - 1)
      val randomProfIndex: Int = random.nextInt(professions.length - 1)
      val randomName: String = names(randomNameIndex)
      val randomProf: String = professions(randomProfIndex)
      val teammateEntry: String = s"$randomName\t$randomProf"
      var unique: Boolean = true

      for (entry: String <- tempTeammates) {
        val components: Array[String] = entry.split("\t")
        if (components(0) == randomName || components(1) == randomProf) {
          unique = false
        }
      }

      if (unique) {
        tempTeammates.append(teammateEntry)
      }
    }
    tempTeammates.toArray
  }

  private def refreshProfessionButtons(textButton: TextButton): Unit = {
    currentProfessionButton.setChecked(false)
    if (currentProfessionButton != null && currentProfessionButton != textButton) {
      currentProfessionButton = textButton
    }
    currentProfessionButton.setChecked(true)
  }



  /**
    * Custom Scene2D Actions
    */
  private[startscreen] var updateLeftTableAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      updateLeftTable()
      leftTable.addAction(Actions.color(Constants.normalColor, 0.25f, Interpolation.sine))
      true
    }
  }
}
