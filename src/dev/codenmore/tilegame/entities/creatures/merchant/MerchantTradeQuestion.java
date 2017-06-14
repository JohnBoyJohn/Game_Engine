package dev.codenmore.tilegame.entities.creatures.merchant;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.codenmore.tilegame.Handler;

public class MerchantTradeQuestion {

	private Handler handler;
	private int selectedOption = 0;
	private boolean active = false;
	
	public MerchantTradeQuestion(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		if(active){
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
				selectedOption--;
			}
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
				selectedOption++;
			}
			if(selectedOption < 0){
				selectedOption = 1;
			}else if(selectedOption > 1){
				selectedOption = 0;
			}
		}
	}
	
	public void render(Graphics g){
		if(active){
			//g.drawImage();
		}
	}
}
