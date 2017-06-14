package dev.codenmore.tilegame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.creatures.NPC;
import dev.codenmore.tilegame.entities.creatures.Player;
import dev.codenmore.tilegame.entities.creatures.merchant.Merchant;
import dev.codenmore.tilegame.entities.specials.WarpTile;

public class EntityManager implements Settings{
	
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<NPC> npcs;
	private ArrayList<Merchant> merchants;
	private WarpTile wt;
	private boolean swapWorld;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		//Sortiert Entities damit Player an exakter Stelle gerendert werden kann
		@Override
		public int compare(Entity a, Entity b){
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;	
		}
	};

	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		npcs = new ArrayList<NPC>();
		merchants = new ArrayList<Merchant>();
		addEntity(player);
		
		//add warp tile
		wt = new WarpTile(handler , WIDTH / 2 - TILESIZE / 2, HEIGHT / 2 - TILESIZE / 2, TILESIZE, TILESIZE);
		
		swapWorld = false;
	}
	
	public void tick(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			e.tick();
			if(!e.isActive()){
				it.remove();
			}
		}
		entities.sort(renderSorter);
		
		
		
		//warp to level -> buggy: links only forwards
		if(getWt().isWarp()){
			System.out.println("Animation to change the world");
			swapWorld = true;
		}
	}
	
	public void render(Graphics g){
		for(Entity e: entities){
			e.render(g);
		}
		player.postRender(g);
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}

	public void addNPC(NPC npc){
		entities.add(npc);
		npcs.add(npc);
	}
	
	public void addMerchant(Merchant merch){
		entities.add(merch);
		npcs.add(merch);
		merchants.add(merch);
	}
	
	//GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	
	public boolean swapWorld(){
		return swapWorld;
	}

	public WarpTile getWt() {
		return wt;
	}

	public void setWt(WarpTile wt) {
		this.wt = wt;
		addEntity(this.wt);
	}

	public ArrayList<NPC> getNPCs() {
		return npcs;
	}

	public void setNPCs(ArrayList<NPC> npcs) {
		this.npcs = npcs;
	}

	public ArrayList<Merchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(ArrayList<Merchant> merchants) {
		this.merchants = merchants;
	}
}
