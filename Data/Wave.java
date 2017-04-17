package Data;

import static helpers.Clock.Delta;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList; //because else towers keep shooting at enemy even if he is dead
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;

	public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}

	private void spawn() {
		int currentEnemy = 0;
		Random random = new Random();
		currentEnemy = random.nextInt(enemyTypes.length);
		
		enemyList.add(new Enemy(enemyTypes[currentEnemy].getTexture(), enemyTypes[currentEnemy].getStartTile(), enemyTypes[currentEnemy].getTileGrid(), 64, 64,
				enemyTypes[currentEnemy].getSpeed(), enemyTypes[currentEnemy].getHealth()));
		enemiesSpawned++;
	}

	public void update() {
		// Assume that all enemies are dead until for loop proves otherwise
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
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
