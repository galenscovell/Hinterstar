package galenscovell.hinterstar.processing

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.things.entities.Npc
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


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
class CombatHandler(root: Stage, playerShip: Ship) {
  private val actionStage: Stage = root
  private val random: Random = new Random()
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
        if (!weaponFx.contains(weapon.getFx)) {
          val weaponSubsystem: String = weapon.getSubsystem
          val weaponTile: Tile = player.getSubsystemMap(weaponSubsystem)
          weaponFx.append(weapon.getFx)
          val srcCoords: Vector2 = weaponTile.getActorCoordinates

          val targetSubsystemNames: Array[String] = opposition.getShip.getSubsystemNames
          val randomSubsystemIndex: Int = random.nextInt(targetSubsystemNames.length)
          val targetCoords: Vector2 = opposition.getShip.getSubsystemMap(targetSubsystemNames(randomSubsystemIndex)).getStageCoordinates

          weapon.getFx.fire(srcCoords, targetCoords)
          weapon.resetFireBar()
        }
      }
    }

    // Repeat above for opposition
    if (npcReadyWeapons.nonEmpty) {

    }
  }



  /**********************
    *     Rendering     *
    **********************/
  def render(delta: Float, spriteBatch: Batch): Unit = {
    val finishedFxIndices: ArrayBuffer[Int] = ArrayBuffer()

    spriteBatch.begin()
    for (fx <- weaponFx) {
      fx.draw(delta, spriteBatch)
      if (fx.done) {
        finishedFxIndices.append(weaponFx.indexOf(fx))
      }
    }
    spriteBatch.end()

    for (i: Int <- finishedFxIndices) {
      weaponFx.remove(i)
    }
  }
}
