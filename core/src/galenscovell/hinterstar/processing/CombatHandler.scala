package galenscovell.hinterstar.processing

import galenscovell.hinterstar.things.entities.NpcShip
import galenscovell.hinterstar.things.parts.Weapon


class CombatHandler {
  private var opposition: NpcShip = _


  def update(readyWeapons: Array[Weapon]): Unit = {
    // Check ready to fire weapons on player ship
    if (readyWeapons.nonEmpty) {

    }

    // Repeat above for opposition
  }

  def setOpposition(npc: NpcShip): Unit = {
    opposition = npc
  }
}
