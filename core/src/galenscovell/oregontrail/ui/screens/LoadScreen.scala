package galenscovell.oregontrail.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics._
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.FitViewport
import galenscovell.oregontrail.OregonTrailMain
import galenscovell.oregontrail.util._


class LoadScreen(gameRoot: OregonTrailMain) extends AbstractScreen(gameRoot) {
  private var loadingBar: ProgressBar = null


  protected override def create(): Unit = {
    this.stage = new Stage(new FitViewport(Constants.EXACT_X, Constants.EXACT_Y), root.spriteBatch)
    val loadingMain: Table = new Table
    loadingMain.setFillParent(true)
    val barTable: Table = new Table
    this.loadingBar = createBar
    barTable.add(loadingBar).width(400).expand.fill
    loadingMain.add(barTable).expand.fill
    stage.addActor(loadingMain)
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    stage.act(delta)
    stage.draw
    if (ResourceManager.assetManager.update) {
      ResourceManager.done
      stage.getRoot.addAction(Actions.sequence(Actions.fadeOut(0.4f), toMainMenuScreen))
    }
    loadingBar.setValue(ResourceManager.assetManager.getLoadedAssets)
  }

  override def show: Unit = {
    ResourceManager.load
    create
    stage.getRoot.getColor.a = 0
    stage.getRoot.addAction(Actions.sequence(Actions.fadeIn(0.4f)))
  }

  private def createBar(): ProgressBar = {
    val fill: TextureRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/loadingFill.png"))))
    val empty: TextureRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/loadingEmpty.png"))))
    val barStyle: ProgressBar.ProgressBarStyle = new ProgressBar.ProgressBarStyle(empty, fill)
    val bar: ProgressBar = new ProgressBar(0, 6, 1, false, barStyle)
    barStyle.knobBefore = fill
    bar.setValue(0)
    bar.setAnimateDuration(0.1f)
    return bar
  }


  private[screens] var toMainMenuScreen: Action = new Action() {
    def act(delta: Float): Boolean = {
      root.setScreen(root.mainMenuScreen)
      true
    }
  }
}