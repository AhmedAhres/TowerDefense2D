package Data;

import static helpers.Artist.QuickLoad;

import static helpers.Artist.DrawQuadTex;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackground;
	private Enemy[] enemyTypes;
	
	public Game(TileGrid grid) {
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemyTypes[0] = new EnemyAlien(2, 0, grid);
		enemyTypes[1] = new EnemyUFO(2, 0, grid);
		enemyTypes[2] = new EnemyPlane(2, 0, grid);
		waveManager = new WaveManager(enemyTypes, 3, 3);
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackground = QuickLoad("towers");
		setupUI();
	}

	private void setupUI() {
		gameUI = new UI();
		gameUI.createMenu("TowerPicker", 1280, 100, 192, 960, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("BlueCannon", "bluecannon");
		towerPickerMenu.quickAdd("IceCannon", "icebase");
	}

	private void updateUI() {
		gameUI.draw();
		gameUI.drawString(1310, 400, "Lives: " + Player.Lives);
		gameUI.drawString(1310, 450, "Cash: " + Player.Cash);
		gameUI.drawString(1310, 500, "Wave: " + waveManager.getWaveNumber());

		if (Mouse.next()) {
			// Left click
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerMenu.isButtonClicked("BlueCannon")) {
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("IceCannon")) {
					player.pickTower(new TowerCannonIce(TowerType.CannonIce, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
	}

	public void Update() {
		DrawQuadTex(menuBackground, 1280, 0, 192, 960);
		grid.draw();
		waveManager.Update();
		player.Update();
		updateUI();
	}

}
