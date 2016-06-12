package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.{Constants, ResourceManager}
import galenscovell.hinterstar.things.parts.{Part, PartParser}

import scala.collection.mutable.{ArrayBuffer, Map}


class PartSelectionPanel extends Table {
  private val moneyLabel: Label = new Label("$100,000.00", ResourceManager.labelMenuStyle)
  moneyLabel.setAlignment(Align.center, Align.center)
  private var currentPartType: TextButton = null
  private val partTypes: List[String] = List("Combat", "Engine", "Hull", "Power", "Shield", "Storage")
  private val partMap: Map[String, Array[Part]] = createPartMap

  construct()


  def getParts: ArrayBuffer[String] = {
    null
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

    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)
    leftTable.setColor(Constants.normalColor)

    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)
    rightTable.setColor(Constants.normalColor)

    partTable.add(leftTable).width(340).height(300).left.padRight(10)
    partTable.add(rightTable).width(340).height(300).right

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

    val moneyTable: Table = new Table
    moneyTable.add(moneyLabel).expand.fill.center

    bottomTable.add(typeButtonsTable).expand.height(60)
    bottomTable.row
    bottomTable.add(moneyTable).expand.pad(2)

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

    for (p: Part <- parts) {
      println(p.getName)
    }
  }
}
