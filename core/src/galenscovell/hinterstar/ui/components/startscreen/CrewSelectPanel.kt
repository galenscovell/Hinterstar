package galenscovell.hinterstar.ui.components.startscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.*
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.*
import java.util.*


class CrewSelectPanel : Table() {
    private val crewmates: List<Crewmate> = randomizeStartingTeamNames()

    private val nameInput: TextField = TextField("", Resources.textFieldStyle)
    private val crewTable: Table = Table()

    private var currentCrewmate: Crewmate? = null
    private var currentCrewButton: TextButton? = null

    init {
        crewTable.background = Resources.npTest1
        crewTable.color = Constants.NORMAL_UI_COLOR

        updateCrewTable()

        add(crewTable).expand().fill()

        nameInput.setAlignment(Align.left)
        nameInput.maxLength = 10
    }



    fun getCrewmates(): List<Crewmate> {
        return crewmates
    }

    private fun updateCrewTable(): Unit {
        crewTable.clear()
        val teamTable: Table = constructCrewTable()
        crewTable.add(teamTable).expand().fill()
    }

    private fun constructCrewTable(): Table {
        val crewTable: Table = Table()

        val nameTable: Table = Table()
        val nameLabel: Label = Label("Crewmates", Resources.labelLargeStyle)
        nameLabel.setAlignment(Align.center)

        val modifyCrewmateButton: TextButton = TextButton("Update Selected", Resources.greenButtonStyle)
        modifyCrewmateButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                currentCrewmate!!.setName(nameInput.text)
                crewTable.addAction(Actions.sequence(
                        updateLeftTableAction
                ))
            }
        })

        nameTable.add(nameLabel).expand().fill().height(30f).padBottom(2f)
        nameTable.row()
        nameTable.add(nameInput).expand().fill().height(40f).pad(2f)

        crewTable.add(nameTable).expand().fill().height(70f).pad(2f)
        crewTable.row()
        val pad = crewTable.add(modifyCrewmateButton).expand().fill().height(50f).pad(10f)

        for (crewmate: Crewmate in crewmates) {
            val memberTable: Table = Table()
            val button: TextButton = TextButton("", Resources.toggleButtonStyle)
            button.setText(crewmate.getName())
            val iconTable: Table = Table()  // Crewmate icon/sprite?
            iconTable.background = Resources.greenButtonNp0

            if (currentCrewButton == null) {
                currentCrewButton = button
                currentCrewButton!!.isChecked = true
                nameInput.text = crewmate.getName()
            }

            button.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                    currentCrewmate = crewmate
                    currentCrewButton!!.isChecked = false
                    if (currentCrewButton != button) {
                        currentCrewButton = button
                        nameInput.text = crewmate.getName()
                    }
                    currentCrewButton!!.isChecked = true
                }
            })

            memberTable.add(button).width(160f).height(60f).pad(4f)
            memberTable.add(iconTable).width(70f).height(60f).pad(4f)

            crewTable.row()
            crewTable.add(memberTable).expand().fill().height(60f).pad(2f)
        }

        return crewTable
    }

    private fun randomizeStartingTeamNames(): List<Crewmate> {
        val randomNames: MutableList<String> = mutableListOf()

        val mainJson: JsonValue = JsonReader().parse(Gdx.files.internal("data/names.json"))
        val humanJson: JsonValue = mainJson.get("Human")
        val humanMale: Array<String> = humanJson.get("Male").asStringArray()
        val humanFemale: Array<String> = humanJson.get("Female").asStringArray()

        val random: Random = Random()
        while (randomNames.size < 3) {
            var randomName: String
            val female: Boolean = random.nextInt(1) == 1

            if (female) {
                val randomNameIndex: Int = random.nextInt(humanFemale.size)
                randomName = humanFemale[randomNameIndex]
            } else {
                val randomNameIndex: Int = random.nextInt(humanMale.size)
                randomName = humanMale[randomNameIndex]
            }

            if (!randomNames.contains(randomName)) {
                randomNames.add(randomName)
            }
        }

        val startingProficiencies: MutableMap<String, Int> = mutableMapOf(
            Pair("Weapons", 0),
            Pair("Engines", 0),
            Pair("Piloting", 0),
            Pair("Shields", 0)
        )
        val startCrew: MutableList<Crewmate> = mutableListOf()
        for (name: String in randomNames) {
            val newCrewmate: Crewmate = Crewmate(name, startingProficiencies, "Medbay", 100f)
            startCrew.add(newCrewmate)
        }

        return startCrew.toList()
    }



    /***************************
     * Custom Scene2D Actions *
     ***************************/
    private var updateLeftTableAction: Action = object : Action() {
        override fun act(delta: Float): Boolean {
            updateCrewTable()
            return true
        }
    }
}
