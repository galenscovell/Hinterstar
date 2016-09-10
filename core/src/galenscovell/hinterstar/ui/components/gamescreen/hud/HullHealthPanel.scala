package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage


class HullHealthPanel(interfaceStage: InterfaceStage, healthBar: ProgressBar) extends Table {
  construct()



  private def construct(): Unit = {
    this.add(healthBar).expand.fill
  }
}
