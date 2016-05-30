package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util.{Constants, ResourceManager}

import scala.collection.mutable.ArrayBuffer


class PartSelectionPanel extends Table {

  construct()


  def getParts: ArrayBuffer[String] = {
    null
  }

  private def construct(): Unit = {
    val partTable: Table = createPartTable

    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)
    bottomTable.setColor(Constants.normalColor)

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
}
