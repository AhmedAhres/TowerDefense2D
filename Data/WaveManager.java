package Data;

public class WaveManager {

	private float timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Enemy[] enemyTypes;
	private Wave currentWave;

	public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies) {
		this.enemyTypes = enemyTypes;
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemiesPerWave = 1;
		this.waveNumber = 0;
		this.currentWave = null;
		newWave();
	}

	public void Update() {
		if (!currentWave.isCompleted())
			currentWave.update();
		else
			newWave();
	}

	private void newWave() {
		currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave);
		waveNumber++;
		enemiesPerWave++;
	}

	public Wave getCurrentWave() {
		return currentWave;
	}
	
	public void resetWave() {
		waveNumber = 1;
	}
	
	public int getEnemiesPerWave() {
		return enemiesPerWave;
	}
	
	public int getWaveNumber() {
		return waveNumber;
	}

}
