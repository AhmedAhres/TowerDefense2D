package Data;


import org.lwjgl.LWJGLException;



import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import helpers.Clock;

import static helpers.Artist.*;

public class Boot {
    
    
    public Boot() {
        
        BeginSession();
        
        int[][] map = {
            {1, 1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
            {0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0},
            {0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
            {0, 0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,1,1},
            
        };
        
        TileGrid grid = new TileGrid(map);
        grid.SetTile(3, 4, grid.GetTile(5, 7).getType());
        Enemy e = new Enemy(QuickLoad("enemy"), grid.GetTile(10, 10), grid, 64, 64, 6);
        Wave wave = new Wave(10, e);
        
        Player player = new Player(grid);
        while(!Display.isCloseRequested()){
            Clock.update();
            
            
            
            grid.Draw();
            wave.Update();
            player.Update();
            
            Display.update();
            Display.sync(60);
            
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args){
        new Boot();
    }
}
