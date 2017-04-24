package Data;


public class ProjectileIceBall extends Projectile {

	public ProjectileIceBall(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	
	@Override
	public void damage() {
		//Divide speed of enemy by 1.1 each time
		super.getTarget().setSpeed(getTarget().getSpeed()/1.1);
		super.damage();
	}
	
}
