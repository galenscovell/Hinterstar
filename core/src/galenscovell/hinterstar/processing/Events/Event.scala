package galenscovell.hinterstar.processing.Events


trait Event {
  def start(): Unit
  def end(): Unit
  def getDistance: Float
  def getType: String
}
