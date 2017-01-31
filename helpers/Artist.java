package helpers;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {
	
	public static final int WIDTH = 1280, HEIGHT = 960;
	
	public static void BeginSession(){
		Display.setTitle("Tower Defense");
		try {
		Display.setDisplayMode(new DisplayMode(600,400));
		Display.create();
		
	} catch(LWJGLException e){
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,WIDTH,HEIGHT,0,1,-1); //what the screen sees, top bottom right and left
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void DrawQuad(float x, float y, float width, float height){
		glBegin(GL_QUADS);
		glVertex2f(x, y); //top left corner
		glVertex2f(x + width,y); //top right corner
		glVertex2f(x + width,y + height); //bottom right corner
		glVertex2f(x,y + height); //bottom left corner
		glEnd();
	}
	
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2d(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
		
	}
	
	public static Texture LoadTexture(String path, String fileType){
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
		tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e){
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture QuickLoad(String name){
		Texture tex = null;
		tex = LoadTexture("res/" + name + ".png", "PNG");
		return tex;
	}
	
	
	

}
