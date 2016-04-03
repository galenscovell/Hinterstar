package galenscovell.hinterstar.things.inanimate.Events


trait Event {
  def start(): Unit
  def end(): Unit
  def setDistanceTo(distance: Int): Unit
  def getDistanceTo(distance: Int): Int
}
