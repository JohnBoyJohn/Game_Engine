package dev.codenmore.tilegame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.monsters.Monster;

public class Item {
	//Handler
	public static Item[] items = new Item[256];
	public static Item logItem = new Item(Assets.item_log, "Wood", 0);
	public static Item rockItem = new Item(Assets.item_stone, "Rock", 1);
	public static Item herbItem = new Herb(Assets.item_herb, "Herb", 2);
	public static Item magicTear = new MagicTear(Assets.item_magic_tear, "Magic Tear", 3);
	public static Item attBerry = new AttBerry(Assets.item_att_berry, "Att Berry", 4);
	public static Item defBerry = new DefBerry(Assets.item_def_berry, "Def Berry", 5);
	public static Item hpBerry = new HPBerry(Assets.item_hp_berry, "HP Berry", 6);
	public static Item mpBerry = new MPBerry(Assets.item_mp_berry, "MP Berry", 7);
	
	//CLASS
	public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count;
	protected boolean pickedUp = false;
	
	public Item(BufferedImage texture, String name, int id){
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		items[id] = this;
	}
	
	public Item(BufferedImage texture, String name, int id, int count){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.count = count;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		items[id] = this;
	}
	
	public void tick(){
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)){
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}
	
	//to render in game 
	public void render(Graphics g){
		if(handler == null)
			return;
		
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	//to render in item menu
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, null);
	}
	
	public Item createNew(int count){
		Item i = new Item(texture, name, id);
		i.setCount(count);
		return i;
	}
	
	public Item createNew(int x, int y){
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}
	
	public void use(Monster m){
	}
	
	public String getItemEffectMessage(Monster m){
		return "";
	}

	//GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
}
