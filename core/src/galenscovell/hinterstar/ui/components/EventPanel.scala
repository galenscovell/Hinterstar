package galenscovell.hinterstar.ui.components

import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table}
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.util.ResourceManager


class EventPanel(gameStage: GameStage) extends Table {
  private final val root: GameStage = gameStage

  construct()


  def construct(): Unit = {
    this.setFillParent(true)

    val mainTable: Table = new Table
    mainTable.setBackground(ResourceManager.np_test4)

    // TOP
    val titleTable: Table = new Table
    titleTable.setBackground(ResourceManager.np_test1)
    val titleLabel: Label = new Label("Event Name", ResourceManager.label_titleStyle)
    titleLabel.setAlignment(Align.center, Align.center)
    titleTable.add(titleLabel)

    // CENTER
    val centerTable: Table = new Table
    val descriptionTable: Table = new Table
    val descriptionLabel: Label = new Label(
      """This is the event text describing exactly in painstaking detail
      |what this event is all about. It can be multiple lines and sentences long
      |and this should be taken into account. Scala has some convenient ways of
      |dealing with such long strings, and libGDX shouldn't have any problem
      |with this.""".stripMargin.replaceAll("\n", " "),
      ResourceManager.label_detailStyle
    )
    descriptionLabel.setWrap(true)
    descriptionLabel.setAlignment(Align.center, Align.left)
    descriptionTable.add(descriptionLabel).width(650)

    val optionTable: Table = new Table
    optionTable.setBackground(ResourceManager.np_test1)

    centerTable.add(descriptionTable).height(100).expand.fill.top
    centerTable.row
    centerTable.add(optionTable).height(250).width(660).expand.fill


    mainTable.add(titleTable).height(50).expand.fill.top
    mainTable.row
    mainTable.add(centerTable).height(350).expand.fill.top

    this.add(mainTable).width(660).height(400).expand.fill.center
  }
}
