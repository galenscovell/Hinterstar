package galenscovell.hinterstar.processing

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import galenscovell.hinterstar.generation.interior.Tile
import galenscovell.hinterstar.graphics.WeaponFx
import galenscovell.hinterstar.things.entities.Enemy
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.things.ships.Ship
import galenscovell.hinterstar.ui.components.gamescreen.stages.EntityStage

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


class CombatProcessor(actionStage: EntityStage) {
  private val random: Random = new Random()
  private var enemy: Enemy = _
  private val weaponFx: ArrayBuffer[WeaponFx] = ArrayBuffer()
  private val finishedFx: ArrayBuffer[WeaponFx] = ArrayBuffer()


  def setEnemy(en: Enemy): Unit = {
    enemy = en
  }



  /*********************
    *     Updating     *
    *********************/
  def update(playerReadyWeapons: Array[Weapon], enemyReadyWeapons: Array[Weapon]): Unit = {
    // Check ready to fire weapons for player ship
    if (playerReadyWeapons.nonEmpty) {
      for (weapon: Weapon <- playerReadyWeapons) {
        if (!weaponFx.contains(weapon.getFx)) {
          val weaponSubsystem: String = weapon.getSubsystem
          val playerShip: Ship = actionStage.getPlayerShip
          val weaponTile: Tile = playerShip.getSubsystemMap(weaponSubsystem)
          weaponFx.append(weapon.getFx)

          val srcCoords: Vector2 = weaponTile.getActorCoordinates
          val targetCoords: Vector2 = weapon.getTarget.getActorCoordinates

          weapon.getFx.fire(srcCoords, targetCoords)
          weapon.resetFireBar()
        }
      }
    }

    // Repeat above for enemy
    if (enemyReadyWeapons.nonEmpty) {

    }
  }



  /**********************
    *     Rendering     *
    **********************/
  def render(delta: Float, spriteBatch: Batch): Unit = {
    spriteBatch.begin()

    for (fx <- weaponFx) {
      fx.draw(delta, spriteBatch)
      if (fx.done) {
        finishedFx.append(fx)
      }
    }

    spriteBatch.end()

    if (finishedFx.nonEmpty) {
      for (fx: WeaponFx <- finishedFx) {
        val index: Int = weaponFx.indexOf(fx)
        weaponFx.remove(index)
      }

      finishedFx.clear()
    }
  }
}
