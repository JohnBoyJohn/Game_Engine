package dev.codenmore.tilegame.states;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.ui.ClickListener;
import dev.codenmore.tilegame.ui.UIImageButton;
import dev.codenmore.tilegame.ui.UIManager;

public class MenuState extends State {
	
	private UIManager uiManager;
	private int start_btn_x, start_btn_y;
	private int btn_width, btn_height;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		btn_width = 128;
		btn_height = 64;
		start_btn_x = (handler.getGame().getWidth() / 2) - (btn_width / 2);
		start_btn_y = (handler.getGame().getHeight() / 2);
		
		uiManager.addObject(new UIImageButton(start_btn_x, start_btn_y, btn_width, btn_height, Assets.start_btn, new ClickListener(){
			@Override
			public void onClick(){
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}
		}));
	}

	@Override
	public void tick() {
		uiManager.tick();
		
		//set Menu instantly to the GameState, menustate is skipped
		handler.getMouseManager().setUIManager(null);
		State.setState(handler.getGame().gameState);
	}

	@Override
	public void render(Graphics g) {
		uiManager.render(g);
	}
	
}
