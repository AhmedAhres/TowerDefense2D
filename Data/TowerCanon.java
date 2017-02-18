package Data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class TowerCanon {
    
    private float x, y, timeSinceLastShot, firingSpeed;
    private int width, height, damage;
    private Texture baseTexture, cannonTexture;
    private Tile startTile;
    private ArrayList<Projectile> projectiles;
    
    public TowerCanon(Texture baseTexture, Tile startTile, int damage) {
        this.baseTexture = baseTexture;
        this.cannonTexture = QuickLoad("cannonGun");
        this.startTile = startTile;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.width = (int) startTile.getWidth();
        this.height = (int) startTile.getHeight();
        this.damage = damage;
        this.firingSpeed = 30;
        this.timeSinceLastShot = 0;
    }
    
    private void Shoot() {
        timeSinceLastShot = 0;
    }
    
    public void Update() {
        
        timeSinceLastShot += Delta();
        if (timeSinceLastShot > firingSpeed)
            Shoot();
        
    }
    
    public void Draw() {
        DrawQuadTex(baseTexture, x, y, width, height);
        DrawQuadTex(cannonTexture, x, y, width, height);
    }
    
}
