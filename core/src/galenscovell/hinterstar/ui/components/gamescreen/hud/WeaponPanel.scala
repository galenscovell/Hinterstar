package galenscovell.hinterstar.ui.components.gamescreen.hud

import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import galenscovell.hinterstar.things.parts.Weapon
import galenscovell.hinterstar.ui.components.gamescreen.stages.HudStage
import galenscovell.hinterstar.util.Resources

import scala.collection.mutable.ArrayBuffer


class WeaponPanel(stage: HudStage) extends Table  {
  private val hudStage: HudStage = stage
  private val contentTable: Table = new Table
  private val weapons: ArrayBuffer[Weapon] = ArrayBuffer()

  construct()
  addWeapon(new Weapon("Machinegun", "Basic part", 5, 10, "None"))
  addWeapon(new Weapon("Railgun", "Basic part", 20, 30, "None"))


  private def construct(): Unit = {
    this.add(contentTable).expand.left
  }

  def addWeapon(w: Weapon): Unit = {
    val weaponTable: Table = new Table
    weaponTable.setBackground(Resources.npTest1)

    val weaponLabel: Label = new Label(w.getName, Resources.labelTinyStyle)
    weaponLabel.setAlignment(Align.left, Align.center)

    val fireBarTable: Table = new Table
    fireBarTable.add(w.getFireBar).width(80).height(24)

    weaponTable.add(weaponLabel).expand.left.padLeft(4).padRight(4)
    weaponTable.add(fireBarTable).expand.left.padRight(4)

    contentTable.add(weaponTable).expand.left.padRight(4)

    weapons.append(w)
  }

  def update(): Array[Weapon] = {
    // Iterate through currently active weapons
    // If weapon is set to fire, append it to an array that is returned
    val readyWeapons: ArrayBuffer[Weapon] = ArrayBuffer()

    for (weapon: Weapon <- weapons) {
      if (weapon.updateFireBar()) {
        readyWeapons.append(weapon)
      }
    }

    readyWeapons.toArray
  }
}
