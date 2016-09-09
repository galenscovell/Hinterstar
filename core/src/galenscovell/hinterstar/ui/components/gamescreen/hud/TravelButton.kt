package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util.Resources


class TravelButton(private val interfaceStage: InterfaceStage) : Table() {
    private var button: TextButton? = null

    init {
        this.setFillParent(true)

        val contentTable: Table = Table()
        this.button = TextButton("Travel", Resources.greenButtonStyle)
        button!!.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                interfaceStage.openTravelPanel()
            }
        })

        contentTable.add(button).width(96f).height(40f).center()

        this.add(contentTable).expand().width(96f).height(40f).top().right().padTop(4f).padRight(10f)
    }
}
