package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class HPBerry extends Item{
	
	private int hpPoints = 3;

	public HPBerry(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setHp_max(m.getHp_max() + hpPoints);
		m.setHp(m.getHp() + hpPoints);
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die HP des Monsters " + m.getName() + " wurden um " + hpPoints + " gesteigert.");
		return res;
	}

}
