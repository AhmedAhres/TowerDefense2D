package Data;

public class EnemyPlane extends Enemy {

	public EnemyPlane(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("plane");
		this.setSpeed(90f);
		this.setHealth(40);
	}
	
}
