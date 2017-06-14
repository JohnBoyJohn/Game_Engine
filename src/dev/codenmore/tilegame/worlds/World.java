package dev.codenmore.tilegame.worlds;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.entities.EntityManager;
import dev.codenmore.tilegame.entities.creatures.NPC;
import dev.codenmore.tilegame.entities.creatures.Player;
import dev.codenmore.tilegame.entities.creatures.merchant.Merchant;
import dev.codenmore.tilegame.entities.specials.WarpTile;
import dev.codenmore.tilegame.entities.statics.Stone;
import dev.codenmore.tilegame.entities.statics.Tree;
import dev.codenmore.tilegame.fight.FightManager;
import dev.codenmore.tilegame.gfx.EnterFightAnimation;
import dev.codenmore.tilegame.items.ItemManager;
import dev.codenmore.tilegame.monsters.MonsterManager;
import dev.codenmore.tilegame.tiles.Tile;
import dev.codenmore.tilegame.utils.Utils;

public class World implements Settings{

	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	//Entities
	private EntityManager entityManager;
	//Items
	private ItemManager itemManager;
	//defaut monsters for npcs
	private int npc_counter = 0;
	//fighs
	private FightManager fightManager;
	private EnterFightAnimation enterFightAnimation;
	
	//MonsterManager
	private MonsterManager monsterManager;
	
	public World(Handler handler, String path, int lvl){
		this.handler = handler;
		monsterManager = new MonsterManager(handler);
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);
		fightManager = new FightManager(handler);
		enterFightAnimation = new EnterFightAnimation(handler);
		
		loadWorld(path);
		loadEntities(path, lvl);
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	
	public void tick(){
		if(fightManager.isGot2Fight()){
			fightManager.tick();
		}else if(enterFightAnimation.getAnimation()){
			enterFightAnimation.tick();
			if(enterFightAnimation.getAnimationEnd()){
				fightManager.setGot2Fight(true);
				enterFightAnimation.setAnimation(false);
			}
		}else{
			itemManager.tick();
			entityManager.tick();
		}
	}
	
	public void render(Graphics g){
		if(fightManager.isGot2Fight()){
			fightManager.render(g);
		}else{
			int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILESIZE);
			int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILESIZE + 1);
			int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILESIZE);
			int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILESIZE + 1);
			
			for(int y = yStart;y < yEnd;y++){
				for(int x = xStart;x < xEnd;x++){
					getTile(x, y).render(g, (int) (x * Tile.TILESIZE - handler.getGameCamera().getxOffset()),
							(int) (y * Tile.TILESIZE - handler.getGameCamera().getyOffset()));
				}
			}
			
			//Items
			itemManager.render(g);
			
			//Entities
			entityManager.render(g);

			if(enterFightAnimation.getAnimation()){
				enterFightAnimation.render(g);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.dirtTile;
		return t;
	}
	
	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0;y < height;y++){
			for(int x = 0;x < width;x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	private void loadEntities(String path, int num){
		path = path.substring(0, path.length() - 10);
		path += String.valueOf("entities" + num + ".txt");
				
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		for(int y = 0;y < height;y++){
			for(int x = 0;x < width;x++){
				int number = Utils.parseInt(tokens[(x + y * width) + 4]);
				if(number > 0){
					if(number == 1){
						entityManager.addEntity(new Tree(handler, x * TILESIZE, y * TILESIZE));
					}else if(number == 2){
						entityManager.addEntity(new Stone(handler, x * TILESIZE, y * TILESIZE));
					}else if(number == 10){
						entityManager.addNPC(new NPC(handler, npc_counter, x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE, "Hallo Fremder", monsterManager.initRandomMonsters(), String.valueOf("NPC #" + npc_counter)));
						npc_counter++;
					}else if(number == 22){
						entityManager.addMerchant(new Merchant(handler, npc_counter, x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE, "Hallo Fremder", monsterManager.initRandomMonsters(), String.valueOf("Merchant #" + npc_counter)));
						npc_counter++;
					}else if(number == 60){
						//entityManager.addEntity(new Boss(handler, x * TILESIZE, y * TILESIZE, TILESIZE * 2, TILESIZE * 2, 350, 250));
					}else if(number == 99){
						entityManager.setWt(new WarpTile(handler , (float) x * TILESIZE, (float) y * TILESIZE, TILESIZE, TILESIZE));
					}
				}
			}
		}
	}
	
	//GETTERS & SETTERS
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public FightManager getFightManager() {
		return fightManager;
	}

	public void setFightManager(FightManager fightManager) {
		this.fightManager = fightManager;
	}

	public EnterFightAnimation getFightAnimation() {
		return enterFightAnimation;
	}

	public void setFightAnimation(EnterFightAnimation fightAnimation) {
		this.enterFightAnimation = fightAnimation;
	}
}