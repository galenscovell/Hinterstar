package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util.{CrewOperations, Resources}


class CrewmateBox(stage: HudStage, c: Crewmate) extends Table {
  private val hudStage: HudStage = stage
  private val crewmate: Crewmate = c

  construct()


  private def construct(): Unit = {
    val mainTable: Table = new Table
    mainTable.setBackground(Resources.npTest4)

    mainTable.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        hudStage.getGameScreen.getActionStage.toggleSubsystemOverlay()
        CrewOperations.selectCrewmate(crewmate)
      }
    })

    val crewmateDetail: Table = new Table

    val spriteTable: Table = new Table
    spriteTable.setBackground(Resources.npTest3)
    val sprite: Image = new Image(crewmate.getSprite)
    spriteTable.add(sprite).expand.fillX

    val detailTop: Table = new Table
    val nameLabel: Label = new Label(crewmate.getName, Resources.labelTinyStyle)
    nameLabel.setAlignment(Align.center)

    val healthBarTable: Table = new Table
    healthBarTable.add(crewmate.getHealthBar).width(72).height(16)

    detailTop.add(nameLabel).expand.fill.height(20)
    detailTop.row
    detailTop.add(healthBarTable).expand.fill.center

    crewmateDetail.add(spriteTable).expand.fill.width(32).height(32).left.top
    crewmateDetail.add(detailTop).expand.fill.width(80).height(32).top

    val assignmentLabel: Label = new Label(crewmate.getAssignedSubsystemName, Resources.labelTinyStyle)
    assignmentLabel.setAlignment(Align.center)

    mainTable.add(crewmateDetail).expand.fill
    mainTable.row
    mainTable.add(assignmentLabel).expand.fill.height(32)

    this.add(mainTable)
  }
}
