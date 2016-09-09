package galenscovell.hinterstar.things.entities

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.util.*


class Crewmate(private var crewName: String, private val proficiencies: MutableMap<String, Int>,
               private var assignmentName: String, health: Float) {
    private var assignment: Tile? = null
    private var weapon: Weapon? = null

    private val sprite: Sprite = Resources.spCrewmate
    private val healthBar: ProgressBar = ProgressBar(0f, health, 1f, true, Resources.crewHealthBarStyle)
    private val crewInnerTable: Table = Table()
    private val crewBox: Group = Group()
    private val assignmentIconTable: Table = Table()
    private val detailLabel: Label = Label("...", Resources.labelTinyStyle)

    private val flag: CrewmateFlag = CrewmateFlag(crewName)

    init {
        constructBox()
    }



    /********************
     *     Getters     *
     ********************/
    fun getThisCrewmate(): Crewmate {
        return this
    }

    fun getName(): String {
        return crewName
    }

    fun getAllProficiencies(): MutableMap<String, Int> {
        return proficiencies
    }

    fun getAProficiency(proficiency: String): Int {
        return proficiencies[proficiency]!!
    }

    fun getAssignment(): Tile? {
        return assignment
    }

    fun getWeapon(): Weapon? {
        return weapon
    }

    fun getAssignedSubsystemName(): String {
        if (assignment != null) {
            assignmentName = assignment!!.getSubsystemName()
        }
        return assignmentName
    }

    fun getHealth(): Float {
        return healthBar.value
    }

    fun getSprite(): Sprite {
        return sprite
    }

    fun getHealthBar(): ProgressBar {
        return healthBar
    }

    fun getFlag(): CrewmateFlag {
        return flag
    }



    /********************
     *     Setters     *
     ********************/
    fun setName(n: String): Unit {
        crewName = n
    }

    fun updateProficiency(proficiency: String, value: Int): Unit {
        proficiencies[proficiency] = proficiencies[proficiency]!! + value
    }

    fun setAssignment(t: Tile?): Unit {
        assignment = t
    }

    fun setWeapon(w: Weapon): Unit {
        weapon = w
        setDetail(w.getName())
    }

    fun removeWeapon(): Unit {
        weapon = null
        setDetail("...")
    }

    fun updateHealth(value: Int): Unit {
        var health: Float = healthBar.value + value
        if (health > 100) {
            health = 100f
        } else if (health < 0) {
            health = 0f
        }
        healthBar.value = health
    }



    /***********************
     *    UI Component    *
     ***********************/
    fun getCrewBox(): Group {
        constructBox()
        return crewBox
    }

    fun highlightTable(): Unit {
        crewInnerTable.background = Resources.npTest2
    }

    fun unhighlightTable(): Unit {
        crewInnerTable.background = Resources.npTest4
    }

    fun setAssignmentIcon(): Unit {
        val assignmentIcon: Image?
        assignmentIconTable.clear()

        if (assignment != null && !flag.hasPath()) {
            assignmentIcon = Image(assignment!!.getIcon())

            if (assignment!!.isWeaponSubsystem()) {
                assignmentIconTable.addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                        CrewOperations.showWeaponSelect(getThisCrewmate())
                    }
                })
                assignmentIcon.setColor(0.7f, 0.2f, 0.2f, 1f)
            }
        } else {
            assignmentIcon = Image(Resources.spMovementIcon)
        }

        assignmentIconTable.add(assignmentIcon)
    }

    fun getDetail(): String {
        return detailLabel.text.toString()
    }

    private fun setDetail(detail: String): Unit {
        detailLabel.setText(detail)
    }

    private fun constructBox(): Unit {
        crewBox.clear()
        crewInnerTable.clear()

        crewInnerTable.background = Resources.npTest4
        crewInnerTable.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float): Unit {
                CrewOperations.selectCrewmate(getThisCrewmate())
            }
        })

        val leftTable: Table = Table()

        val spriteTable: Table = Table()
        spriteTable.background = Resources.npTest3
        val sprite: Image = Image(getSprite())
        spriteTable.add(sprite).expand().fillX

        leftTable.add(spriteTable).expand().fill().width(32f).height(32f).left().top()
        leftTable.row()
        leftTable.add(healthBar).expand().fill().width(32f).height(28f).left()

        val rightTable: Table = Table()

        val nameLabel: Label = Label(crewName, Resources.labelTinyStyle)
        nameLabel.setAlignment(Align.center, Align.left)

        detailLabel.setAlignment(Align.center, Align.left)

        rightTable.add(nameLabel).expand().fill().width(80f)
        rightTable.row()
        rightTable.add(detailLabel).expand().fill().width(80f)

        crewInnerTable.add(leftTable).expand().width(32f).height(60f).left()
        crewInnerTable.add(rightTable).expand().fill().width(88f).height(60f)

        crewBox.addActor(crewInnerTable)
        crewInnerTable.setSize(120f, 60f)
        crewInnerTable.setPosition(0f, 0f)

        crewBox.addActor(assignmentIconTable)
        assignmentIconTable.setSize(32f, 32f)
        assignmentIconTable.setPosition(102f, 40f)
    }
}
