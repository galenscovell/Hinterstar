package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.scenes.scene2d.ui.Table

import scala.collection.mutable.ArrayBuffer


class ResourceSelectionPanel extends Table {
  private val resources: ArrayBuffer[String] = new ArrayBuffer[String]()

  construct()


  private def construct(): Unit = {

  }

  def getResources: ArrayBuffer[String] = {
    resources
  }
}
