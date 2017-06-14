package dev.codenmore.tilegame.entities.creatures.merchant;

import dev.codenmore.tilegame.Handler;

public class MerchantMenu {

	private Handler handler;
	private boolean active = false;
	
	public MerchantMenu(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		if(active){
			
		}
	}
	
	public void render(){
		if(active){
			
		}
	}

	//GETTERS & SETTERS
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
