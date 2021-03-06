package com.unit.ft_bird.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unit.ft_bird.controller.Controller;
import com.unit.ft_bird.database.BirdDB;
import com.unit.ft_bird.model.Background;
import com.unit.ft_bird.model.Bird;
import com.unit.ft_bird.model.BirdFont;
import com.unit.ft_bird.model.Button;
import com.unit.ft_bird.model.GameMode;
import com.unit.ft_bird.model.Image;
import com.unit.ft_bird.model.PipeCollector;
import com.unit.ft_bird.model.SkinScroller;

import java.util.ArrayList;

public class FtBird extends ApplicationAdapter {
	static public SpriteBatch	batch;
	private Background			gameBG;
	private Bird				flappyBird;
	private PipeCollector		pipeCollector;
	private Button				tap_tap,
								menuButton,
								skinsButton,
								okButton,
								playButton,
								scoreButton,
								arrowRightButton,
								arrowLeftButton;
	private Image				gameOverImage,
								skinChooserImage,
								fbLogoImage,
								scoreImage;
	private BirdFont			font;
	private Music die,
								flapWings;
	private SkinScroller		scroller;
	public static GameMode		gameMode;
	public static int			score = 0, maxScore;

	@Override
	public void create () {
		BirdDB.createDB();
		batch = new SpriteBatch();
		gameBG = new Background();
		flappyBird = new Bird();
		font = new BirdFont();
		pipeCollector = new PipeCollector();
		die = Gdx.audio.newMusic(Gdx.files.internal("data/sfx_die.wav"));
		die.setVolume(0.7f);
		die.setLooping(false);
		flapWings = Gdx.audio.newMusic(Gdx.files.internal("data/sfx_wing.wav"));
		flapWings.setVolume(0.7f);
		flapWings.setLooping(false);
		tap_tap = new Button(
				"data/flappy_tap_tap.atlas",
				Gdx.graphics.getWidth() / 3,
				Gdx.graphics.getHeight() / 6,
				Gdx.graphics.getWidth() / 2 - 150,
				Gdx.graphics.getHeight() / 2
		);
		menuButton = new Button(
				"data/menu_button.atlas",
				Gdx.graphics.getWidth() / 6,
				Gdx.graphics.getHeight() / 20,
				1,
				1
		);
		skinsButton = new Button(
				"data/skins_button.atlas",
				Gdx.graphics.getWidth() / 6,
				Gdx.graphics.getHeight() / 20,
				1,
				menuButton.getButtonHeight() + 2
		);
		gameOverImage = new Image(
				"data/game_over_image.atlas",
				6 * Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 4,
				Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 2
		);
		playButton = new Button(
				"data/play_button.atlas",
				Gdx.graphics.getWidth() / 3,
				Gdx.graphics.getHeight() / 8,
				gameOverImage.getPosition().x,
				gameOverImage.getPosition().y - gameOverImage.getImageHeight() / 2 - 50
		);
		scoreButton = new Button(
				"data/score_button.atlas",
				Gdx.graphics.getWidth() / 3,
				Gdx.graphics.getHeight() / 8,
				gameOverImage.getPosition().x + gameOverImage.getImageWidth() - Gdx.graphics.getWidth() / 3,
				gameOverImage.getPosition().y - gameOverImage.getImageHeight() / 2 - 50
		);
		skinChooserImage = new Image(
				"data/skin_chooser_image.atlas",
				6 * Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 4,
				Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getHeight() / 2
		);
		okButton = new Button(
				"data/ok_button.atlas",
				Gdx.graphics.getWidth() / 6,
				Gdx.graphics.getHeight() / 20,
				skinChooserImage.getPosition().x + skinChooserImage.getImageWidth() / 2 - Gdx.graphics.getWidth() / 12,
				skinChooserImage.getPosition().y - skinChooserImage.getImageHeight() / 3
		);
		arrowLeftButton = new Button(
				"data/arrow_left_button.atlas",
				Gdx.graphics.getWidth() / 30,
				Gdx.graphics.getHeight() / 20,
				skinChooserImage.getPosition().x + 20,
				skinChooserImage.getPosition().y + skinChooserImage.getImageHeight() / 2 - Gdx.graphics.getHeight() / 40
		);
		arrowRightButton = new Button(
				"data/arrow_right_button.atlas",
				Gdx.graphics.getWidth() / 30,
				Gdx.graphics.getHeight() / 20,
				skinChooserImage.getPosition().x + skinChooserImage.getImageWidth() - Gdx.graphics.getWidth() / 30 - 20,
				skinChooserImage.getPosition().y + skinChooserImage.getImageHeight() / 2 - Gdx.graphics.getHeight() / 40
		);
		Image fbYellowImage = new Image(
				"data/flappy_bird_animation.atlas",
				Gdx.app.getGraphics().getWidth() / 10,
				Gdx.app.getGraphics().getWidth() / 14,
				0,
				0
		);
		Image fbBlueImage = new Image(
				"data/flappy_bird_blue_animation.atlas",
				Gdx.app.getGraphics().getWidth() / 10,
				Gdx.app.getGraphics().getWidth() / 14,
				0,
				0
		);
		Image fbRedImage = new Image(
				"data/flappy_bird_red_animation.atlas",
				Gdx.app.getGraphics().getWidth() / 10,
				Gdx.app.getGraphics().getWidth() / 14,
				0,
				0
		);
		Image fbAbodnarImage = new Image(
				"data/abodnar_animation.atlas",
				Gdx.app.getGraphics().getWidth() / 10,
				Gdx.app.getGraphics().getWidth() / 14,
				0,
				0
		);
		Image fbUnitImage = new Image(
				"data/unit_animation.atlas",
				Gdx.app.getGraphics().getWidth() / 10,
				Gdx.app.getGraphics().getWidth() / 14,
				0,
				0
		);
		scroller = new SkinScroller(
				skinChooserImage.getPosition().x + arrowLeftButton.getButtonWidth() + Gdx.graphics.getWidth() / 15,
				skinChooserImage.getPosition().y + skinChooserImage.getImageHeight() / 2 - Gdx.graphics.getHeight() / 28,
				100
		);
		fbLogoImage = new Image(
				"data/fb_logo_image.atlas",
				3 * Gdx.app.getGraphics().getWidth() / 4,
				Gdx.app.getGraphics().getWidth() / 7,
				Gdx.app.getGraphics().getWidth() / 8,
				5 * Gdx.app.getGraphics().getHeight() / 6
		);
		scoreImage = new Image(
				"data/score_bg_image.atlas",
				Gdx.graphics.getHeight() / 3,
				6 * Gdx.graphics.getWidth() / 8,
				Gdx.graphics.getWidth() / 5,
				Gdx.graphics.getHeight() / 3
		);
		scroller.add(fbBlueImage);
		scroller.add(fbYellowImage);
		scroller.add(fbRedImage);
		scroller.add(fbAbodnarImage);
		scroller.add(fbUnitImage);
		Gdx.input.setInputProcessor(new Controller(this));
		gameMode = GameMode.MAIN_MENU;
	}

