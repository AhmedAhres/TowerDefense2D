package Data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCanon {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private CopyOnWriteArrayList<Enemy> enemies;
	private Enemy target; // tracking enemy to shoot him
	private boolean targeted;

	public TowerCanon(Texture baseTexture, Tile startTile, int damage, int range, CopyOnWriteArrayList<Enemy> enemies) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		this.range = range;
		this.firingSpeed = 3;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
		this.targeted = false;
	}

	private Enemy acquireTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		for (Enemy e : enemies) {
			if (isInRange(e) && findDistance(e) < closestDistance) {
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		if (closest != null) {
			targeted = true;
		}
		return closest;
	}

	private boolean isInRange(Enemy e) { // range of enemy when getting shot
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range)
			return true;
		else
			return false;
	}

	private float calculateAngle() { // calculate angle for tower to shoot enemy
		// x and y are position of tower that shoots
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;

	}

	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}

	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
	}

	private void Shoot() {
		timeSinceLastShot = 0;
		// bullet needs to spawn in the center of the tile where the tower is,
		// so we need calculations to find the center
		projectiles.add(new ProjectileIceBall(QuickLoad("icegun"), target, x + TILE_SIZE / 2 - TILE_SIZE / 4,
				y + TILE_SIZE / 2 - TILE_SIZE / 4, 32, 32, 900, 10));
	}

	public void Update() {

		if (!targeted) {
			target = acquireTarget();
		}

		if (target == null || target.isAlive() == false) {
			targeted = false;
		}

		timeSinceLastShot += Delta();
		if (timeSinceLastShot > firingSpeed)
			Shoot();

		for (Projectile p : projectiles)
			p.update();

		angle = calculateAngle();
		Draw();
	}

	public void Draw() {
		DrawQuadTex(baseTexture, x, y, width, height);
		DrawQuadTexRot(cannonTexture, x, y, width, height, angle);
	}

}
