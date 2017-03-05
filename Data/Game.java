package Data;

import static helpers.Artist.QuickLoad;
import static helpers.Clock.*;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;


	TowerCanon tower;

	public Game(int[][] map) {

		grid = new TileGrid(map);
		waveManager = new WaveManager(new Enemy(QuickLoad("enemy"), grid.GetTile(12, 9), grid, 64, 64, 70), 2, 2);
		player = new Player(grid, waveManager);

	}

	public void Update() {
		grid.Draw();
		waveManager.Update();
		player.Update();
		tower.Update();
	}

}
