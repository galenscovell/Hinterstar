package galenscovell.hinterstar.generation.sector

import galenscovell.hinterstar.processing.Event
import galenscovell.hinterstar.util.*



class System(val x: Int, val y: Int, val size: Int) {
    lateinit private var systemMarker: SystemMarker
    private var event: Event = Event()



    /********************
     *     Getters     *
     ********************/
    fun getSystemMarker(): SystemMarker {
        return systemMarker
    }

    fun getEvent(): Event {
        return event
    }



    /********************
     *     Setters     *
     ********************/
    fun setSystemMarker(newSystemMarker: SystemMarker): Unit {
        systemMarker = newSystemMarker
        systemMarker.becomeUnexplored()
    }

    fun setAsTutorial(): Unit {
        val startingEvent: Event = Event()
        startingEvent.setStartEvent()
        event = startingEvent
    }



    fun enter(): Unit {
        createBackground()
    }

    private fun createBackground(): Unit {
        val num0: Int = (Math.random() * 8).toInt()  // Value between 0-7
        val num1: Int = (Math.random() * 4).toInt() + 8 // Value between 8-12
        SystemOperations.gameScreen.transitionSector(num0.toString(), num1.toString(), "stars0", "stars1", "stars0_blur", "stars1_blur")
    }
}
