package Data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonBase"), QuickLoad("cannonGun")}, 10, 1000, 3),
	CannonBlue(new Texture[]{QuickLoad("cannonBase"), QuickLoad("cannonGun")}, 30, 1000, 3),
	CannonIce(new Texture[]{QuickLoad("icebase"), QuickLoad("icegun")}, 30, 1000, 3);
	
	Texture[] textures;
	int damage, range;
	float firingSpeed;
	
	TowerType(Texture[] textures, int damage, int range, float firingSpeed) {
		this.textures = textures;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
	}

}
