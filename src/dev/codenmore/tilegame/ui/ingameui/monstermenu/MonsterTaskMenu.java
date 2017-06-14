package dev.codenmore.tilegame.ui.ingameui.monstermenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.monsters.Monster_Settings;

public class MonsterTaskMenu implements Settings, Monster_Settings{
	
	private Handler handler;
	private boolean active = false;
	private boolean arrange = false;
	private int selectedMonsterTask = 0;
	private int MONSTERTASKMENU_WIDTH = 220, 
			MONSTERTASKMENU_HEIGHT = 225,
			MONSTERTASKMENU_X = WIDTH/2 - MONSTERTASKMENU_WIDTH/2 - 13, 
			MONSTERTASKMENU_Y = HEIGHT/2 - MONSTERTASKMENU_HEIGHT/2,
			MONSTERTASKMENU_FONT_Y_OFFSET = -10,
			MONSTERTASKMENU_FONT_Y_ITERATOR = 50; 
	
	public MonsterTaskMenu(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			setActive(false);
		}

		if(!active)
			return;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedMonsterTask--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedMonsterTask++;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			followTask();
			return;
		}		
		
		if(selectedMonsterTask < 0)
			selectedMonsterTask = handler.getWorld().getEntityManager().getPlayer().getMonsters().size() - 1;
		else if(selectedMonsterTask >= MONSTERTASKS.length)
			selectedMonsterTask = 0;
	}
	
	private void followTask() {
		System.out.println("Do Monster Menu Task: " + MONSTERTASKS[selectedMonsterTask]);
		if(MONSTERTASKS[selectedMonsterTask].equals("arrange")){
			setActive(false);
			arrange = true;
		}else if(MONSTERTASKS[selectedMonsterTask].equals("let free")){
			MonsterTaskHandler.deleteMonster(handler.getWorld().getEntityManager().getPlayer().getMenu().getMonsterMenu().getSelectedMonster());
			setActive(false);
		}
	}

	public void render(Graphics g){
		if(!active)
			return;
		
		g.drawImage(Assets.monsterTaskMenu_background, MONSTERTASKMENU_X, MONSTERTASKMENU_Y, MONSTERTASKMENU_WIDTH, MONSTERTASKMENU_HEIGHT, null);
		
		//draw monster task names
		for(int i = 0; i < MONSTERTASKS.length; i++){
			Text.drawString(g, MONSTERTASKS[i], MONSTERTASKMENU_X + MENU_FONT_X_OFFSET, 
					MONSTERTASKMENU_Y + MONSTERTASKMENU_FONT_Y_OFFSET + MONSTERTASKMENU_FONT_Y_ITERATOR * (i + 1), 
					false, Color.BLACK, Assets.font28);
		}
		
		//draw arrow
		g.drawImage(Assets.menu_arrow, MONSTERTASKMENU_X + MENU_ARROW_X_OFFSET,
				MONSTERTASKMENU_Y + MONSTERTASKMENU_FONT_Y_OFFSET + MENU_ARROW_Y_OFFSET + MONSTERTASKMENU_FONT_Y_ITERATOR * (selectedMonsterTask + 1), 
				Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);	
	}

	//GETTERS & SETTERS
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isArrange() {
		return arrange;
	}

	public void setArrange(boolean arrange) {
		this.arrange = arrange;
	}

	public int getSelectedMonsterTask() {
		return selectedMonsterTask;
	}

	public void setSelectedMonsterTask(int selectedMonsterTask) {
		this.selectedMonsterTask = selectedMonsterTask;
	}

}
