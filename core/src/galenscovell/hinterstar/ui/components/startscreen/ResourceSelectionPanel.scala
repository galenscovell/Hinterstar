package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.util.ResourceManager

import scala.collection.mutable.ArrayBuffer


class ResourceSelectionPanel extends Table {
  private val resources: ArrayBuffer[String] = new ArrayBuffer[String]()

  construct()


  def getResources: ArrayBuffer[String] = {
    resources
  }

  private def construct(): Unit = {
    val topTable: Table = new Table
    topTable.setBackground(ResourceManager.npTest1)

    val middleTable: Table = new Table
    middleTable.setBackground(ResourceManager.npTest1)

    val bottomTable: Table = new Table
    bottomTable.setBackground(ResourceManager.npTest1)

    add(topTable).width(700).height(140).center
    row
    add(middleTable).width(700).height(100).padTop(10).center
    row
    add(bottomTable).width(700).height(140).padTop(10).center
  }
}
