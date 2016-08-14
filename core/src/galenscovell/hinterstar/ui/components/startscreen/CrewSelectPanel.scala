package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class CrewSelectPanel extends Table {
  private val crewmates: Array[Crewmate] = randomizeStartingTeamNames

  private var currentCrewmate: Crewmate = _
  private var currentCrewButton: TextButton = _
  private val nameInput: TextField = new TextField("", Resources.textFieldStyle)
  nameInput.setAlignment(Align.left)
  nameInput.setMaxLength(20)

  private val leftTable, rightTable: Table = new Table

  construct()


  def getCrewmates: Array[Crewmate] = {
    crewmates
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
    var teamTable: Table = constructCrewTable
    teamTable = constructCrewTable
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

    val modifyCrewmateButton: TextButton = new TextButton("Update Crewmate", Resources.greenButtonStyle)
    modifyCrewmateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentCrewmate.setName(nameInput.getText)
        leftTable.addAction(Actions.sequence(
          Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
          updateLeftTableAction
        ))
      }
    })

    rightTable.add(optionTable).expand.fill.height(350)
    rightTable.row
    rightTable.add(modifyCrewmateButton).expand.fill.height(50).pad(10)
  }

  private def constructCrewTable: Table = {
    val crewTable: Table = new Table

    for (crewmate: Crewmate <- crewmates) {
      val memberTable: Table = new Table
      val button: TextButton = new TextButton("", Resources.toggleButtonStyle)
      button.setText(crewmate.getName)
      val iconTable: Table = new Table  // Crewmate icon/sprite?
      iconTable.setBackground(Resources.greenButtonNp0)

      if (currentCrewButton == null) {
        currentCrewButton = button
        currentCrewButton.setChecked(true)
        nameInput.setText(crewmate.getName)
      }

      button.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          rightTable.addAction(Actions.sequence(
            Actions.color(Constants.FLASH_UI_COLOR, 0.25f, Interpolation.sine),
            Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine)
          ))

          currentCrewmate = crewmate
          currentCrewButton.setChecked(false)
          if (currentCrewButton != button) {
            currentCrewButton = button
            nameInput.setText(crewmate.getName)
          }
          currentCrewButton.setChecked(true)
        }
      })

      memberTable.add(button).width(252).height(70).pad(4)
      memberTable.add(iconTable).width(70).height(70).pad(4)

      crewTable.add(memberTable).expand.fill.height(75).pad(1)
      crewTable.row
    }
    crewTable
  }

  private def randomizeStartingTeamNames: Array[Crewmate] = {
    val randomNames: ArrayBuffer[String] = ArrayBuffer()

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
    while (randomNames.length < 3) {
      val randomNameIndex: Int = random.nextInt(names.length - 1)
      val randomName: String = names(randomNameIndex)

      if (!randomNames.contains(randomName)) {
        randomNames.append(randomName)
      }
    }

    val startingProficiencies: mutable.Map[String, Int] = mutable.Map(
      "Weapons" -> 0,
      "Engines" -> 0,
      "Piloting" -> 0,
      "Shields" -> 0
    )
    val startCrew: ArrayBuffer[Crewmate] = ArrayBuffer()
    for (name: String <- randomNames) {
      val newCrewmate: Crewmate = new Crewmate(name, startingProficiencies, "No Action", 100)
      startCrew.append(newCrewmate)
    }

    startCrew.toArray
  }



  /***************************
    * Custom Scene2D Actions *
    ***************************/
  private[startscreen] var updateLeftTableAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      updateLeftTable()
      leftTable.addAction(Actions.color(Constants.NORMAL_UI_COLOR, 0.25f, Interpolation.sine))
      true
    }
  }
}
