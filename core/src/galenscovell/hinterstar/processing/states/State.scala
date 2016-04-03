package galenscovell.hinterstar.processing.states


trait State {
  def enter(): Unit
  def exit(): Unit
  def getStateType(): Short
}
