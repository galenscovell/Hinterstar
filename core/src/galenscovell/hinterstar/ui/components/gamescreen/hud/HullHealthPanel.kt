package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.*
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage


class HullHealthPanel(interfaceStage: InterfaceStage, healthBar: ProgressBar) : Table() {

    init {
        this.add(healthBar).expand().fill()
    }
}
