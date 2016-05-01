package galenscovell.hinterstar.things.inanimate.Events

import java.util.Random


class Planet(distanceTo: Float) extends Event {
  private var resources: Int = 0
  private var planetType: Int = 0
  private var population: Int = 0
  private var humidity: Int = 0
  private var atmosphere: Int = 0
  private var temperature: Int = 0
  private var gravity: Int = 0

  private val distance: Float = distanceTo
  private val eventType: String = "Test"

  generate()


  private def generate(): Unit = {
    val random: Random = new Random
  }

  override def start(): Unit = {
    println("Planet event started")
  }

  override def end(): Unit = {
    println("Planet event ended")
  }

  override def getDistance: Float = {
    distance
  }

  override def getType: String = {
    eventType
  }
}
