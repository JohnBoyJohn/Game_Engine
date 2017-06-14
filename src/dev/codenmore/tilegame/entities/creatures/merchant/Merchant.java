package dev.codenmore.tilegame.entities.creatures.merchant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.entities.creatures.NPC;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.items.Item;
import dev.codenmore.tilegame.monsters.Monster;

public class Merchant extends NPC{
	
	private boolean activeTradeQuestion = false;
	private ArrayList<Item> items;
	private BufferedImage image;
	
	private MerchantTradeQuestion merchantTradeQuestion;

	public Merchant(Handler handler, int id, float x, float y, int width, int height, String message,
			ArrayList<Monster> monsters, String name) {
		super(handler, id, x, y, width, height, message, null, name);
		this.items = new ArrayList<Item>();
		items.add(Item.attBerry);
		items.add(Item.defBerry);
		items.add(Item.hpBerry);
		items.add(Item.mpBerry);
		image = Assets.enemy_down[0];
		this.message += " Möchtest du handeln?";
	}
	
	@Override
	public void tick(){
		if(active && activeTradeQuestion){
			//draw merchant main menu
			System.out.println("Merchant Trade Question Menu is active");
		}else if(active){
			//draw answers of trade question
			
		}else{
			//tick merchant animation
			
		}
	}
	
	@Override
	public void render(Graphics g) {
		//draw merchant on map
		g.drawImage(image, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	//GETTERS & SETTERS
	public boolean isActiveTradeQuestion() {
		return activeTradeQuestion;
	}

	public void setActiveTardeQuestion(boolean activeTradeQuestion) {
		this.activeTradeQuestion = activeTradeQuestion;
	}
}
