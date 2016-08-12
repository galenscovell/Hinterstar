package galenscovell.hinterstar.processing

import galenscovell.hinterstar.things.entities.NpcShip


class CombatHandler {
  private var opposition: NpcShip = _


  def update(): Unit = {
    // Check active weapons on player ship
    // For each active weapon, check firerate vs cooldown
    // If time to fire, have that weapon fire

    // Repeat above for opposition
  }

  def setOpposition(npc: NpcShip): Unit = {
    opposition = npc
  }
}
