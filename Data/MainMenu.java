package Data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {

	private Texture background;
	private UI menuUI;

	public MainMenu() {
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play", "play", WIDTH / 2 - 128, (int) (HEIGHT * 0.40f)); // -128 for the center																			
		menuUI.addButton("Editor", "editor", WIDTH / 2 - 128, (int) (HEIGHT * 0.50f));
		menuUI.addButton("Quit", "quit", WIDTH / 2 - 128, (int) (HEIGHT * 0.60f));
	}

	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Play")) 
				StateManager.setState(GameState.GAME);
			if (menuUI.isButtonClicked("Editor")) 
				StateManager.setState(GameState.EDITOR);
			if (menuUI.isButtonClicked("Quit")) 
				System.exit(0); //quit game
		}
	}

	public void update() {
		DrawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}

}
