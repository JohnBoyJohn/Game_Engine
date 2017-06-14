package dev.codenmore.tilegame.ui.ingameui.monstermenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.monsters.Monster;
import dev.codenmore.tilegame.monsters.Monster_Settings;

public class MonsterMenu implements Settings, Monster_Settings{
	
	private Handler handler;
	private MonsterTaskMenu monsterTaskMenu;
	private MonsterTaskHandler monsterTaskHandler;
	private int MONSTERMENU_X = 20, 
			MONSTERMENU_Y = 20, 
			MONSTERMENU_WIDTH = WIDTH - MONSTERMENU_X * 2, 
			MONSTERMENU_HEIGHT = HEIGHT - MONSTERMENU_Y * 2,
			MONSTERMENU_Y_FONT_OFFSET = 30;
	private boolean active = false;
	private int selectedMonster = 0;
	private int arrangeMonster = 0;
	private Monster activeMonster;

	public MonsterMenu(Handler handler){
		this.handler = handler;
		monsterTaskMenu = new MonsterTaskMenu(handler);
		monsterTaskHandler = new MonsterTaskHandler(handler);
	}
	
	public void tick(){
		if(monsterTaskMenu.isActive()){
			monsterTaskMenu.tick();
			return;
		}
		
		if(!active)
			return;
		//if you have to choose 2nd monster for arrange task
		else if(monsterTaskMenu.isArrange()){
			selectArrangeMonster();
		}else{
			selectMonster();
		}
	}
	
