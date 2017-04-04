package Data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;
import java.util.ArrayList;
import static helpers.Artist.*;

public class Enemy implements Entity {
	private int width, height, currentCheckpoint;
	private float speed, x, y, health, startHealth;
	private Tile startTile;
	private Texture texture, healthBackground, healthForeground, healthBorder;
	private boolean first = true, alive = true;
	private TileGrid grid;

	private ArrayList<CheckPoint> checkpoints;
	private int[] directions;

	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health) {
		this.texture = texture;
		this.healthBackground = QuickLoad("healthbackground");
		this.healthForeground = QuickLoad("healthforeground");
		this.healthBorder = QuickLoad("healthborder");
		this.startTile = startTile;
		this.grid = grid;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.startHealth = health;
		this.checkpoints = new ArrayList<CheckPoint>();
		this.directions = new int[2];

		// x direction
		this.directions[0] = 0;

		// y direction
		this.directions[1] = 0;
		directions = findNextD(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}

	private boolean checkpointReached() {
		boolean reached = false;
		Tile t = checkpoints.get(currentCheckpoint).getTile();
		if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
			// Check if position reached tile within variance of 3 (which is an
			// arbitrary number)
			reached = true;
			x = t.getX();
			y = t.getY();
		}

		return reached;
	}

	// enemy pathfinding
	private void populateCheckpointList() {
		checkpoints.add(FindNextC(startTile, directions = findNextD(startTile)));

		int counter = 0;
		boolean cont = true;
		while (cont) {
			int[] currentD = findNextD(checkpoints.get(counter).getTile());
			// Check fi a next direction/checkpoints exists, end after 20
			// checkpoints (arbitrary)
			if (currentD[0] == 2 || counter == 20) {
				cont = false; // cannot continue anymore because it refers to no
								// directions found
			} else {
				checkpoints.add(FindNextC(checkpoints.get(counter).getTile(),
						directions = findNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}

	}

	public void draw() {
		float healthPercentage = health / startHealth;
		DrawQuadTex(texture, x, y, width, height);
		DrawQuadTex(healthBackground, x, y - 16, width, 8);
		DrawQuadTex(healthForeground, x, y - 16, TILE_SIZE * healthPercentage, 8);
		DrawQuadTex(healthBorder, x, y - 16, width, 8);
	}

	public void update() {
		if (first)
			first = false;

		else {
			if (checkpointReached()) {
				if (currentCheckpoint + 1 == checkpoints.size())
					endOfMazeReached();
				else
					currentCheckpoint++;
			} else { // move them
				x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}

		}
	}
	
	private void endOfMazeReached() {
		Player.modifyLives(-1);
		die();
	}

	// Finding the checkpoint i.e corner to stop
	private CheckPoint FindNextC(Tile s, int[] dir) {
		Tile next = null;
		CheckPoint c = null;
		// boolean to decide if next checkpoint is found
		boolean found = false;
		int counter = 1;

		while (!found) {

			if (s.getXPlace() + dir[0] * counter == grid.getTilesWide()
					|| s.getYPlace() + dir[1] * counter == grid.getTilesHigh() || s.getType() != grid
							.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				counter -= 1;
				next = grid.getTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}
			counter++;

		}
		c = new CheckPoint(next, dir[0], dir[1]);

		return c;
	}

	private int[] findNextD(Tile start) { // Find Next Direction
		int[] dir = new int[2];
		Tile u = grid.getTile(start.getXPlace(), start.getYPlace() - 1); // up
		Tile d = grid.getTile(start.getXPlace(), start.getYPlace() + 1); // down
		Tile r = grid.getTile(start.getXPlace() + 1, start.getYPlace()); // right
		Tile l = grid.getTile(start.getXPlace() - 1, start.getYPlace()); // left

		// choose which directions to go to when finding a corner
		if (start.getType() == u.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		} else if (start.getType() == r.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		} else if (start.getType() == d.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		} else if (start.getType() == l.getType() && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		} else {
			dir[0] = 2;
			dir[1] = 2;
		}
		return dir;
	}

	public void damage(int amount) { // damage from projectiles
		health -= amount;
		if (health <= 0) {
			die();
		}
		Player.modifyCash(5);
	}

	private void die() { // killing the enemy
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public TileGrid getTileGrid() {
		return grid;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

}
