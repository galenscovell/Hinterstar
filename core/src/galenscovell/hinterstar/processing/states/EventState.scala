package galenscovell.hinterstar.processing.states


class EventState extends State {


  override def getStateType(): Short = 1

  override def enter(): Unit = {
    println("Entering Event State")
  }

  override def exit(): Unit = {
    println("Exiting Event State")
  }
}
