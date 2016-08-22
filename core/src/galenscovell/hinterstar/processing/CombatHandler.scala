package galenscovell.hinterstar.processing

import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.things.entities.Npc
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship

import scala.collection.mutable.ArrayBuffer


/**
  * Handles combat related processes between player ship and current opposition (generally NPC ship).
  *
  * Every game update an array of ready to fire weapons (for both player and opposition) is sent to this.
  * Each ready weapon uses its internal stats to determine damage, hit chance, and effects.
  * If there is no current opposition then the ready player weapons stay at full and do not fire.
  * Every weapon has a unique animation, with the source being the ship housing the weapon, and the target
  *     being the opposition ship.
  *
  * TODO:
  * The exact coordinate source for each weapon is the subsystem that it's housed within.
  * The exact coordinate target is any subsystem on the opposition ship.
  */
class CombatHandler(playerShip: Ship) {
  private val player: Ship = playerShip
  private var opposition: Npc = _
  private val weaponFx: ArrayBuffer[WeaponFx] = ArrayBuffer()



  def setOpposition(npc: Npc): Unit = {
    opposition = npc
  }



  /*********************
    *     Updating     *
    *********************/
  def update(playerReadyWeapons: Array[Weapon], npcReadyWeapons: Array[Weapon]): Unit = {
    // Check ready to fire weapons for player ship
    if (playerReadyWeapons.nonEmpty) {
      for (weapon: Weapon <- playerReadyWeapons) {
        val weaponSubsystem: String = weapon.getSubsystem
        val weaponTile: Tile = player.getSubsystemMap(weaponSubsystem)
        println(weaponTile.getScreenCoordinates.toString)
      }

    }

    // Repeat above for opposition
    if (npcReadyWeapons.nonEmpty) {

    }
  }



  /**********************
    *     Rendering     *
    **********************/
  def render(delta: Float): Unit = {
    val finishedFxIndices: ArrayBuffer[Int] = ArrayBuffer()

    for (fx <- weaponFx) {
      if (!fx.done) {
        fx.draw(delta)
      } else {
        finishedFxIndices.append(weaponFx.indexOf(fx))
      }
    }

    for (i: Int <- finishedFxIndices) {
      weaponFx.remove(i)
    }
  }
}
