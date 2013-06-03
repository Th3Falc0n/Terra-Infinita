package com.dafttech.wai;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dafttech.wai.world.Material;
import com.dafttech.wai.world.Player;
import com.dafttech.wai.world.World;

public class Game extends BasicGame {
	
	Player localPlayer = new Player();
	World localWorld = new World(5000, 3000);

	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics gra) throws SlickException {
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Material.init();
	}

	@Override
	public void update(GameContainer gc, int time) throws SlickException {
		
	}
	
}
