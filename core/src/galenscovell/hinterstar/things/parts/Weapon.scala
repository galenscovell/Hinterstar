package galenscovell.hinterstar.things.parts


class Weapon(n: String, desc: String, dmg: Int, fr: Int, fx: String) {
  private val name: String = n
  private val description: String = desc
  private val damage: Int = dmg
  private val firerate: Int = fr
  private val effect: String = fx
  private var active: Boolean = false


  def getName: String = {
    name
  }

  def getDescription: String = {
    description
  }

  def getDamage: Int = {
    damage
  }

  def getFirerate: Int = {
    firerate
  }

  def getEffect: String = {
    effect
  }



  def isActive: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def activate: Boolean = {
    // TODO: Not yet implemented
    active
  }

  def deactivate(): Unit = {
    // TODO: Not yet implemented
    active = false
  }
}
