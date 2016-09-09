package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.Table
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util.PlayerData


class CrewPanel(interfaceStage: InterfaceStage) : Table() {
    private val contentTable: Table = Table()

    init {
        refresh()
        this.add(contentTable).expand().left().bottom()
    }



    fun refresh(): Unit {
        contentTable.clear()

        for (crewmate: Crewmate in PlayerData.getCrew()) {
            contentTable.add(crewmate.getCrewBox()).width(120f).height(60f).left().padRight(12f)
            crewmate.setAssignmentIcon()
        }
    }
}
