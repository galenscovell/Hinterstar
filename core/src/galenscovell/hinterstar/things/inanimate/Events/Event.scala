package galenscovell.hinterstar.things.inanimate.Events


trait Event {
  def start(): Unit
  def end(): Unit
}
