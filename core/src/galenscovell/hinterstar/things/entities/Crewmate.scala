package galenscovell.hinterstar.things.entities


class Crewmate(n: String, p: Map[String, Int]) {
  val name: String = n
  val proficiency: Map[String, Int] = p
  val assignment: String = _
  val health: Int = 100


  def getName(): String = {
    name
  }

  def getProficiency(): Map[String, Int] = {
    proficiency
  }

  def getAssignment(): String = {
    assignment
  }

  def getHealth(): Int = {
    health
  }
}
