package helpers;

import Data.Game;
import Data.Editor;
import Data.MainMenu;
import Data.TileGrid;

import static helpers.Leveler.LoadMap;

public class StateManager {
	
	public static enum GameState {
		MAINMENU, GAME, EDITOR
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;
	
	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	
	static TileGrid map = LoadMap("mapTest1");
	
	public static void update() {
		switch (gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAME:
			if (game == null) 
				game = new Game(map);
			game.Update();
			break;
		case EDITOR:
			if (editor == null) {
				editor = new Editor();
			}
			editor.update();
			break;
		}
		
		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		
		framesInCurrentSecond++;
	}
	
	public static void setState (GameState newState) {
		gameState = newState;
	}

}
