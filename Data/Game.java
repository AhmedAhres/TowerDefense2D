package Data;

import static helpers.Artist.QuickLoad;
import sun.audio.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static helpers.Artist.DrawQuadTex;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;
import sun.applet.Main;

public class Game {

	private TileGrid grid;
	private Player player2;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackground;
	private Enemy[] enemyTypes;
	public boolean isFinished = false;
	
	public Game(TileGrid grid) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemyTypes[0] = new EnemyAlien(0, 4, grid);
		enemyTypes[1] = new EnemyUFO(0, 4, grid);
		enemyTypes[2] = new EnemyPlane(0, 4, grid);
		waveManager = new WaveManager(enemyTypes, 2);
//		try {
//			theStream = new AudioStream(new FileInputStream("/res/towerdefense"));
//			AudioPlayer.player.start(theStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		playSound("towerdefense.wav");
		player2 = new Player(grid, waveManager);
		player2.setup();
		this.menuBackground = QuickLoad("towers");
		setupUI();
		this.isFinished = false;
	}
	
	private void setNull(Game game) {
		game = null;
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.createMenu("TowerPicker", 1088, 100, 192, 960, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("BlueCannon", "bluecannon");
		towerPickerMenu.quickAdd("IceCannon", "icebase");
		towerPickerMenu.quickAdd("RedCannon", "cannonBase");
	}
	
	// Method to play background music, run in a separate thread
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		        Main.class.getResourceAsStream("/res/" + url));
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawString(1150, 300, "Score: " + Player.Score);
		gameUI.drawString(1150, 370, "Cash: " + Player.Cash);
		gameUI.drawString(1150, 440, "Lives: " + Player.Lives);
		gameUI.drawString(1150, 510, "Wave: " + waveManager.getWaveNumber());

		if (Mouse.next()) {
			// Left click
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerMenu.isButtonClicked("BlueCannon")) {
					player2.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("RedCannon")) {
					player2.pickTower(new TowerCannonBlue(TowerType.CannonRed, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("IceCannon")) {
					player2.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
	}

	public void Update() {
		grid.draw();
		DrawQuadTex(menuBackground, 1088, 0, 192, 960);
		waveManager.Update();
		player2.Update();
		updateUI();
		if (Player.Lives <= 0) {
			StateManager.setNull(GameState.GAME);
			Player.Score = 0;
			Player.Cash = 30;
			Player.Lives = 1;
			waveManager.resetWave();
			StateManager.setState(GameState.MAINMENU);
		}
	}
}
