package dev.codenmore.tilegame.entities.statics;

import java.awt.Graphics;

import dev.codenmore.tilegame.Handler;
import dev.codenmore.tilegame.gfx.Assets;
import dev.codenmore.tilegame.items.Item;
import dev.codenmore.tilegame.tiles.Tile;

public class Stone extends StaticEntity{

	public Stone(Handler handler, float x, float y){
		super(handler, x, y, Tile.TILESIZE, Tile.TILESIZE);
	
		bounds.x = 1;
		bounds.y = 2;
		bounds.width = width - 3;
		bounds.height = height - 4;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.stone, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) x, (int) y));
	}

	@Override
	public void knockBack(int xMove, int yMove) {}
}
