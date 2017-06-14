package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class AttBerry extends Item{
	
	private int attPoints = 3;

	public AttBerry(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setAtt(m.getAtt() + attPoints);
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die Att-Punkte des Monsters " + m.getName() + " wurden um " + attPoints + " gesteigert.");
		return res;
	}

}
