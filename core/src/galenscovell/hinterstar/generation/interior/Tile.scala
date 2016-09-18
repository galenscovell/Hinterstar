package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import galenscovell.hinterstar.util.{Constants, Resources}


class Tile(val tx: Int, val ty: Int, tileType: String) extends Group {
  private var neighbors: Array[Tile] = _
  private val sprite: Sprite = createSprite

  setX(getX - Gdx.graphics.getWidth / 2)
  createIcon()


  private def createSprite: Sprite = {
      tileType match {
        case "empty" => null
        case "ladder-up" => Resources.atlas.createSprite("tile-ladder-up")
        case "ladder-down" => Resources.atlas.createSprite("tile-ladder-down")
        case _ => Resources.atlas.createSprite("tile-empty")
      }
  }

  private def createIcon(): Unit = {
    var image: Image = null
    tileType match {
      case "Turret" => image = new Image(Resources.atlas.createSprite("icon_weapon"))
      case "Engine" => image = new Image(Resources.atlas.createSprite("icon_engine"))
      case "Helm" => image = new Image(Resources.atlas.createSprite("icon_helm"))
      case "Shield" => image = new Image(Resources.atlas.createSprite("icon_shield"))
      case "Medbay" => image = new Image(Resources.atlas.createSprite("icon_medbay"))
      case "Life Support" => image = new Image(Resources.atlas.createSprite("icon_oxygen"))
      case "Generator" => image = new Image(Resources.atlas.createSprite("icon_battery"))
      case "Sensor" => image = new Image(Resources.atlas.createSprite("icon_sensor"))
      case "Sleep Pod" => image = new Image(Resources.atlas.createSprite("icon_sleeppod"))
      case _ => Unit
    }

    if (image != null) {
      addActor(image)
      image.setSize(32, 32)
      image.setPosition(1 + getWidth / 2 + image.getWidth / 3, getHeight / 2 + image.getHeight + 4)
    }
  }


  /********************
    *     Getters     *
    ********************/
  def getTileType: String = {
    tileType
  }

  private def getThisRoom: Tile = {
    this
  }

  def isTraversible: Boolean = {
    tileType != "empty"
  }

  def getNeighbors: Array[Tile] = {
    neighbors
  }



  /********************
    *     Setters     *
    ********************/
  def setNeighbors(rooms: Array[Tile]): Unit = {
    neighbors = rooms
  }



  /**********************
    *     Rendering     *
    **********************/
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    if (isTraversible) {
      batch.draw(sprite, getX, getY, Constants.TILE_WIDTH, Constants.TILE_HEIGHT)
      super.draw(batch, parentAlpha)
    }
  }
}
