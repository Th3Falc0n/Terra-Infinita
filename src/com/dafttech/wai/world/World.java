package com.dafttech.wai.world;

public class World {
	Tile[][] map;
	
	public World(int width, int height) {
		map = new Tile[width][height];
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				map[x][y] = new Tile(Material.get("dirt"));
			}
		}
	}
	
	public void drawWorld(Player player) {
		//TODO: Render Bereich an Bildschirm anpassen
		for(int x = (int)(player.getPosition().x - 10); x < (int)(player.getPosition().x + 10); x++) {
			for(int y = (int)(player.getPosition().y - 10); x < (int)(player.getPosition().y + 10); x++) {
				if(x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
					map[x][y].draw();
				}
			}
		}
	}
}
