package dev.codenmore.tilegame.ui.ingameui;

import java.awt.Color;
import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.creatures.NPC;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;

public class Talk implements Settings{

	private Handler handler;
	private String message;
	@SuppressWarnings("unused")
	private NPC npc;
	
	public Talk(Handler handler){
		this.handler = handler;
		message = "";
	}
	
	public void tick(){
		message = handler.getWorld().getEntityManager().getPlayer().getMessage();
	}
	
	public void render(Graphics g){
		//draw white rectangle
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT/6);
		
		//draw border of rectangle
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
		g2.setStroke(new java.awt.BasicStroke(3)); // thickness of 3.0f
		g2.setColor(Color.BLACK);
		g2.drawRect(1, 1, WIDTH-3, HEIGHT/6);
		
		//draw message
		Text.drawString(g, message, 50, 50, false, Color.BLACK, Assets.fontArial24);
	}
}
