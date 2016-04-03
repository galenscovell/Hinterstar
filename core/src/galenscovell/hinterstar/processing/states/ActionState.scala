package galenscovell.hinterstar.processing.states


class ActionState extends State {


  override def getStateType(): Short = 0

  override def enter(): Unit = {
    println("Entering Action State")
  }

  override def exit(): Unit = {
    println("Exiting Action State")
  }
}
