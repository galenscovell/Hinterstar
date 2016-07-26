package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util._

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class CrewSelectPanel extends Table {
  private val teamMates: Array[String] = randomizeStartingTeamNames
  private val proficiencies: List[String] =
    List("Piloting", "Engines", "Shields", "Weapons", "Combat", "Repair")

  private var currentTeammate: String = _
  private var currentTeamButton: TextButton = _
  private val nameInput: TextField = new TextField("", Resources.textFieldStyle)
  nameInput.setAlignment(Align.left)
  nameInput.setMaxLength(20)

  private val leftTable: Table = new Table
  private val rightTable: Table = new Table

  construct()


  def getTeammates: Array[String] = {
    teamMates
  }

  private def construct(): Unit = {
    leftTable.setBackground(Resources.npTest1)
    leftTable.setColor(Constants.NORMAL_UI_COLOR)
    rightTable.setBackground(Resources.npTest1)
    rightTable.setColor(Constants.NORMAL_UI_COLOR)

    updateLeftTable()
    updateRightTable()

    add(leftTable).width(385).height(400).left.padRight(10)
    add(rightTable).width(385).height(400).right
  }

  private def updateLeftTable(): Unit = {
    leftTable.clear()

    var teamTable: Table = constructTeamTable
    val inputName: String = nameInput.getText

    teamTable = constructTeamTable

    leftTable.add(teamTable).expand.fill
  }

  private def updateRightTable(): Unit = {
    val optionTable: Table = new Table
    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Crewmate", Resources.labelMenuStyle)
    nameLabel.setAlignment(Align.center)

    nameTable.add(nameLabel).expand.fill.height(40).padBottom(16)
    nameTable.row
    nameTable.add(nameInput).expand.fill.height(40).pad(10)

    optionTable.add(nameTable).expand.fill.height(50).pad(4)
    optionTable.row

    val modifyTeammateButton: TextButton = new TextButton("Update Crewmate", Resources.greenButtonStyle)
    modifyTeammateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        leftTable.addAction(Actions.sequence(
          Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
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
    for (teammate: String <- teamMates) {
      val memberTable: Table = new Table
      val memberButton: TextButton = new TextButton("", Resources.toggleButtonStyle)
      memberButton.setText(teammate)
      val iconTable: Table = new Table  // Crewmate icon
      iconTable.setBackground(Resources.greenButtonNp0)

      if (currentTeamButton == null) {
        currentTeamButton = memberButton
        currentTeamButton.setChecked(true)
        nameInput.setText(teammate)
      }

      memberButton.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          rightTable.addAction(Actions.sequence(
            Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
            Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine)
          ))

          currentTeammate = teammate
          currentTeamButton.setChecked(false)
          if (currentTeamButton != memberButton) {
            currentTeamButton = memberButton
            nameInput.setText(teammate)
          }
          currentTeamButton.setChecked(true)
        }
      })

      memberTable.add(memberButton).width(252).height(58).pad(4)
      memberTable.add(iconTable).width(70).height(58).pad(4)

      teamTable.add(memberTable).expand.fill.height(64).pad(1)
      teamTable.row
    }
    teamTable
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

    val random: Random = new Random
    while (tempTeammates.length < 6) {
      val randomNameIndex: Int = random.nextInt(names.length - 1)
      val randomName: String = names(randomNameIndex)

      if (!tempTeammates.contains(randomName)) {
        tempTeammates.append(randomName)
      }
    }

    tempTeammates.toArray
  }



  /**
    * Custom Scene2D Actions
    */
  private[startscreen] var updateLeftTableAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      updateLeftTable()
      leftTable.addAction(Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine))
      true
    }
  }
}
