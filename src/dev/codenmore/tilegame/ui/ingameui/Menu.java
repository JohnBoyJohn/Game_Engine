package dev.codenmore.tilegame.ui.ingameui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.ui.ingameui.monstermenu.MonsterMenu;

public class Menu implements Settings{
	
	private Handler handler;
	private MonsterMenu monsterMenu;
	private String[] menues = {"Monsters", "Items", "Save", "Options", "Exit"};

	private boolean active = false;
	private boolean submenuActive = false;
	
	private int selectedMenu = 0;
	private BufferedImage arrow = Assets.menu_arrow;
	
	public Menu(Handler handler){
		this.handler = handler;
		monsterMenu = new MonsterMenu(handler);
	}
	
	public void tick(){
		//submenus tick
		if(submenuActive){
			monsterMenu.tick();
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_M))
			active = !active;
		if(!active)
			return;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedMenu--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedMenu++;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER))
			openMenu();
		
		if(selectedMenu < 0)
			selectedMenu = menues.length - 1;
		else if(selectedMenu >= menues.length)
			selectedMenu = 0;
	}

	public void render(Graphics g){
		if(monsterMenu.isActive())
			monsterMenu.render(g);
		
		if(!active)
			return;
		
		//draw background
		g.drawImage(Assets.mainMenu_background, MENU_X, MENU_Y, MENU_WIDTH, MENU_HEIGHT, null);
		
		//draw menu text
		for(int i = 1; i <= menues.length; i++){
			Text.drawString(g, menues[i-1], MENU_X + MENU_FONT_X_OFFSET, 
					MENU_Y + MENU_FONT_Y_OFFSET + MENU_FONT_Y_ITERATOR * i, 
					false, Color.BLACK, Assets.font28);
		}
		
		//draw arrow
		g.drawImage(arrow, MENU_X + MENU_ARROW_X_OFFSET,
				MENU_Y + MENU_ARROW_Y_OFFSET + MENU_FONT_Y_OFFSET + MENU_FONT_Y_ITERATOR * (selectedMenu + 1), 
				arrow.getWidth(), arrow.getHeight(), null);		
	}
	
	private void openMenu() {
		if(menues[selectedMenu].equals("Monsters")){
			monsterMenu.setActive(true);
			active = false;
			submenuActive = true;
		}else if(menues[selectedMenu].equals("Exit")){
			System.exit(0);
		}
	}

	public String[] getMenues() {
		return menues;
	}

	public void setMenues(String[] menues) {
		this.menues = menues;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public MonsterMenu getMonsterMenu() {
		return monsterMenu;
	}

	public void setMonsterMenu(MonsterMenu monsterMenu) {
		this.monsterMenu = monsterMenu;
	}

	public boolean isSubmenuActive() {
		return submenuActive;
	}

	public void setSubmenuActive(boolean submenuActive) {
		this.submenuActive = submenuActive;
	}
}
