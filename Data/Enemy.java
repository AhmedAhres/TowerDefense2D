package Data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;

import java.util.ArrayList;

import static Data.TileGrid.*;
import static helpers.Artist.*;

public class Enemy {
	private int width, height, health, currentCheckpoint;
	private float speed, x, y;
	private Tile startTile;
	private Texture texture;
	private boolean first = true, alive = true;
	private TileGrid grid;

	private ArrayList<CheckPoint> checkpoints;
	private int[] directions;

	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed) {
		this.texture = texture;
		this.startTile = startTile;
		this.grid = grid;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.checkpoints = new ArrayList<CheckPoint>();
		this.directions = new int[2];

		// x direction
		this.directions[0] = 0;

		// y direction
		this.directions[1] = 0;
		directions = FindNextD(startTile);
		this.currentCheckpoint = 0;
		PopulateCheckpointList();
	}

	private boolean CheckpointReached() {
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
	private void PopulateCheckpointList() {
		checkpoints.add(FindNextC(startTile, directions = FindNextD(startTile)));

		int counter = 0;
		boolean cont = true;
		while (cont) {
			int[] currentD = FindNextD(checkpoints.get(counter).getTile());
			// Check fi a next direction/checkpoints exists, end after 20
			// checkpoints (arbitrary)
			if (currentD[0] == 2 || counter == 20) {
				cont = false; // cannot continue anymore because it refers to no
								// directions found
			} else {
				checkpoints.add(FindNextC(checkpoints.get(counter).getTile(),
						directions = FindNextD(checkpoints.get(counter).getTile())));
			}
			counter++;
		}

	}

	public void Draw() {
		DrawQuadTex(texture, x, y, width, height);
	}

	public void Update() {
		if (first)
			first = false;

		else {
			if (CheckpointReached()) {
				if (currentCheckpoint + 1 == checkpoints.size())
					Die();
				else
					currentCheckpoint++;
			} else { // move them
				x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}

		}
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
							.GetTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				counter -= 1;
				next = grid.GetTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}
			counter++;

		}
		c = new CheckPoint(next, dir[0], dir[1]);

		return c;
	}

	private int[] FindNextD(Tile start) { // Find Next Direction
		int[] dir = new int[2];
		Tile u = grid.GetTile(start.getXPlace(), start.getYPlace() - 1); // up
		Tile d = grid.GetTile(start.getXPlace(), start.getYPlace() + 1); // down
		Tile r = grid.GetTile(start.getXPlace() + 1, start.getYPlace()); // right
		Tile l = grid.GetTile(start.getXPlace() - 1, start.getYPlace()); // left

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

	private void Die() { // killing the enemy
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

	public int getHealth() {
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
