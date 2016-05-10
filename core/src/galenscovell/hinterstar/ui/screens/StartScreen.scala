package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent, Stage}
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.util.{Constants, ResourceManager}


class StartScreen(gameRoot: Hinterstar) extends AbstractScreen(gameRoot) {
  // This screen displays to the player at the start of their journey
  // Player selects starting loadout here: ship, teammates and resources

  // Panels:
  //  Teammates: Player selects number of teammates (between min and max)
  //    More teammates means certain resources are depleted faster
  //    Player names teammates and selects their profession
  //    This choice is not final, other teammates can become available ingame
  //  Ship: Player selects ship design from premade options
  //    More options can be unlocked through game accomplishments
  //    This choice is not final, other ships can become available ingame
  //    Ships have different looks, upgrade options and starting components
  //  Resources: Player selects starting resources
  //    Player begins with cash amount dependent on team members
  //    Different resources cost different amounts and take up storage
  //    Food, water, fuel, spare parts,
  //    Resources are found throughout game

  // 'Start Journey' or the like is displayed once all panels are finished
  private var teamPanel: Table = null
  private var shipPanel: Table = null
  private var resourcePanel: Table = null


  protected override def create(): Unit = {
    this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch)
    teamPanel = constructTeamPanel
    shipPanel = constructShipPanel
    resourcePanel = constructResourcePanel

    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val titleTable: Table = new Table
    val titleLabel: Label = new Label("Loadout", ResourceManager.labelTitleStyle)
    titleLabel.setAlignment(Align.center, Align.center)
    val returnButton: TextButton = new TextButton("Return", ResourceManager.buttonMenuStyle)
    returnButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.5f),
          toMainMenuAction)
        )
      }
    })
    val embarkButton: TextButton = new TextButton("Embark", ResourceManager.buttonMenuStyle)
    titleTable.add(returnButton).width(200).height(50)
    titleTable.add(titleLabel).width(375).height(50)
    titleTable.add(embarkButton).width(200).height(50)

    mainTable.add(titleTable).width(780).height(50).center.pad(4)
    mainTable.row
    mainTable.add(teamPanel).width(780).height(400).center.pad(4)

    stage.addActor(mainTable)
    mainTable.addAction(Actions.sequence(
      Actions.fadeOut(0),
      Actions.fadeIn(0.5f))
    )
  }

  private def constructTeamPanel: Table = {
    val teamPanel: Table = new Table

    val leftTable: Table = new Table
    leftTable.setBackground(ResourceManager.npTest1)

    val rightTable: Table = new Table
    rightTable.setBackground(ResourceManager.npTest1)

    val nextButton: TextButton = new TextButton(">", ResourceManager.buttonMenuStyle)

    teamPanel.add(leftTable).width(340).height(400).pad(4)
    teamPanel.add(rightTable).width(340).height(400).pad(4)
    teamPanel.add(nextButton).width(80).height(400).pad(4)
    teamPanel
  }

  private def constructShipPanel: Table = {
    val shipPanel: Table = new Table

    shipPanel
  }

  private def constructResourcePanel: Table = {
    val resourcePanel: Table = new Table

    resourcePanel
  }


  /**
    * Custom Scene2D Actions
    */
  private[screens] var toMainMenuAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.mainMenuScreen)
      true
    }
  }
}
