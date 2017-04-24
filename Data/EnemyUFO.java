package Data;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("enemy");
		this.setSpeed(60);
	}

}
