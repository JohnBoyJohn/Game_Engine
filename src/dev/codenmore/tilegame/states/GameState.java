package dev.codenmore.tilegame.states;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.Settings;
import dev.codenmore.tilegame.inventory.Inventory;
import dev.codenmore.tilegame.ui.ingameui.IngameUI;
import dev.codenmore.tilegame.worlds.World;

public class GameState extends State implements Settings{
	
	private World world;
	private int counter;
	private IngameUI ingameUI;
	
	public GameState(Handler handler){
		super(handler);
		counter = 0;
		world = new World(handler, "res/worlds/world0.txt", counter);
		ingameUI = new IngameUI(handler);
		handler.setWorld(world);
	}
	
	@Override
	public void tick() {
		world.tick();
		ingameUI.tick();
		if(world.getEntityManager().swapWorld()){
			counter++;
			if(counter <= MAX_LEVELS){
				Inventory inv = world.getEntityManager().getPlayer().getInventory();
				String path = String.valueOf("res/worlds/world" + counter + ".txt");
				world = new World(handler, path, counter);
				world.getEntityManager().getPlayer().setInventory(inv);
				handler.setWorld(world);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		ingameUI.render(g);
	}

}