	public Music getFlapWings() {
		return flapWings;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		switch (gameMode) {
			case MAIN_MENU:
				renderMainMenu();
				break;
			case GAME:
				renderGame();
				break;
			case GAMEOVER:
				renderGameOver();
				break;
			case CHOOSE_SKIN:
				renderChooseSkin();
				break;
			case MENU:
				renderMenu();
				break;
			case SCORE_MENU:
				renderScoreMenu();
				break;
		}
		batch.end();
	}

	private void renderMainMenu() {
		gameBG.draw();
		tap_tap.draw();
		fbLogoImage.draw();
		pipeCollector.draw();
		gameBG.drawFloor();
		menuButton.draw();
		skinsButton.draw();
		flappyBird.draw();
		score = 0;
	}

	private void renderGame() {
		gameBG.draw();
		pipeCollector.draw();
		gameBG.drawFloor();
		flappyBird.draw();
		font.draw(
				Integer.toString(score / 2),
				1,
				Gdx.graphics.getHeight() - 20,
				Gdx.graphics.getWidth() / 300
		);
		flappyBird.update();
		pipeCollector.update();
		if (pipeCollector.checkCollision(flappyBird.getCollisionCircle())) {
			gameMode = GameMode.GAMEOVER;
			BirdDB.insertScore(score);
			maxScore = BirdDB.getMax() / 2;
			die.play();
		}
	}

	private void renderGameOver() {
		gameBG.draw();
		pipeCollector.draw();
		gameBG.drawFloor();
		flappyBird.draw();
		flappyBird.update();
		gameOverImage.draw();
		playButton.draw();
		scoreButton.draw();
		font.draw(
				Integer.toString(score / 2),
				gameOverImage.getPosition().x + 7 * gameOverImage.getImageWidth() / 10,
				gameOverImage.getPosition().y + 4 * gameOverImage.getImageHeight() / 6,
				Gdx.graphics.getWidth() / 500
		);
		font.draw(
				Integer.toString(maxScore),
				gameOverImage.getPosition().x + 7 * gameOverImage.getImageWidth() / 10,
				gameOverImage.getPosition().y + 3 * gameOverImage.getImageHeight() / 10,
				Gdx.graphics.getWidth() / 500
		);
	}

	private void renderChooseSkin() {
		gameBG.draw();
		gameBG.drawFloor();
		skinChooserImage.draw();
		okButton.draw();
		arrowLeftButton.draw();
		arrowRightButton.draw();
		scroller.draw();
	}

	private void renderMenu() {
		gameBG.draw();
		gameBG.drawFloor();
		fbLogoImage.draw();
		playButton.draw();
		scoreButton.draw();
	}

	private void renderScoreMenu() {
		gameBG.draw();
		gameBG.drawFloor();
		scoreImage.draw();
		ArrayList<Integer> scores = BirdDB.getDB();
		for (int i = 0; i < scores.size(); i++) {
			font.draw(
					Integer.toString(i + 1) + ") " + Integer.toString(scores.get(i) / 2),
					Gdx.graphics.getWidth() / 4,
					7 * Gdx.graphics.getHeight() / 10 - i * 100,
					Gdx.graphics.getWidth() / 500
			);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		tap_tap.dispose();
		skinsButton.dispose();
		flappyBird.dispose();
		menuButton.dispose();
		gameBG.dispose();
		font.dispose();
		playButton.dispose();
		gameOverImage.dispose();
		pipeCollector.dispose();
		skinChooserImage.dispose();
		okButton.dispose();
		arrowLeftButton.dispose();
		arrowRightButton.dispose();
		scoreButton.dispose();
		scroller.dispose();
		fbLogoImage.dispose();
		scoreImage.dispose();
	}

	public Bird getFlappyBird() {
		return flappyBird;
	}

	public Button getMenuButton() {
		return menuButton;
	}

	public Button getSkinsButton() {
		return skinsButton;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getPlayButton() {
		return playButton;
	}

	public Button getScoreButton() {
		return scoreButton;
	}

	public PipeCollector getPipeCollector() {
		return pipeCollector;
	}

	public Button getArrowRightButton() {
		return arrowRightButton;
	}

	public Button getArrowLeftButton() {
		return arrowLeftButton;
	}

	public SkinScroller getScroller() {
		return scroller;
	}
}
