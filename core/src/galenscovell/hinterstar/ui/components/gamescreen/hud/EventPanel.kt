package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.ui.components.gamescreen.stages.InterfaceStage
import galenscovell.hinterstar.util.Resources


class EventPanel(private val interfaceStage: InterfaceStage, private val eventContainer: Event) : Table() {

    init {
        this.setFillParent(true)
        val mainTable: Table = Table()
        mainTable.background = Resources.npTest4

        val titleTable: Table = createTitleTable(eventContainer.name)

        val centerTable: Table = Table()
        val descriptionTable: Table = createDescriptionTable(eventContainer.description)
        val optionTable: Table = createOptionTable()

        centerTable.add(descriptionTable).height(100f).expand().fill().top()
        centerTable.row()
        centerTable.add(optionTable).width(680f).height(250f).expand().fill()

        mainTable.add(titleTable).height(50f).expand().fill().top()
        mainTable.row()
        mainTable.add(centerTable).height(350f).expand().fill().top()

        this.add(mainTable).width(680f).height(400f).expand().fill().center()
    }



    private fun createTitleTable(eventTitle: String): Table {
        val titleTable: Table = Table()
        titleTable.background = Resources.npTest1
        val titleLabel: Label = Label(eventTitle, Resources.labelXLargeStyle)
        titleLabel.setAlignment(Align.center, Align.center)
        titleTable.add(titleLabel)
        return titleTable
    }

    private fun createDescriptionTable(eventDescription: String): Table {
        val descriptionTable: Table = Table()
        val descriptionLabel: Label = Label(eventDescription, Resources.labelSmallStyle)
        descriptionLabel.setWrap(true)
        descriptionLabel.setAlignment(Align.center, Align.left)
        descriptionTable.add(descriptionLabel).width(680f)
        return descriptionTable
    }

    private fun createOptionTable(): Table {
        val optionTable: Table = Table()
        optionTable.background = Resources.npTest1

        for (choice in eventContainer.choices) {
            val choiceText: String = choice["choice-text"].toString()
            val choiceButton: TextButton = TextButton(choiceText, Resources.buttonEventStyle)
            choiceButton.label.setAlignment(Align.left, Align.left)
            choiceButton.label.setWrap(true)
            choiceButton.labelCell.padLeft(20f).padRight(20f)
            optionTable.add(choiceButton).width(680f).height(50f).expand().fill().top().padTop(2f).padBottom(2f)
            optionTable.row()
        }

        return optionTable
    }
}
