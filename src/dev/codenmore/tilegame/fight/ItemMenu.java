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

public class ItemMenu implements Settings{
	
	public Handler handler;
	private UseItemMenu useItemMenu;
	private int x = 300, y = 200,
			width = 220, height = 225;
	private int item_x = 340, 
			item_y = 235,
			item_iterator = 40;
	private int arrow_x = 310,
			arrow_y = 220;
	private boolean active = false;
	private ArrayList<Item> items = new ArrayList<Item>();
	private int page = 0;
	private int max_page = 0;
	private int selectedItem = 0;
	private int MAX_ITEMS_PER_PAGE = 5;

	public ItemMenu(Handler handler, ArrayList<Item> items){
		this.handler = handler;
		this.items = items;
		useItemMenu = new UseItemMenu(handler);
	}
	
	public void tick(){
		if(active && !useItemMenu.isActive()){
			if(items.size() % MAX_ITEMS_PER_PAGE != 0){
				max_page = (items.size() / MAX_ITEMS_PER_PAGE) + 1;
			}else{
				max_page = (items.size() / MAX_ITEMS_PER_PAGE) + 1;
			}
			
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
				active = false;
				return;
			}
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
				if(!items.isEmpty())
					useItemMenu.setToActive(items.get(selectedItem));
				return;
			}

			//handle input
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
				selectedItem--;
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
				selectedItem++;
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A))
				page--;
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D))
				page++;
			
			//handle arrow
			if(selectedItem < 0)
				if(MAX_ITEMS_PER_PAGE > items.size() - (page * MAX_ITEMS_PER_PAGE) )
					selectedItem = items.size() % MAX_ITEMS_PER_PAGE - 1;
				else
					selectedItem = MAX_ITEMS_PER_PAGE - 1;
			else if(selectedItem >= MAX_ITEMS_PER_PAGE || selectedItem >= items.size() - (page * MAX_ITEMS_PER_PAGE))
				selectedItem = 0;
			
			//handle pagination
			if(page < 0){
				page = max_page - 1;
			}else if(page >= max_page){
				page = 0;
			}
		}
		useItemMenu.tick();
	}
	
	public void render(Graphics g){
		if(active){
			//draw background
			g.drawImage(Assets.monsterTaskMenu_background, x, y, width, height, null);
			
			if(!items.isEmpty()){
				//draw items
				int count = 0;
				for(int i = 0; i < items.size(); i++){
					if((page * MAX_ITEMS_PER_PAGE) <= i && i <= (page * MAX_ITEMS_PER_PAGE) + MAX_ITEMS_PER_PAGE - 1){
						Text.drawString(g, items.get(i).getName(), item_x, item_y + item_iterator * (count % MAX_ITEMS_PER_PAGE), false, Color.BLACK, Assets.fontArialBold24);
						Text.drawString(g, String.valueOf(items.get(i).getCount() + "x"), item_x + 125, item_y + item_iterator * (count % MAX_ITEMS_PER_PAGE), false, Color.BLACK, Assets.fontArialBold24);
					}
					count++;
				}
				
				//draw arrow
				g.drawImage(Assets.menu_arrow, arrow_x, arrow_y + item_iterator * selectedItem, 
						Assets.menu_arrow.getWidth(), Assets.menu_arrow.getHeight(), null);
				
				//draw page number
				Text.drawString(g, String.valueOf(page), item_x + 150, y + height - 8, false, Color.WHITE, Assets.fontArialBold24);
			}else{
				Text.drawString(g, "No items left", item_x - 10, item_y + 80, false, Color.BLACK, Assets.fontArialBold24);
			}
		}
		useItemMenu.render(g);
	}

	//GETTERS & SETTERS
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UseItemMenu getUseItemMenu() {
		return useItemMenu;
	}

	public void setUseItemMenu(UseItemMenu useItemMenu) {
		this.useItemMenu = useItemMenu;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems() {
		this.items = handler.getWorld().getEntityManager().getPlayer().getInventory().getInventoryItems();
	}

	public int getSelectedItem() {
		return selectedItem + page * MAX_ITEMS_PER_PAGE;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
