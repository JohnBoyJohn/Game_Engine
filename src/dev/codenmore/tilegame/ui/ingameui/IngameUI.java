/*
 * handle health used item on special button and enemy counter per level
 */

package dev.codenmore.tilegame.ui.ingameui;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;

public class IngameUI implements Settings{
	
	private Handler handler;
	private Talk talk;
	private Menu menu;
	private boolean isTalking, showMenu;
	
	public IngameUI(Handler handler){
		this.handler = handler;
		isTalking = false;
		talk = new Talk(handler);
	}
	
	public void tick(){
		isTalking = handler.getWorld().getEntityManager().getPlayer().isTalking();
		if(isTalking)
			talk.tick();
		if(showMenu)
			menu.tick();
			
	}

	public void render(Graphics g){
		if(isTalking)
			talk.render(g);
		if(showMenu)
			menu.render(g);
	}
}
