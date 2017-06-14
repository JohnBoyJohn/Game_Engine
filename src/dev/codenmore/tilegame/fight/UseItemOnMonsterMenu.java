package dev.codenmore.tilegame.fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.items.Item;
import dev.codenmore.tilegame.monsters.Monster;

public class UseItemOnMonsterMenu implements Settings{

	private Handler handler;
	private boolean active = false;
	private boolean own_monster = true;
	private boolean show_item_text = false;
	private ArrayList<Monster> monsters;
	private int selectedMonster = 0;
	private int x = 350, y = 175,
			width = 220, height = 225;
	private int monster_iterator = 50;
	
	public UseItemOnMonsterMenu(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		if(active){
			if(!show_item_text){
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
					active = false;
					return;
				}
				
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
					selectedMonster--;
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
					selectedMonster++;
				
				if(selectedMonster < 0){
					selectedMonster = monsters.size() - 1;
				}else if(selectedMonster >= monsters.size()){
					selectedMonster = 0;
				}
				
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
					show_item_text = true;
					return;
				}
			}else if(show_item_text){
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
					//set to next fight state
					Fight activeFight = handler.getWorld().getFightManager().getActiveFight();
					activeFight.setState("ATTACK_TEXT");

					//use item on monster & remove from inventory list
					Item item = activeFight.getItemMenu().getItems().get(activeFight.getItemMenu().getSelectedItem());
					Monster monster = monsters.get(selectedMonster);
					
					item.use(monster);
					handler.getWorld().getEntityManager().getPlayer().getInventory().removeItem(item);
					handler.getWorld().getFightManager().getActiveFight().getItemMenu().setItems();				
					
					//set menus to inactive
					activeFight.getItemMenu().getUseItemMenu().setToInactive();
					activeFight.getItemMenu().setActive(false);
					
					//set selected_indexes to zero
					activeFight.getItemMenu().getUseItemMenu().setSelectedOperation(0);
					activeFight.getItemMenu().setSelectedItem(0);
					selectedMonster = 0;
					
					//set inventory in fight
					activeFight.getItemMenu().setItems();
					
					active = false;
					show_item_text = false;
				}
			}
		}
	}
	
	public void render(Graphics g){
		if(active){
			//draw background
			g.drawImage(Assets.monsterTaskMenu_background, x, y, width, height, null);
			
			for(int i = 0; i < monsters.size(); i++){
				Text.drawString(g, monsters.get(i).getName(), x + 50, y + monster_iterator * i + 25, false, Color.BLACK, Assets.fontArialBold24);
			}
			
			//draw arrow
			g.drawImage(Assets.menu_arrow, x + 10, y + monster_iterator * selectedMonster + 10, 
					Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);
			

			if(show_item_text){
				//draw background
				g.drawImage(Assets.fightmenu_monsterHealthBar, 0, (int) (HEIGHT - HEIGHT/5.7), WIDTH, (int) (HEIGHT/5.7), null);
				
				//drawText
				Fight activeFight = handler.getWorld().getFightManager().getActiveFight();
				Item item = activeFight.getItemMenu().getItems().get(activeFight.getItemMenu().getSelectedItem());
				Monster monster = monsters.get(selectedMonster);
				
				String item_mess = String.valueOf(item.getName() + " was used on monster " + monster.getName());
				Text.drawString(g, item_mess, 50, (int) (HEIGHT - HEIGHT/5.7) + 50, false, Color.BLACK, Assets.fontArial24);
			}
		}
	}
	
	private void setMonsters(){
		if(own_monster){
			monsters = handler.getWorld().getFightManager().getActiveFight().getPlayer_mon();
		}else{
			monsters = handler.getWorld().getFightManager().getActiveFight().getNpc_mon();
		}
	}
	
	//GETTERS & SETTERS
	public void set2Active(boolean own_monster){
		active = true;
		this.own_monster = own_monster;
		setMonsters();
	}
	
	public boolean isActive(){
		return active;
	}
}