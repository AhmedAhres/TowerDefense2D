package Data;
import static helpers.Artist.*;

import org.newdawn.slick.opengl.Texture;

import UI.UI;

public class MainMenu {
	
	private Texture background;
	private UI menuUI;
	
	public MainMenu() {
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play", "play", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f)); //-128 for the center
	}
	
	public void update() {
		DrawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
	}

}
