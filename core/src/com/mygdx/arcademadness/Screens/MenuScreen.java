package com.mygdx.arcademadness.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.arcademadness.ArcadeMadness;

/**
 * Created by Banzneri on 23/03/2017.
 */

/**
 * An abstract MenuScreen class, which implements the Screen interface. All the menu screen classes
 * extend this class.
 */
public abstract class MenuScreen implements Screen {
    private Skin skin;

    private ArcadeMadness host;
    private Stage stage;
    private OrthographicCamera camera;
    private BitmapFont font;

    private TextButton textButtonUp;
    private TextButton textButtonDown;
    private String titleText;
    private String subtitleText;

    private GlyphLayout layoutTitle;
    private GlyphLayout layoutSubtitle;

    private String won;

    /**
     * Constructor for the MenuScreen abstract class. Receives the ArcadeMadness host game object,
     * and two ImageButton objects
     *
     * @param host ArcadeMadness object
     */
    public MenuScreen(ArcadeMadness host) {
        this.host = host;

        createSkin();

        font = host.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage =  new Stage(new FitViewport(ArcadeMadness.worldWidth, ArcadeMadness.worldHeight), host.getBatch());

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Setter for the won String, either won, lost, or none
     *
     * @param won String, which is either won, lost or none. Used to determine the font color for
     *            the subtitle text
     */
    public void setWon(String won) {
        this.won = won;
    }

    /**
     * Sets the title text of the menu screen
     *
     * @param titleText the title text for the menu screen
     */
    public void setTitleText(String titleText) {
        this.titleText = titleText;
        layoutTitle = new GlyphLayout(font, titleText);
    }

    /**
     * Sets the subtitle text for the menu screen
     *
     * @param subtitleText the subtitle text for the menu screen
     */
    public void setSubtitleText(String subtitleText) {
        this.subtitleText = subtitleText;
        layoutSubtitle = new GlyphLayout(font, subtitleText);
    }

    /**
     * Sets the text for the lower text button
     *
     * @param upText the text for the upper text button
     */
    public void setUpperButtonText(String upText) {
        textButtonUp= new TextButton(upText, skin);
        textButtonUp.setWidth(300);
        textButtonUp.setHeight(100);
        textButtonUp.setPosition(ArcadeMadness.worldWidth / 2 - textButtonUp.getWidth() / 2,
                                 ArcadeMadness.worldHeight / 2 - textButtonUp.getHeight() / 2);
        stage.addActor(textButtonUp);

    }

    /**
     * Sets the text for the lower text button
     *
     * @param downText the text for the lower text button
     */
    public void setLowerButtonText(String downText) {
        textButtonDown= new TextButton(downText, skin);
        textButtonDown.setWidth(300);
        textButtonDown.setHeight(100);
        textButtonDown.setPosition(ArcadeMadness.worldWidth / 2 - textButtonDown.getWidth() / 2,
                                   ArcadeMadness.worldHeight / 2 - textButtonUp.getHeight() / 2 - 100);
        stage.addActor(textButtonDown);
    }

    /**
     * Creates the skin, replacing the skin's default font
     */
    public void createSkin() {
        this.skin  = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/ui-skin.atlas")));
        skin.add("font", getHost().getFont(), BitmapFont.class);
        skin.add("font-over", getHost().getFont(), BitmapFont.class);
        skin.add("font-pressed", getHost().getFont(), BitmapFont.class);
        skin.load(Gdx.files.internal("skin/ui-skin.json"));
    }

    /**
     * Getter for the ArcadeMadness host object
     *
     * @return ArcadeMadness host object
     */
    public ArcadeMadness getHost() {
        return host;
    }

    /**
     * Getter for the upper button
     *
     * @return the upper text button object
     */
    public TextButton getTextButtonUp() {
        return textButtonUp;
    }

    /**
     * Getter for the lower button
     *
     * @return the lower text button object
     */
    public TextButton getTextButtonDown() {
        return textButtonDown;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawTitleTexts();

        stage.act();
        stage.draw();
    }

    /**
     * Draws the title texts, changes font color of subtitle text depending on victory condition:
     * Red if 'lost', green if 'won' and default white if 'none', which means not an end game menu
     */
    public void drawTitleTexts() {


        host.getBatch().begin();
        font.draw(host.getBatch(), titleText, ArcadeMadness.worldWidth / 2 - layoutTitle.width / 2, ArcadeMadness.worldHeight - 30);
        if(won.equals("won")) {
            host.greenFont.draw(host.getBatch(), subtitleText, ArcadeMadness.worldWidth / 2 - layoutSubtitle.width / 2, ArcadeMadness.worldHeight - 60);
        } else if(won.equals("lost")) {
            host.redFont.draw(host.getBatch(), subtitleText, ArcadeMadness.worldWidth / 2 - layoutSubtitle.width / 2, ArcadeMadness.worldHeight - 60);
        } else {
            font.draw(host.getBatch(), subtitleText, ArcadeMadness.worldWidth / 2 - layoutSubtitle.width / 2, ArcadeMadness.worldHeight - 60);
        }
        host.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