	private void selectMonster(){
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			setActive(false);
			handler.getWorld().getEntityManager().getPlayer().getMenu().setSubmenuActive(false);		
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
			selectedMonster--;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
			selectedMonster++;
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER))
			openMonsterTaskMenu();
		
		if(selectedMonster < 0)
			selectedMonster = handler.getWorld().getEntityManager().getPlayer().getMonsters().size() - 1;
		else if(selectedMonster >= handler.getWorld().getEntityManager().getPlayer().getMonsters().size())
			selectedMonster = 0;
		
		activeMonster = handler.getWorld().getEntityManager().getPlayer().getMonsters().get(selectedMonster);
	}
	
	private void selectArrangeMonster(){
		//escape arrange selection
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			monsterTaskMenu.setArrange(false);
			setActive(true);
			arrangeMonster = 0;
			return;
		}
		
		//use task function
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			monsterTaskHandler.arrangeMonsters(selectedMonster, arrangeMonster);
			monsterTaskMenu.setArrange(false);
			setActive(true);
			arrangeMonster = 0;
			return;
		}
		
		if(arrangeMonster == selectedMonster)
			arrangeMonster++;
		
		//get arrow input
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
			arrangeMonster--;
			if(arrangeMonster == selectedMonster)
				arrangeMonster--;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
			arrangeMonster++;
			if(arrangeMonster == selectedMonster)
				arrangeMonster++;
		}	
			
		//controll position
		if(arrangeMonster < 0){
			arrangeMonster = handler.getWorld().getEntityManager().getPlayer().getMonsters().size() - 1;
			if(arrangeMonster == selectedMonster)
				arrangeMonster--;
		}
		else if(arrangeMonster >= handler.getWorld().getEntityManager().getPlayer().getMonsters().size()){
			arrangeMonster = 0;
			if(arrangeMonster == selectedMonster)
				arrangeMonster++;
		}
	}

	public void render(Graphics g){
		if(!active)
			return;
		
		//draw background
		g.drawImage(Assets.monsterMenu_background, MONSTERMENU_X, MONSTERMENU_Y, MONSTERMENU_WIDTH, MONSTERMENU_HEIGHT, null);
		
		//draw monster headline
		Text.drawString(g, "MONSTERS", MONSTERMENU_X + 23, 
				MONSTERMENU_Y + 35, 
				false, Color.WHITE, Assets.font28);
		
		//draw monster names
		for(int i = 1; i <= handler.getWorld().getEntityManager().getPlayer().getMonsters().size(); i++){
			Text.drawString(g, handler.getWorld().getEntityManager().getPlayer().getMonsters().get(i-1).getName(), MONSTERMENU_X + MENU_FONT_X_OFFSET, 
					MONSTERMENU_Y + MONSTERMENU_Y_FONT_OFFSET + MENU_FONT_Y_ITERATOR * i, 
					false, Color.BLACK, Assets.font28);
		}
		
		//draw arrow
		g.drawImage(Assets.menu_arrow, MONSTERMENU_X + MENU_ARROW_X_OFFSET,
				MONSTERMENU_Y + MENU_ARROW_Y_OFFSET + MONSTERMENU_Y_FONT_OFFSET + MENU_FONT_Y_ITERATOR * (selectedMonster + 1), 
				Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);	
		
		//draw arrange task arrow
		if(monsterTaskMenu.isArrange()){
			g.drawImage(Assets.menu_arrow_2, MONSTERMENU_X + MENU_ARROW_X_OFFSET,
					MONSTERMENU_Y + MENU_ARROW_Y_OFFSET + MONSTERMENU_Y_FONT_OFFSET + MENU_FONT_Y_ITERATOR * (arrangeMonster + 1), 
					Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);	
		}
		
		
		//draw monster variables
		if(activeMonster != null){
			//draw image
			g.drawImage(activeMonster.getImage(), MONSTERMENU_X + 220, MONSTERMENU_Y + 22, activeMonster.getImage().getWidth() * 2, activeMonster.getImage().getHeight() * 2, null);	
			
			//draw name
			Text.drawString(g, "Name:", MONSTERMENU_X + 360, MONSTERMENU_Y + 47, false, Color.BLACK, Assets.font28);
			Text.drawString(g, activeMonster.getName(),	MONSTERMENU_X + 360, MONSTERMENU_Y + 77, false, Color.BLACK, Assets.font28);
			
			//draw race
			Text.drawString(g, "Race:", MONSTERMENU_X + 360, MONSTERMENU_Y + 117, false, Color.BLACK, Assets.font28);
			Text.drawString(g, MONSTERCLASSNAMES[activeMonster.getRace()], MONSTERMENU_X + 360, MONSTERMENU_Y + 147, 
					false, Color.BLACK, Assets.font28);
			
			//draw hp
			Text.drawString(g, "Health:", MONSTERMENU_X + 220, MONSTERMENU_Y + 200, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, activeMonster.getHp() + " / " + activeMonster.getHp_max(), MONSTERMENU_X + 360, MONSTERMENU_Y + 200, 
					false, Color.BLACK, Assets.font28);
			
			//draw mp
			Text.drawString(g, "MP:", MONSTERMENU_X + 220, MONSTERMENU_Y + 240, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, activeMonster.getMp() + " / " + activeMonster.getMp_max(), MONSTERMENU_X + 360, MONSTERMENU_Y + 240, 
					false, Color.BLACK, Assets.font28);
			
			//draw att
			Text.drawString(g, "Att:", MONSTERMENU_X + 220, MONSTERMENU_Y + 280, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, String.valueOf(activeMonster.getAtt()), MONSTERMENU_X + 360, MONSTERMENU_Y + 280, 
					false, Color.BLACK, Assets.font28);
			
			//draw def
			Text.drawString(g, "Def:", MONSTERMENU_X + 220, MONSTERMENU_Y + 320, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, String.valueOf(activeMonster.getDef()), MONSTERMENU_X + 360, MONSTERMENU_Y + 320, 
					false, Color.BLACK, Assets.font28);
			
			//draw luk
			Text.drawString(g, "Luk:", MONSTERMENU_X + 220, MONSTERMENU_Y + 360, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, String.valueOf(activeMonster.getLuk()), MONSTERMENU_X + 360, MONSTERMENU_Y + 360, 
					false, Color.BLACK, Assets.font28);
			
			//draw agi
			Text.drawString(g, "Agi:", MONSTERMENU_X + 220, MONSTERMENU_Y + 400, false, Color.BLACK, Assets.font28);			
			Text.drawString(g, String.valueOf(activeMonster.getAgi()), MONSTERMENU_X + 360, MONSTERMENU_Y + 400, 
					false, Color.BLACK, Assets.font28);
		}
		monsterTaskMenu.render(g);
	}
	
	private void openMonsterTaskMenu() {
		monsterTaskMenu.setActive(true);
	}

	//GETTERS & SETTERS
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getSelectedMonster() {
		return selectedMonster;
	}

	public void setSelectedMonster(int selectedMonster) {
		this.selectedMonster = selectedMonster;
	}

	public int getArrangeMonster() {
		return arrangeMonster;
	}

	public void setArrangeMonster(int arrangeMonster) {
		this.arrangeMonster = arrangeMonster;
	}

	public Monster getActiveMonster() {
		return activeMonster;
	}

	public void setActiveMonster(Monster activeMonster) {
		this.activeMonster = activeMonster;
	}
}
