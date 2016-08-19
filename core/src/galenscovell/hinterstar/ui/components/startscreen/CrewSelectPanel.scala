package galenscovell.hinterstar.ui.components.startscreen

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

  private val crewTable: Table = new Table

  construct()


  def getCrewmates: Array[Crewmate] = {
    crewmates
  }

  private def construct(): Unit = {
    crewTable.setBackground(Resources.npTest1)
    crewTable.setColor(Constants.NORMAL_UI_COLOR)

    updateCrewTable()

    add(crewTable).expand.fill
  }

  private def updateCrewTable(): Unit = {
    crewTable.clear()
    var teamTable: Table = constructCrewTable
    teamTable = constructCrewTable
    crewTable.add(teamTable).expand.fill
  }

  private def updateShipTable(): Unit = {

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
          currentCrewmate = crewmate
          currentCrewButton.setChecked(false)
          if (currentCrewButton != button) {
            currentCrewButton = button
            nameInput.setText(crewmate.getName)
          }
          currentCrewButton.setChecked(true)
        }
      })

      memberTable.add(button).width(160).height(60).pad(4)
      memberTable.add(iconTable).width(70).height(60).pad(4)

      crewTable.add(memberTable).expand.fill.height(60).pad(2)
      crewTable.row
    }

    val nameTable: Table = new Table
    val nameLabel: Label = new Label("Crewmate", Resources.labelMenuStyle)
    nameLabel.setAlignment(Align.center)

    val modifyCrewmateButton: TextButton = new TextButton("Update Crewmate", Resources.greenButtonStyle)
    modifyCrewmateButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        currentCrewmate.setName(nameInput.getText)
        crewTable.addAction(Actions.sequence(
          updateLeftTableAction
        ))
      }
    })

    nameTable.add(nameLabel).expand.fill.height(30).padBottom(2)
    nameTable.row
    nameTable.add(nameInput).expand.fill.height(40).pad(2)

    crewTable.add(nameTable).expand.fill.height(70).pad(2)
    crewTable.row
    crewTable.add(modifyCrewmateButton).expand.fill.height(50).pad(10)

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
      updateCrewTable()
      true
    }
  }
}
