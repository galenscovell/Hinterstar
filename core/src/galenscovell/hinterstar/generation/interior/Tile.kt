package galenscovell.hinterstar.generation.interior

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import galenscovell.hinterstar.things.entities.Crewmate
import galenscovell.hinterstar.util.*


class Tile(private val tx: Float, private val ty: Float, private val tileSize: Float, private val overlayHeight: Int,
           private val ssName: String, private val hasWeapon: Boolean, private val isPlayerSubsystem: Boolean,
           private val traversible: Boolean) : Actor() {
    private var frames: Int = 100
    private var glowing: Boolean = true
    private var assignedCrewmates: Int = 0

    private val maxOccupancy: Int = setMaxOccupancy()
    private val icon: Sprite? = createSprite()
    private val infoDisplay: SubsystemInfo? = constructInfo()

    private val neighbors: MutableList<Tile> = mutableListOf()

    init {
        if (isSubsystem()) {
            if (isPlayerSubsystem) {
                // Set clicklistener for player subsystems (for crewmate assignment)
                this.addListener(object : ActorGestureListener() {
                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit {
                        CrewOperations.assignCrewmate(getThisTile())
                    }
                })

                // Start all player crewmates in their saved assigned subsystem
                // Start unassigned player crewmates in Medbay subsystem
                for (crewmate: Crewmate in PlayerData.getCrew()) {
                    if (crewmate.getAssignedSubsystemName() == ssName) {
                        crewmate.setAssignment(getThisTile())
                        assignCrewmate()
                    }
                }
            } else {
                // Set clicklistener for enemy subsystems (for targetting)
                this.addListener(object : ActorGestureListener() {
                    override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Unit {
                        if (CrewOperations.weaponSelected()) {
                            CrewOperations.targetEnemySubsystem(getThisTile())
                        }
                    }
                })
            }
        }

        this.setPosition(tx * tileSize, (tileSize * (overlayHeight - 1)) - (ty * tileSize))
    }



    /********************
     *     Getters     *
     ********************/
    fun getTx(): Float {
        return tx
    }

    fun getTy(): Float {
        return ty
    }

    fun getSubsystemName(): String {
        return ssName
    }

    private fun getThisTile(): Tile {
        return this
    }

    fun isSubsystem(): Boolean {
        return ssName != "none"
    }

    fun isWeaponSubsystem(): Boolean {
        return hasWeapon
    }

    fun isTraversible(): Boolean {
        return traversible
    }

    fun getIcon(): Sprite {
        return icon!!
    }

    fun getNeighbors(): List<Tile> {
        return neighbors.toList()
    }

    fun getActorCoordinates(): Vector2 {
        // This is confusing -- one would think to use getX() and getY(), but _don't_
        // Those return the location of the actor within the table, which we would then
        // attempt to transform to stage coordinates.
        // What we need to do is determine where a point is _within_ the current actor.
        // Vector2(0, 0) is the bottom left corner of the actor
        // Vector2(0, getHeight()) is the top left corner of the actor
        // We want the centerpoint of the tile
        return this.localToStageCoordinates(Vector2(
                (tileSize / 2) - 4f,
                (tileSize / 2) - 4f
        ))
    }



    /********************
     *     Setters     *
     ********************/
    fun setNeighbors(tiles: List<Tile>): Unit {
        for (t: Tile in tiles) {
            neighbors.add(t)
        }
    }



    /**********************
     *     Occupancy     *
     **********************/
    fun occupancyFull(): Boolean {
        return assignedCrewmates == maxOccupancy
    }

    fun assignCrewmate(): Unit {
        assignedCrewmates += 1
        infoDisplay!!.updateOccupancy(1)
    }

    fun removeCrewmate(): Unit {
        assignedCrewmates -= 1
        infoDisplay!!.updateOccupancy(-1)
    }

    private fun setMaxOccupancy(): Int {
        when (ssName) {
            "Weapon Control" -> return 3
            "Shield Control" -> return 3
            "Engine Room" -> return 3
            "Helm" -> return 1
            "Medbay" -> return 6
            else -> return 0
        }
    }



    /**********************
     *     Rendering     *
     **********************/
    private fun createSprite(): Sprite? {
        if (isSubsystem()) {
            val iconName: String = when (ssName) {
                "Weapon Control" -> "weapon"
                "Shield Control" -> "shield"
                "Engine Room" -> "engine"
                "Helm" -> "helm"
                "Medbay" -> "medbay"
                else -> ""
            }
            return Sprite(Resources.atlas!!.createSprite("icon_" + iconName))
        } else {
            return null
        }
    }

    private fun constructInfo(): SubsystemInfo? {
        if (isSubsystem()) {
            // Render above or below depending on relative position of subsystem
            var infoY: Float = y
            if (overlayHeight - ty > (overlayHeight / 2)) {
                infoY += (overlayHeight * tileSize)
            }
            return SubsystemInfo(ssName, icon!!, maxOccupancy, tx * tileSize, infoY)
        } else {
            return null
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float): Unit {
        if (isSubsystem()) {
            if (glowing) {
                frames += 1
            } else {
                frames -= 1
            }

            if (frames == 240) {
                glowing = false
            } else if (frames == 120) {
                glowing = true
            }

            val frameAlpha: Float = frames / 240.0f

            batch.setColor(0.2f, 0.9f, 0.2f, frameAlpha)
            batch.draw(Resources.spSubsystemMarker, x, y, tileSize, tileSize)

            infoDisplay?.draw(batch, parentAlpha)
            batch.setColor(1f, 1f, 1f, 1f)
        }
    }
}
