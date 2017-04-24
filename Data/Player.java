package Data;

import org.lwjgl.input.Keyboard;
import static helpers.StateManager.setState;
import org.lwjgl.input.Mouse;
import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;
import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	public static int Cash, Lives, Score;
	private boolean leftMouseButtonDown, holdingTower;
	private Tower tempTower;
	// Since updated is too fast, so a click would be actually 4 clicks without
	// this boolean

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		Cash = 0;
		Lives = 0;
		Score = 0;
	}

	// Initialize cash and lives and score
	public void setup() {
		Cash = 100;
		Lives = 2;
		Score = 0;
	}

	//Check if we can afford the tower
	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			return true;
		}
		return false;
	}
	
	public static void modifyScore(int amount) {
		Score += amount;
	}

	public static void modifyLives(int amount) {
		Lives += amount;
	}

	public void Update() {

		// Update holding tower for when picking up to place them
		if (holdingTower) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}
		
		if (Lives <= 0) {
			StateManager.setState(GameState.MAINMENU);
		}

		// Update all towers in the game
		for (Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) { // 0 is left mouse
			placeTower();
		}

		leftMouseButtonDown = Mouse.isButtonDown(0);

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f); // speed of game increase
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f); // speed of game decrease
			}
		}
	}

	public void pickTower(Tower t) {
		tempTower = t;
		holdingTower = true;
	}

	private void placeTower() {
		Tile currentTile = getMouseTile();
		if (holdingTower) 
			if (!currentTile.getOccupied() && modifyCash(- tempTower.getPrice()))  {
				towerList.add(tempTower);
				currentTile.setOccupied(true);
				holdingTower = false;
				tempTower = null;
			}
		
	}

	private Tile getMouseTile() {
		return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}

}
