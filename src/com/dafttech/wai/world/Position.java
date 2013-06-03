package com.dafttech.wai.world;

public class Position {
	int x, y;
	
	public Position(int vx, int vy) {
		x = vx;
		y = vy;
	}
	
	public Vector2 toScreenPos(Player player) {
		return new Vector2(player.position.x - x, player.position.y - y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int v) {
		x = v;
	}
	
	public void setY(int v) {
		y = v;
	}
}
