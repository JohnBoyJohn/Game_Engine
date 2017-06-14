package dev.codenmore.tilegame.fight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.gfx.Text;
import dev.codenmore.tilegame.items.Item;

public class UseItemMenu implements Settings{

	private Handler handler;
	private Item item;
	private boolean active = false;
	private boolean throw_message = false;
	private String[] operations = {"Use", "Give", "Throw Away", "Back"};
	private int operation_x = 200, operation_y = 150, operation_iterator = 50;
	private int arrow_x = operation_x - 30, arrow_y = operation_y - 15;
	private int selectedOperation = 0;
	private UseItemOnMonsterMenu itemOnMonsterMenu;
	
	public UseItemMenu(Handler handler){
		this.handler = handler;
		itemOnMonsterMenu = new UseItemOnMonsterMenu(handler);
	}
	
	public void tick(){
		if(active && !itemOnMonsterMenu.isActive()){
			if(!throw_message){
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
					active = false;
					return;
				}
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
					if(selectedOperation == 0){
						itemOnMonsterMenu.set2Active(true);
						return;
					}else if(selectedOperation == 1){
						itemOnMonsterMenu.set2Active(false);
						return;
					}else if(selectedOperation == 2){
						throw_message = true;
						return;
					}else if(selectedOperation == 3){
						setToInactive();
						return;
					}
				}
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
					selectedOperation--;
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
					selectedOperation++;
				
				if(selectedOperation < 0)
					selectedOperation = operations.length - 1;
				else if(selectedOperation >= operations.length)
					selectedOperation = 0;
			}else{
				if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
					handler.getWorld().getEntityManager().getPlayer().getInventory().removeItem(item);
					handler.getWorld().getFightManager().getActiveFight().setState("ATTACK_TEXT");
					setToInactive();
					return;
				}
			}
			
		}
		itemOnMonsterMenu.tick();
	}
	
	public void render(Graphics g){
		if(active){
			//drawBackground
			g.drawImage(Assets.monsterTaskMenu_background, operation_x - 50, operation_y - 37, null);
			
			//draw items
			int count = 0;
			for(String o: operations){
				Text.drawString(g, o, operation_x, operation_y + operation_iterator * count, false, Color.BLACK, Assets.fontArialBold24);
				count++;
			}
			
			//draw arrow
			g.drawImage(Assets.menu_arrow, arrow_x, arrow_y + operation_iterator * selectedOperation, 
					Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);
		}
		if(throw_message){
			//draw background
			g.drawImage(Assets.fightmenu_monsterHealthBar, 0, (int) (HEIGHT - HEIGHT/5.7), WIDTH, (int) (HEIGHT/5.7), null);
			
			//draw throw away text
			String mess = String.valueOf("Item " + item.getName() + " wurde wegeschmissen.");
			Text.drawString(g, mess, 50, (int) (HEIGHT - HEIGHT/5.7) + 50, false, Color.BLACK, Assets.fontArial24);
			
		}
		itemOnMonsterMenu.render(g);
	}

	//GETTERS & SETTERS
	public boolean isActive() {
		return active;
	}

	public void setToActive(Item i) {
		this.active = true;
		this.item = i;
	}
	
	public void setToInactive(){
		selectedOperation = 0;
		throw_message = false;
		active = false;
	}

	public UseItemOnMonsterMenu getItemOnMonsterMenu() {
		return itemOnMonsterMenu;
	}

	public void setItemOnMonsterMenu(UseItemOnMonsterMenu itemOnMonsterMenu) {
		this.itemOnMonsterMenu = itemOnMonsterMenu;
	}

	public int getSelectedOperation() {
		return selectedOperation;
	}

	public void setSelectedOperation(int selectedOperation) {
		this.selectedOperation = selectedOperation;
	}
}
