package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.{Constants, ResourceManager}
import galenscovell.hinterstar.things.parts.{Part, PartParser}

import scala.collection.mutable.{ArrayBuffer, Map}

// UNUSED! Introduced too much complication to the new game process

class PartSelectionPanel extends Table {
  private var partTableLeft: Table = null
  private var partTableRight: Table = null
  private var selectedPart: TextButton = null
  private val chosenParts: ArrayBuffer[Part] = ArrayBuffer()

  private var currentPartType: TextButton = null
  private val partTypes: List[String] = List("Combat", "Engine", "Hull", "Power", "Shield", "Storage")
  private val partMap: Map[String, Array[Part]] = createPartMap

  construct()


  def getParts: ArrayBuffer[Part] = {
    chosenParts
  }

  private def construct(): Unit = {
    val partTable: Table = createPartTable
    val bottomTable: Table = createBottomTable

    add(partTable).width(690).height(310).center
    row
    add(bottomTable).width(690).height(85).center.padTop(5)
  }

  private def createPartTable: Table = {
    val partTable: Table = new Table

    partTableLeft = new Table
    partTableLeft.setBackground(ResourceManager.npTest1)
    partTableLeft.setColor(Constants.normalColor)

    partTableRight = new Table
    partTableRight.setBackground(ResourceManager.npTest1)
    partTableRight.setColor(Constants.normalColor)

    partTable.add(partTableLeft).width(340).height(300).left.padRight(10)
    partTable.add(partTableRight).width(340).height(300).right

    partTable
  }

  private def createBottomTable: Table = {
    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)
    bottomTable.setColor(Constants.normalColor)

    val typeButtonsTable: Table = new Table

    for (x <- partTypes.indices) {
      val typeButton: TextButton = new TextButton(partTypes(x), ResourceManager.toggleButtonStyle)
      typeButton.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          if (currentPartType == null) {
            currentPartType = typeButton
          } else if (currentPartType != typeButton) {
            currentPartType.setChecked(false)
            currentPartType = typeButton
          }
          currentPartType.setChecked(true)
          displayParts(typeButton.getText.toString)
        }
      })
      typeButtonsTable.add(typeButton).width(102).height(50).pad(5)
    }

    bottomTable.add(typeButtonsTable).expand.fill

    bottomTable
  }

  private def createPartMap: Map[String, Array[Part]] = {
    val partParser: PartParser = new PartParser
    val map: Map[String, Array[Part]] = Map()

    for (partType: String <- partTypes) {
      val parts: Array[Part] = partParser.parseAll(partType, "rank-1")
      map.put(partType, parts)
    }
    map
  }

  private def displayParts(partType: String): Unit = {
    val parts: Array[Part] = partMap.get(partType).get
    updatePartLeftTable(parts)
  }

  private def updatePartLeftTable(partsArray: Array[Part]): Unit = {
    val containingTable: Table = new Table

    for (p: Part <- partsArray) {
      val partTable: Table = new Table
      val choiceButton: TextButton = new TextButton("X", ResourceManager.greenButtonStyle)
      val partButton: TextButton = new TextButton(p.getName, ResourceManager.toggleButtonStyle)
      partButton.addListener(new ClickListener() {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          if (selectedPart != null) {
            selectedPart.setChecked(false)
          }
          selectedPart = partButton
          updatePartRightTable(p)
        }
      })
      partTable.add(choiceButton).width(80).expand.fill
      partTable.add(partButton).width(240).expand.fill
      containingTable.add(partTable).height(54).expand.fill
      containingTable.row
    }

    partTableLeft.clear()
    partTableRight.clear()
    partTableLeft.add(containingTable).expand.fill
  }

  private def updatePartRightTable(p: Part): Unit = {
    val containingTable: Table = new Table

    val nameTable: Table = new Table
    nameTable.setBackground(ResourceManager.npTest3)
    val nameLabel: Label = new Label(p.getName, ResourceManager.labelMenuStyle)
    nameLabel.setAlignment(Align.center, Align.left)
    nameTable.add(nameLabel).expand.fill.pad(10)

    val descTable: Table = new Table
    descTable.setBackground(ResourceManager.npTest4)
    val descLabel: Label = new Label(p.getDescription, ResourceManager.labelDetailStyle)
    descLabel.setAlignment(Align.top, Align.left)
    descTable.add(descLabel).expand.fill.pad(10)

    val statTable: Table = new Table
    val statLabel: Label = new Label("+" + p.getStat.toString + " " + p.getType, ResourceManager.labelMenuStyle)
    statLabel.setAlignment(Align.center, Align.left)
    statTable.add(statLabel).expand.fill.pad(10)

    val energyTable: Table = new Table
    val energyLabel: Label = new Label("Power Consumption: " + p.getEnergyRequired.toString, ResourceManager.labelMenuStyle)
    energyLabel.setAlignment(Align.center, Align.left)
    energyTable.add(energyLabel).expand.fill.pad(10)

    containingTable.add(nameTable).height(40).expand.fill
    containingTable.row
    containingTable.add(descTable).height(140).expand.fill
    containingTable.row
    containingTable.add(statTable).height(40).expand.fill
    containingTable.row
    containingTable.add(energyTable).height(40).expand.fill

    partTableRight.clear()
    partTableRight.add(containingTable).expand.fill
  }
}
