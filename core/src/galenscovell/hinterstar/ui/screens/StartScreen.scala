package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{Action, InputEvent, Stage}
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.ui.components.startscreen.{ResourceSelectionPanel, ShipSelectionPanel, TeamSelectionPanel}
import galenscovell.hinterstar.util.{Constants, PlayerData, ResourceManager}


class StartScreen(gameRoot: Hinterstar) extends AbstractScreen(gameRoot) {
  //  Ship: Player selects ship design from premade options
  //    More options can be unlocked through game accomplishments
  //    This choice is not final, other ships can become available ingame
  //    Ships have different looks, upgrade options and starting parts
  //  Resources: Player selects starting resources
  //    Player begins with cash amount dependent on team members
  //    Different resources cost different amounts and take up storage
  //    Food, water, fuel, spare parts
  //    Resources are found/generated throughout game
  private val teamPanel: Table = new TeamSelectionPanel
  private val shipPanel: Table = new ShipSelectionPanel
  private val resourcePanel: Table = new ResourceSelectionPanel
  private val contentTable: Table = new Table

  updateContentTable(teamPanel)


  protected override def create(): Unit = {
    this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch)

    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val titleTable: Table = new Table
    val titleLabel: Label = new Label("Loadout", ResourceManager.labelTitleStyle)
    titleLabel.setAlignment(Align.center, Align.center)

    val returnButton: TextButton = new TextButton("Return", ResourceManager.greenButtonStyle)
    returnButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.3f),
          toMainMenuAction)
        )
      }
    })

    val embarkButton: TextButton = new TextButton("Embark", ResourceManager.greenButtonStyle)
    embarkButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        val team: Array[String] = teamPanel.asInstanceOf[TeamSelectionPanel].getTeammates
        establishTeam(team)
        root.createGameScreen()
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.3f),
          toGameScreenAction)
        )
      }
    })

    titleTable.add(returnButton).width(200).height(50)
    titleTable.add(titleLabel).width(364).height(50)
    titleTable.add(embarkButton).width(200).height(50)

    mainTable.add(titleTable).width(764).height(50).pad(8)
    mainTable.row
    mainTable.add(contentTable).width(780).height(400)

    stage.addActor(mainTable)
    mainTable.addAction(Actions.sequence(
      Actions.fadeOut(0),
      Actions.fadeIn(0.3f))
    )
  }

  private def updateContentTable(content: Table): Unit = {
    contentTable.clear()

    val nextButton: TextButton = new TextButton(">", ResourceManager.blueButtonStyle)
    nextButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        transitionPanel()
      }
    })

    contentTable.add(content).width(690).height(400).left.padRight(10)
    contentTable.add(nextButton).width(80).height(400).right
  }

  private def transitionPanel(): Unit = {
    if (teamPanel.hasParent) {
      updateContentTable(shipPanel)
    } else if (shipPanel.hasParent) {
      updateContentTable(resourcePanel)
    } else if (resourcePanel.hasParent) {
      updateContentTable(teamPanel)
    }
  }

  private def establishTeam(team: Array[String]): Unit = {
    PlayerData.establishTeam(team)
  }

  private def establishShip(): Unit = {

  }

  private def establishResources(resources: Map[String, Int]): Unit = {

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
  private[screens] var toGameScreenAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.gameScreen)
      true
    }
  }
}
