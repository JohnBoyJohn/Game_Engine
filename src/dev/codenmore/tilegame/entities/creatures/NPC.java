package dev.codenmore.tilegame.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.monsters.Monster;

public class NPC extends Creature{
	
	private BufferedImage image;
	protected String message;
	private ArrayList<Monster> monsters;
	private boolean hasMonsters = false;
	private String name;
	private int id;

	public NPC(Handler handler, int id, float x, float y, int width, int height, String message, ArrayList<Monster> monsters, String name) {
		super(handler, x, y, width, height);
		bounds.x = 16;
		bounds.y = 12;
		bounds.width = 32;
		bounds.height = 54;
		
		this.message = message;
		this.monsters = monsters;
		if(this.monsters != null)
			hasMonsters = true;
		
		image = Assets.enemy_down[0];
		this.name = name;
		this.id = id;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		renderNPC(g);
	}

	private void renderNPC(Graphics g) {
		g.drawImage(image, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}

	@Override
	public void die(){}

	//GETTERS & SETTERS
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String var){
		message = var;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public boolean isHasMonsters() {
		return hasMonsters;
	}

	public void setHasMonsters(boolean hasMonsters) {
		this.hasMonsters = hasMonsters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
}
