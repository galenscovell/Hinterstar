package galenscovell.hinterstar.processing


/**
 * Events are the primary points of interaction for the Player.
 * Events have:
 *     an eventType (eg. Base, Enemy, Planet, Ship, Space, Team)
 *     a name (displayed as the title in the interaction dialogue)
 *     a description (displayed in the interaction dialogue)
 *     multiple choices (options for Player in interaction dialogue)
 */
class Event() {
    val choices: MutableList<MutableMap<String, String>> = mutableListOf()

    var eventType: String = ""
    var name: String = ""
    var description: String = ""


    fun getType(): String {
        return eventType
    }

    fun setStartEvent(): Unit {
        name = "Starting Event"
        description = "This is the first event in the game. It describes the goal and basic controls."
    }
}
