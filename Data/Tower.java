package Data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRot;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Entity {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range;
	private Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean targeted;
	private ArrayList<Projectile> projectiles;
	
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = new CopyOnWriteArrayList<Enemy>();
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
		this.enemies = enemies;
	}
	
	private Enemy acquireTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		for (Enemy e : enemies) {
			if (isInRange(e) && findDistance(e) < closestDistance && e.isAlive()) {
				closestDistance = findDistance(e);
				closest = e;
			}
		}
		if (closest != null) {
			targeted = true;
		}
		return closest;
	}
	
	public void Shoot() {
		timeSinceLastShot = 0;
		// bullet needs to spawn in the center of the tile where the tower is,
		// so we need calculations to find the center
		projectiles.add(new ProjectileIceBall(QuickLoad("icegun"), target, x + TILE_SIZE / 2 - TILE_SIZE / 4,
				y + TILE_SIZE / 2 - TILE_SIZE / 4, 32, 32, 900, 10));
	}
	
	private boolean isInRange(Enemy e) { // range of enemy when getting shot
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range)
			return true;
		return false;
	}
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
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
	
	public void update() {

		if (!targeted) {
			target = acquireTarget();
		} else {
			if (timeSinceLastShot > firingSpeed)
				Shoot();
		}

		if (target == null || target.isAlive() == false) {
			targeted = false;
		}

		timeSinceLastShot += Delta();

		for (Projectile p : projectiles)
			p.update();

		angle = calculateAngle();
		draw();
	}

	public void draw() {
		DrawQuadTex(textures[0], x, y, width, height);
		if (textures.length > 1) {
			for (int i = 1; i < textures.length; i++) {
				DrawQuadTexRot(textures[i], x, y, width, height, angle);
			}
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Enemy getTarget() {
		return target;
	}
}
