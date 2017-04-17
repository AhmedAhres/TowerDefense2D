package Data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonIce extends Tower {
	
	public TowerCannonIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileIceBall(super.type.projectileType, super.target, super.getX(), super.getY(),
				32, 32));
		super.target.reduceHiddenHealth(super.type.projectileType.damage);
	}
	
	

}
