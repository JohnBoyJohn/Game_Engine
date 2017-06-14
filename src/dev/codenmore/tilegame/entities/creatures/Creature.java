package dev.codenmore.tilegame.entities.creatures;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.Entity;
import dev.codenmore.tilegame.tiles.Tile;

public abstract class Creature extends Entity implements Settings{
	
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATURE_HEIGHT = 64;
	
	protected float speed;
	protected float xMove, yMove;
	protected int attack;

	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		attack = DEFAULT_ATTACK;
	}
	
	public void move(){
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	public void moveX(){
		if(xMove > 0){//Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILESIZE;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILESIZE) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILESIZE)){
				x += xMove;
			}else{
				x = tx * Tile.TILESIZE - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){//Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILESIZE;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILESIZE) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILESIZE)){
				x += xMove;
			}else{
				x = tx * Tile.TILESIZE + Tile.TILESIZE - bounds.x;
			}
		}
	}
	
	public void moveY(){
		if(yMove < 0){//Up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILESIZE;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILESIZE, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILESIZE, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILESIZE + Tile.TILESIZE - bounds.y;
			}
		}else if(yMove > 0){//Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILESIZE;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILESIZE, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILESIZE, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILESIZE - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	@Override
	public void knockBack(int xMove, int yMove){
		y = yMove;
		x = xMove;
	}
	
	//GETTERS SETTERS

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
}
