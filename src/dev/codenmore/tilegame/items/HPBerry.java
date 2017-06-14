package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class HPBerry extends Item{
	
	private int healPoints = 3;

	public HPBerry(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setHp_max(m.getHp_max() + healPoints);
		m.setHp(m.getHp() + healPoints);
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die HP des Monsters " + m.getName() + " wurden um " + healPoints + " gesteigert.");
		return res;
	}

}
