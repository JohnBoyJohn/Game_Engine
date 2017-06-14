package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class MPBerry extends Item{
	
	private int magicPoints = 3;

	public MPBerry(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setMp_max(m.getMp_max() + magicPoints);
		m.setMp(m.getMp() + magicPoints);
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die MP des Monsters " + m.getName() + " wurden um " + magicPoints + " gesteigert.");
		return res;
	}

}
