package Data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Clock.*;

import static helpers.Artist.*;

public class Enemy {
	private int width, height, health;
	private float speed, x, y;
	private Tile startTile;
	private Texture texture;
	private boolean first = true;
	
	public Enemy(Texture texture, Tile startTile, int width, int height, float speed){
		this.texture = texture;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.speed = speed;
	}
	public void Draw(){
		DrawQuadTex(texture, x, y, width, height);
	}
	
	public void Update() {
		if(first) first = false;
		else x += Delta() * speed;
	}

}
