package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import Data.Tile;
import Data.TileGrid;
import Data.TileType;

public class Leveler {

	public static void saveMap(String mapName, TileGrid grid) {
		String mapData = "";
		for (int i = 0; i < grid.getTilesWide(); i++) {
			for (int j = 0; j < grid.getTilesHigh(); j++) {
				mapData += getTileID(grid.getTile(i, j));
			}
		}
		try {
			File file = new File(mapName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TileGrid loadMap(String mapName) {
		TileGrid grid = new TileGrid();
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapName)); //read file
			String data = br.readLine();
			for (int i = 0; i < grid.getTilesWide(); i++) {
				for (int j = 0; j < grid.getTilesHigh(); j++) {
					grid.setTile(i, j, getTileType(data.substring(i * grid.getTilesHigh() + j, 
							i * grid.getTilesHigh() + j + 1))); //take one character at a time to load the map
																//same formula but with + 1, hence it takes letter by letter
				}
			} br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return grid;
	}

	public static TileType getTileType(String ID) {
		TileType type = TileType.NULL;
		switch (ID) {
		case "0":
			type = TileType.Grass;
			break;
		case "1":
			type = TileType.Dirt;
			break;
		case "2":
			type = TileType.Water;
			break;
		case "3":
			type = TileType.NULL;
			break;
		}

		return type;
	}

	public static String getTileID(Tile t) {
		String ID = "e";
		switch (t.getType()) {
		case Grass:
			ID = "0";
			break;
		case Dirt:
			ID = "1";
			break;
		case Water:
			ID = "2";
			break;
		case NULL:
			ID = "3";
			break;
		}
		return ID;
	}

}
