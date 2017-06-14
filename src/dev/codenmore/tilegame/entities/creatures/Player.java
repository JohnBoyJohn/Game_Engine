package dev.codenmore.tilegame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.entities.creatures.merchant.Merchant;
import dev.codenmore.tilegame.fight.Fight;
import dev.codenmore.tilegame.gfx.Animation;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.inventory.Inventory;
import dev.codenmore.tilegame.monsters.Monster;
import dev.codenmore.tilegame.ui.ingameui.Menu;

public class Player extends Creature {
	
	//Animations
	private Animation animDown, animUp, animLeft, animRight;
	//Inventory
	private Inventory inventory;
	//Monsters
	private ArrayList<Monster> monsters;
	
	//talk
	private int lastDir; //1 = up, 2 = down, 3 = left, 4 = right
	private boolean talk, tryToTalk, isMoving;
	private String message;
	private NPC lastNPC;
	
	//menue
	private Menu menu;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;
		
		//Animations
		animDown = new Animation(250, Assets.player_down);
		animUp = new Animation(250, Assets.player_up);
		animLeft = new Animation(250, Assets.player_left);
		animRight = new Animation(250, Assets.player_right);
	
		inventory = new Inventory(handler);
		
		attack = 5;
		lastDir = 2;
		talk = tryToTalk = false;
		message = "";
		lastNPC = null;
		
		menu = new Menu(handler);
		
		//init start monster
		monsters = new ArrayList<Monster>();
		monsters.add(new Monster(handler, 0, 0, Assets.slime, new Animation(250, Assets.slime_attack), new Animation(250, Assets.slime_die)));
		monsters.add(new Monster(handler, 1, 0, Assets.dragon, new Animation(250, Assets.dragon_attack), new Animation(250, Assets.dragon_die)));
		monsters.add(new Monster(handler, 2, 0, Assets.golem, new Animation(250, Assets.golem_attack), new Animation(250, Assets.golem_die)));
	}

	@Override
	public void tick() {
		//Animation
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
		
		//Movement
		getInput();
		if(tryToTalk){
			tryToTalk();
		}
		
		if(!talk && !menu.isActive() && !menu.isSubmenuActive()){
			move();
		}
		
		handler.getGameCamera().centerOnEntity(this);
		
		//Inventory
		inventory.tick();
		//Menu
		menu.tick();
	}
	
	private void tryToTalk() {
		//check if inventory or menu is open
		if(inventory.isActive())
			return;	
		if(menu.isActive() || menu.isSubmenuActive())
			return;
		
		//check if player already talks
		if(talk){
			talk = false;
			tryToTalk = false;
			//check if npc has monsters
			if(lastNPC.isHasMonsters()){
				handler.getWorld().getFightManager().addFight(new Fight(handler, lastNPC));
				//animation set to true
				handler.getWorld().getFightAnimation().initAnimation();
			//check if npc is merchant
			}else{
				ArrayList<Merchant> merchs = handler.getWorld().getEntityManager().getMerchants();
				for(Merchant m: merchs){
					if(lastNPC.equals(m)){
						m.setActiveTardeQuestion(false);
					}
				}
			}
			
			return;
		}
				
		tryToTalk = false;
		
		//handle talk range
		Rectangle cb = getCollisionBounds(0, 0);
		Rectangle ar = new Rectangle();
		int arSize = bounds.width;
		ar.width = ar.height = arSize;
		
		if(lastDir == 1){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}else if(lastDir == 2){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}else if(lastDir == 3){
			ar.x = cb.x - arSize / 2;
			ar.y = cb.y + cb.height / 2 - arSize/2;
		}else if(lastDir == 4){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize/2;
		}else{
			return;
		}
		
		for(NPC npc: handler.getWorld().getEntityManager().getNPCs()){
			if(npc.getCollisionBounds(0, 0).intersects(ar)){
				//Check if npc is no merchant
				ArrayList<Merchant> merchs = handler.getWorld().getEntityManager().getMerchants();
				for(Merchant m: merchs){
					if(npc.equals(m)){
						m.setActiveTardeQuestion(true);
					}
				}
				message = npc.getMessage();
				lastNPC = npc;
				System.out.println("Last NPC was NPC #" + npc.getId());
				talk = true;
			}
		}	
	}

	@Override
	public void die() {
		System.out.println("You Loose!");
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		isMoving = false;
		
		if(inventory.isActive() || menu.isActive() || menu.isSubmenuActive())
			return;
		
		if(!talk){
			if(handler.getKeyManager().up){
				yMove = -speed;
				isMoving = true;
				lastDir = 1;
			}
			if(handler.getKeyManager().down){
				yMove = speed;
				isMoving = true;
				lastDir = 2;
			}
			if(handler.getKeyManager().left){
				xMove = -speed;
				isMoving = true;
				lastDir = 3;
			}
			if(handler.getKeyManager().right){
				xMove = speed;
				isMoving = true;
				lastDir = 4;
			}
		}
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
			tryToTalk = true;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		//Draw Collision Rectangle 
		/*
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
				bounds.width, bounds.height);
		*/
	}
	
	public void postRender(Graphics g){
		inventory.render(g);
		menu.render(g);
	}
	
	private BufferedImage getCurrentAnimationFrame(){
		if(!talk && !inventory.isActive() && !menu.isActive() && !menu.isSubmenuActive() && isMoving){
			if(lastDir == 1){
				return animUp.getCurrentFrame();
			}else if(lastDir == 2){
				return animDown.getCurrentFrame();
			}else if(lastDir == 3){
				return animLeft.getCurrentFrame();
			}else if(lastDir == 4){
				return animRight.getCurrentFrame();
			}else{
				return animDown.getCurrentFrame();
			}
		}else{
			if(lastDir == 1){
				return Assets.player_up[0];
			}else if(lastDir == 2){
				return Assets.player_down[0];
			}else if(lastDir == 3){
				return Assets.player_left[0];
			}else if(lastDir == 4){
				return Assets.player_right[0];
			}else{
				return Assets.player_down[0];
			}
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public boolean isTalking() {
		return talk;
	}
	
	public String getMessage(){
		return message;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}
}
