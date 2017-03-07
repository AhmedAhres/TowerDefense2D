package Data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCanon> towerList;
	private boolean leftMouseButtonDown; // because update is too fast so a
											// click would be 4 clicks without
											// this boolean

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCanon>();
	}

	public void setTile() {
		grid.SetTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64),
				types[index]);
	}

	public void Update() {
		for (TowerCanon t : towerList) {
			t.Update();
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) { //0 is left mouse
			towerList.add(new TowerCanon(QuickLoad("cannonBase"), grid.GetTile(Mouse.getX() / 64, (HEIGHT - Mouse.getY() - 1) / 64), 10,
					waveManager.getCurrentWave().getEnemyList()));
		}

		leftMouseButtonDown = Mouse.isButtonDown(0);
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f); //speed of game increase
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f); //speed of game decrease
			}
		}
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}

}
