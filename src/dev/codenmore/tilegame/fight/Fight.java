package dev.codenmore.tilegame.fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.creatures.NPC;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.monsters.Monster;

public class Fight implements Settings{

	private Handler handler;
	private NPC npc;
	private ArrayList<Monster> player_mon, npc_mon;
	private enum states { ENTER, DECISION, ATTACK_TEXT, ANIMATION, DEFEAT, FINISHED};
	private enum options { ATTACK, SKILL, ITEM, RUN};
	private String state;
	private int arrow_x1 = 320, arrow_x2 = 465,
			arrow_y1 = 415, arrow_y2 = 450;
	//4 options in 2 rows (count from left to right and down line after upper line)
	private int selectedOption = 0;
	private boolean[] monsterHasAttacked;
	//text variable
	private boolean endText = true;
	//fight won or lost or run away
	private boolean defeat = false;
	private boolean won = false;
	private boolean run = false;
	private ItemMenu itemMenu;
	
	public Fight(Handler handler, NPC npc){
		this.handler = handler;
		this.npc = npc;
		player_mon = this.handler.getWorld().getEntityManager().getPlayer().getMonsters();
		npc_mon = this.npc.getMonsters();
		state = states.ENTER.name();
		monsterHasAttacked = new boolean[player_mon.size() + npc_mon.size()];
		itemMenu = new ItemMenu(handler, this.handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems());
		
	}
	
	public void tick(){		
		//switch tick method to state
		if(state.equals(states.ENTER.name())){
			tickText();
		}else if(state.equals(states.DECISION.name())){
			if(itemMenu.isActive()){
				itemMenu.tick();
			}else{
				tickDecisionArrow();
			}
		}else if(state.equals(states.ATTACK_TEXT.name())){
			if(tickAttackText()){
				tickAttacks();
			}
		}else if(state.equals(states.ANIMATION.name())){
			tickAnimations();
		}else if(state.equals(states.DEFEAT.name())){
			tickText();
		}
	}

