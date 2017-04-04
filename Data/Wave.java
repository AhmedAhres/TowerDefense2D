package Data;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList; //because else towers keep shooting at enemy even if he is dead
	private int enemiesPerWave;
	private boolean waveCompleted;

	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		spawn();
	}

	private void spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(), 64, 64,
				enemyType.getSpeed(), enemyType.getHealth()));
	}

	public void update() {
		boolean allEnemiesDead = true; // assume that all enemies are dead
		if (enemyList.size() < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false; // there is at least one alive
				e.update();
				e.draw();
			} else
				enemyList.remove(e);
		}

		if (allEnemiesDead) {
			waveCompleted = true;
		}
	}

	public boolean isCompleted() {
		return waveCompleted;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}


}
