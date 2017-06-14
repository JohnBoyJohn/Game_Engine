package dev.codenmore.tilegame.entities.specials;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.Entity;
import dev.codenmore.tilegame.gfx.Assets;

public class WarpTile extends Entity implements Settings{
	
	private Handler handler;
	private boolean visible;
	private boolean warp;
	
	private Rectangle rec;

	public WarpTile(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		visible = false;
		warp = false;
		rec = new Rectangle((int) x, (int) y, TILESIZE, TILESIZE);
		bounds.width = 0;
		bounds.height = 0;
	}

	@Override
	public void tick() {
		if(!visible){
			visible = true;
		}else{
			//check if player enter the tile
			checkForPlayerOnTile();
		}
	}

	private void checkForPlayerOnTile() {
		if(rec.intersects(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0))){
			System.out.println("Warp to next level");
			warp = true;
		}
	}

	@Override
	public void render(Graphics g) {
		if(visible)
			g.drawImage(Assets.warp_tile, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), TILESIZE, TILESIZE, null);
	}

	@Override
	public void die() {
		
	}
	
	@Override
	public void hurt(int amt){
		return;
	}
	
	@Override
	public void knockBack(int xMove, int yMove) {
		
	}

	//GETTERS & SETTERS
	public boolean isWarp() {
		return warp;
	}

	public void setWarp(boolean warp) {
		this.warp = warp;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
