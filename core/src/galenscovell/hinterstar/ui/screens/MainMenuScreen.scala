package galenscovell.hinterstar.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.hinterstar.Hinterstar
import galenscovell.hinterstar.util._


class MainMenuScreen(gameRoot: Hinterstar) extends AbstractScreen(gameRoot) {


  protected override def create(): Unit = {
    this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch)
    val mainTable: Table = new Table
    mainTable.setFillParent(true)

    val titleTable: Table = new Table
    val titleLabel: Label = new Label("Hinterstar", ResourceManager.labelTitleStyle)
    titleLabel.setAlignment(Align.center, Align.left)
    titleTable.add(titleLabel).width(760).height(60)

    val buttonTable: Table = new Table
    val newGameTable: Table = new Table
    newGameTable.setBackground(ResourceManager.npTest4)
    val newGameButton: TextButton = new TextButton("New Game", ResourceManager.buttonMenuStyle)
    newGameButton.getLabel.setAlignment(Align.center, Align.center)
    newGameButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        root.newGame()
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.5f),
          toStartScreenAction)
        )
      }
    })
    val continueGameTable: Table = new Table
    continueGameTable.setBackground(ResourceManager.npTest4)
    val continueGameButton: TextButton = new TextButton("Continue Game", ResourceManager.buttonMenuStyle)
    continueGameButton.getLabel.setAlignment(Align.center, Align.center)
    continueGameButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {

      }
    })
    val settingTable: Table = new Table
    settingTable.setBackground(ResourceManager.npTest4)
    val settingButton: TextButton = new TextButton("Preferences", ResourceManager.buttonMenuStyle)
    settingButton.getLabel.setAlignment(Align.center, Align.center)
    settingButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {

      }
    })
    val quitTable: Table = new Table
    quitTable.setBackground(ResourceManager.npTest4)
    val quitButton: TextButton = new TextButton("Close", ResourceManager.buttonMenuStyle)
    quitButton.getLabel.setAlignment(Align.center, Align.center)
    quitButton.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        stage.getRoot.addAction(Actions.sequence(
          Actions.fadeOut(0.5f),
          quitGameAction)
        )
      }
    })

    val detailTable: Table = new Table
    val detailLabel: Label = new Label(s"v0.1a 2016 Galen Scovell", ResourceManager.labelDetailStyle)
    detailLabel.setAlignment(Align.center, Align.right)
    detailTable.add(detailLabel).width(760).height(40)

    buttonTable.add(newGameButton).width(550).height(90).pad(6).left
    buttonTable.add(newGameTable).width(212).height(90).expand.pad(6).right
    buttonTable.row
    buttonTable.add(continueGameTable).width(212).height(90).expand.pad(6).left
    buttonTable.add(continueGameButton).width(550).height(90).pad(6).right
    buttonTable.row
    buttonTable.add(settingButton).width(550).height(90).pad(6).left
    buttonTable.add(settingTable).width(212).height(90).expand.pad(6).right
    buttonTable.row
    buttonTable.add(quitTable).width(212).height(90).expand.pad(6).left
    buttonTable.add(quitButton).width(550).height(90).pad(6).right

    mainTable.add(titleTable).width(780).height(60).expand.center.pad(6)
    mainTable.row
    mainTable.add(buttonTable).width(780).height(380).expand.center.pad(6)
    mainTable.row
    mainTable.add(detailTable).width(780).height(40).expand.center.pad(6)

    stage.addActor(mainTable)
    mainTable.addAction(Actions.sequence(
      Actions.fadeOut(0),
      Actions.fadeIn(0.5f))
    )
  }



  /**
    * Custom Scene2D Actions
    */
  private[screens] var toStartScreenAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.startScreen)
      true
    }
  }
  private[screens] var toGameScreenAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.gameScreen)
      true
    }
  }
  private[screens] var quitGameAction: Action = new Action() {
    def act(delta: Float): Boolean = {
      Gdx.app.exit()
      true
    }
  }
}