	private void tickText() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			if(endText){
				if(state.equals(states.ENTER.name())){
					state = states.DECISION.name();
				}else if(state.equals(states.DEFEAT.name())){
					if(won){
						handler.getWorld().getEntityManager().getNPCs().get(npc.getId()).setHasMonsters(false);
					}else if(!won && !run){
						handler.getWorld().getEntityManager().getPlayer().die();
					}else if(run){
						
					}
					state = states.FINISHED.name();
				}
			}
		}
	}
	
	private void tickDecisionArrow() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			//hande standard attack
			if(selectedOption == 0){
				state = states.ATTACK_TEXT.name();
			//handle skill
			}else if(selectedOption == 1){
				
			//handle items
			}else if(selectedOption == 2){
				itemMenu.setActive(true);
			//handle run 
			}else if(selectedOption == 3){
				run();
			}
			return;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
			if(selectedOption == 0){
				selectedOption = 2;
			}else if(selectedOption == 1){
				selectedOption = 3;
			}else if(selectedOption == 2){
				selectedOption = 0;
			}else if(selectedOption == 3){
				selectedOption = 1;
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
			if(selectedOption == 0){
				selectedOption = 2;
			}else if(selectedOption == 1){
				selectedOption = 3;
			}else if(selectedOption == 2){
				selectedOption = 0;
			}else if(selectedOption == 3){
				selectedOption = 1;
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
			if(selectedOption == 0){
				selectedOption = 1;
			}else if(selectedOption == 1){
				selectedOption = 0;
			}else if(selectedOption == 2){
				selectedOption = 3;
			}else if(selectedOption == 3){
				selectedOption = 2;
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)){
			if(selectedOption == 0){
				selectedOption = 1;
			}else if(selectedOption == 1){
				selectedOption = 0;
			}else if(selectedOption == 2){
				selectedOption = 3;
			}else if(selectedOption == 3){
				selectedOption = 2;
			}
		}
	}

	private boolean tickAttackText() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			return true;
		}else 
			return false;
	}

	//es wird noch nicht gecheckt ob monster.get(0) lebendig ist oder nicht
	private void tickAttacks(){
		if(!run){
			for(int i = 0; i < monsterHasAttacked.length; i++){
				if(!monsterHasAttacked[i]){
					//attack from player monster
					if(i < player_mon.size()){
						for(Monster m: npc_mon){
							if(m.getHp() > 0){
								m.setHp(m.getHp() - player_mon.get(0).getAtt());
								monsterHasAttacked[i] = true;
								state = states.ANIMATION.name();
								return;
							}
						}
						//if no npc_mon has health left
						defeat = true;
						won = true;
						state = states.ANIMATION.name();
						return;
					//attack from npc monsters
					}else{
						for(Monster m: player_mon){
							if(m.getHp() > 0){
								m.setHp(m.getHp() - npc_mon.get(i - player_mon.size()).getAtt());
								monsterHasAttacked[i] = true;
								state = states.ANIMATION.name();
								return;
							}
						}
						//if no player_mon has health left
						defeat = true;
						won = false;
						state = states.ANIMATION.name();
						return;
					}
				}
			}
		}else{
			run = false;
			return;
		}
		
		setMonsterAttacked2Zero();
		
		state = states.ANIMATION.name();
	}

	private void tickAnimations() {
		if(defeat){
			state = states.DEFEAT.name();
			setMonsterAttacked2Zero();
			return;
		}
		
		for(boolean m: monsterHasAttacked){
			if(m == false){
				state = states.ATTACK_TEXT.name();
				return;
			}
		}
		
		setMonsterAttacked2Zero();
		state = states.DECISION.name();
	}
	
	private void setMonsterAttacked2Zero(){
		//set state to Decision if every monster has attacked
		for(int i = 0; i < monsterHasAttacked.length; i++){
			monsterHasAttacked[i] = false;
		}
	}
	
	private void run(){		
		Random rand = new Random();
		int dec = rand.nextInt(3);
		
		if(dec <= 1){
			run = true;
			state = states.ATTACK_TEXT.name();
		}else{
			run = true;
			defeat = true;
			state = states.DEFEAT.name();
		}
	}
	
	public void render(Graphics g){
		drawPlayerMonsterHealthBar(g);
		drawEnemies(g);
		if(state.equals(states.ENTER.name())){
			drawText(g, String.valueOf(npc.getName() + " möchte kämpfen"));
		}else if(state.equals(states.DECISION.name())){
			drawOptions(g);
			if(itemMenu.isActive()){
				itemMenu.render(g);
			}
		}else if(state.equals(states.ATTACK_TEXT.name())){
			drawAttackText(g);
		}else if(state.equals(states.ANIMATION.name())){
			drawAttackAnimation(g);
		}else if(state.equals(states.DEFEAT.name())){
			if(won)
				drawText(g, String.valueOf("Du hast de Kampf gegen " + npc.getName() + " gewonnen"));
			else if(!won && !run)
				drawText(g, String.valueOf(npc.getName() + " hat gegen dich gewonnen. Du wurdest ohnmächtig..."));
			else if(!won && run)
				drawText(g, String.valueOf("Du bist geflüchtet."));
		}
		
	}

	private void drawPlayerMonsterHealthBar(Graphics g) {
		//draw background
		g.drawImage(Assets.fightmenu_monsterHealthBar, 0, 0, WIDTH, (int) (HEIGHT/5.7), null);
		//draw Monster status
		for(int i = 0; i < player_mon.size(); i++){
			Text.drawString(g, player_mon.get(i).getName(), 30 + 200*i, 25, false, Color.BLACK, Assets.fontArialBold24);
			String hp = String.valueOf(player_mon.get(i).getHp() + " / " + player_mon.get(i).getHp_max());
			Text.drawString(g, hp, 30 + 200*i, 50, false, Color.BLACK, Assets.fontArial24);
			String mp = String.valueOf(player_mon.get(i).getMp() + " / " + player_mon.get(i).getMp_max());
			Text.drawString(g, mp, 30 + 200*i, 75, false, Color.BLACK, Assets.fontArial24);
		}
	}

	private void drawEnemies(Graphics g) {
		//monster images are drawn in double TILESIZE sizes
		int start_x = 50;
		if(npc_mon.size() == 1)
			start_x = WIDTH/2 - TILESIZE;
		else if(npc_mon.size() == 2)
			start_x = WIDTH/2 - TILESIZE - 100;
		
		for(int i = 0; i < npc_mon.size(); i++){
			g.drawImage(npc_mon.get(i).getImage(), start_x + 200 * i, 125, TILESIZE * 2, TILESIZE * 2, null);
		}
	}
	
	private void drawText(Graphics g, String message){
		g.drawImage(Assets.fightmenu_monsterHealthBar, 0, (int) (HEIGHT - HEIGHT/5.7), WIDTH, (int) (HEIGHT/5.7), null);
		Text.drawString(g, message, 25, (int) (HEIGHT - HEIGHT/5.7) + 35, false, Color.BLACK, Assets.fontArial24);
	}
	
	private void drawOptions(Graphics g){
		g.drawImage(Assets.fightmenu_monsterHealthBar, 0, (int) (HEIGHT - HEIGHT/5.7), WIDTH, (int) (HEIGHT/5.7), null);
		Text.drawString(g, "Was möchtest du tun?", 25, (int) (HEIGHT - HEIGHT/5.7) + 35, false, Color.BLACK, Assets.fontArial24);
		int count = 0;
		for(int i = 0; i < options.values().length / 2; i++){
			for(int j = 0; j < options.values().length / 2; j++){
				Text.drawString(g, options.values()[count].name(), 350 + j * 150, (int) (HEIGHT - HEIGHT/5.7) + 35 + i * 35 , false, Color.BLACK, Assets.fontArial24);
				count++;
			}
		}
		//draw arrow
		int x = arrow_x1, y = arrow_y1;
		if(selectedOption == 0){
			x = arrow_x1;
			y = arrow_y1;
		}else if(selectedOption == 1){
			x = arrow_x2;
			y = arrow_y1;
		}else if(selectedOption == 2){
			x = arrow_x1;
			y = arrow_y2;
		}else if(selectedOption == 3){
			x = arrow_x2;
			y = arrow_y2;
		}
		g.drawImage(Assets.menu_arrow, x, y, Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);	
		
	}
	
	private void drawAttackText(Graphics g){
		//draw background
		g.drawImage(Assets.fightmenu_monsterHealthBar, 0, (int) (HEIGHT - HEIGHT/5.7), WIDTH, (int) (HEIGHT/5.7), null);
		
		if(run){
			//drawText
			Text.drawString(g, String.valueOf("Du konntest nicht flüchten."), 25, (int) (HEIGHT - HEIGHT/5.7) + 35, false, Color.BLACK, Assets.fontArial24);
		}else{
			//drawText
			for(int i = 0; i < monsterHasAttacked.length; i++){
				if(monsterHasAttacked[i] == false){
					if(i-1 < 0 || monsterHasAttacked[i-1] == true){
						if(i < player_mon.size()){
							Text.drawString(g, String.valueOf("Dein Monster " + player_mon.get(i).getName() + " greift an!"), 25, (int) (HEIGHT - HEIGHT/5.7) + 35, false, Color.BLACK, Assets.fontArial24);
						}else{
							Text.drawString(g, String.valueOf("Das Monster " + npc_mon.get(i - player_mon.size()).getName() + " von " + npc.getName() + " greift an!"), 25, (int) (HEIGHT - HEIGHT/5.7) + 35, false, Color.BLACK, Assets.fontArial24);
						}
					}
				}
			}
		}
	}
	
	private void drawAttackAnimation(Graphics g){
		
	}

	
	//GETTERS & SETTERS
	public ArrayList<Monster> getPlayer_mon() {
		return player_mon;
	}

	public void setPlayer_mon(ArrayList<Monster> player_mon) {
		this.player_mon = player_mon;
	}

	public ArrayList<Monster> getNpc_mon() {
		return npc_mon;
	}

	public void setNpc_mon(ArrayList<Monster> npc_mon) {
		this.npc_mon = npc_mon;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemMenu getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(ItemMenu itemMenu) {
		this.itemMenu = itemMenu;
	}
}
