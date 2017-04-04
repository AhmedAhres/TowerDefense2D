package Data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import helpers.Clock;
import static helpers.Artist.*;
import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	public static int Cash, Lives;
	private boolean leftMouseButtonDown, rightMouseButtonDown; // because update
																// is too fast
																// so a
	// click would be 4 clicks without
	// these booleans

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		Cash = 0;
		Lives = 0;
	}

	public void setup() {
		Cash = 50;
		Lives = 10;
	}

	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			return true;
		}
		return false;
	}

	public static void modifyLives(int amount) {
		Lives += amount;
	}

	public void Update() {
		for (Tower t : towerList) {
			t.update();
			t.draw();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) { // 0 is left mouse
			if (modifyCash(-20)) {
				towerList.add(new TowerCannonBlue(TowerType.CannonBlue,
						grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
						waveManager.getCurrentWave().getEnemyList()));
			}
		}

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) { // 1 is right
																// mouse
			if (modifyCash(-55)) {
				towerList.add(new TowerIce(TowerType.CannonIce,
						grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
						waveManager.getCurrentWave().getEnemyList()));
			}
		}

		leftMouseButtonDown = Mouse.isButtonDown(0);
		rightMouseButtonDown = Mouse.isButtonDown(1);

		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f); // speed of game increase
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f); // speed of game decrease
			}
		}
	}

}
