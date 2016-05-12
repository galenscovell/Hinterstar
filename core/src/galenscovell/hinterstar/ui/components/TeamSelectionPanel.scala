package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.{Table, TextButton}
import galenscovell.hinterstar.util.ResourceManager

import scala.collection.mutable.ArrayBuffer


class TeamSelectionPanel extends Table {
  private val teamMates: ArrayBuffer[String] = new ArrayBuffer[String]()

  construct()


  private def construct(): Unit = {
    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)
    // Display created teammates and their details
    val teamTable: Table = new Table

    val removeTeammateButton: TextButton = new TextButton("Remove", ResourceManager.buttonMenuStyle)
    leftTable.add(teamTable).expand.width(340).height(350)
    leftTable.row
    leftTable.add(removeTeammateButton).expand.width(340).height(50)

    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)
    // Display teammate options - Name, age, profession, gender
    val optionTable: Table = new Table

    val addTeammateButton: TextButton = new TextButton("Add", ResourceManager.buttonMenuStyle)
    rightTable.add(optionTable).expand.width(340).height(350)
    rightTable.row
    rightTable.add(addTeammateButton).expand.width(340).height(50)


    val nextButton: TextButton = new TextButton(">", ResourceManager.buttonMenuStyle)

    add(leftTable).width(340).height(400).pad(4)
    add(rightTable).width(340).height(400).pad(4)
    add(nextButton).width(80).height(400).pad(4)
  }

  def getTeammates: ArrayBuffer[String] = {
    teamMates
  }
}
