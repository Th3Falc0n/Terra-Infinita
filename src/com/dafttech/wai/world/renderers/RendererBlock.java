package com.dafttech.wai.world.renderers;

import com.dafttech.wai.graphics.AbstractScreen;
import com.dafttech.wai.world.Player;
import com.dafttech.wai.world.Renderer;
import com.dafttech.wai.world.Tile;
import com.dafttech.wai.world.Vector2;

public class RendererBlock extends Renderer {
	public static Renderer $Instance = new RendererBlock();

	@Override
	public void draw(AbstractScreen screen, Tile tile, Player player) {
		Vector2 screenVec = tile.getPosition().toScreenPos(player);
		
		screen.batch.draw(tile.getImage(), screenVec.x, screenVec.y, 8, 8);
	}
}
