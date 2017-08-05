package Data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonBase"), QuickLoad("cannonGun")}, ProjectileType.CannonBall, 30, 1000, 4, 15),
	CannonBlue(new Texture[]{QuickLoad("bluecannon"), QuickLoad("bluebullet")}, ProjectileType.CannonBall, 30, 1000, 3, 10),
	CannonIce(new Texture[]{QuickLoad("icebase"), QuickLoad("icegun")}, ProjectileType.Iceball, 30, 1000, 5, 20);
	
	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, price;
	float firingSpeed;
	
	TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int price) {
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.price = price;
	}

}
