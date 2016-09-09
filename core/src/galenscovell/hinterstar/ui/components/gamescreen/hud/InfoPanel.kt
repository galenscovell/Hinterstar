package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.*
import galenscovell.hinterstar.util.Resources


class InfoPanel(message: String) : Table() {

    init {
        this.setFillParent(true)

        val pauseTable: Table = Table()
        pauseTable.background = Resources.blueButtonNp0
        val pauseLabel: Label = Label(message, Resources.labelMediumStyle)

        pauseTable.add(pauseLabel).pad(6f)

        this.add(pauseTable).expand().height(32f).center().top().padTop(80f)
    }
}
