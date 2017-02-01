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
				{0, 1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0, 0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0},
				
		};
		
		TileGrid grid = new TileGrid(map);
		grid.SetTile(3, 4, grid.GetTile(2, 4).getType());
		Enemy e = new Enemy(QuickLoad("enemy"), grid.GetTile(10, 10), 64, 64, 4);
		
		while(!Display.isCloseRequested()){
			Clock.update();
			e.Update(); //make the enemy move
			
			grid.Draw();
			e.Draw();
			Display.update();
			Display.sync(60);
			
		}
		
		Display.destroy();
	}
	
	public static void main(String[] args){
		new Boot();
	}
}
