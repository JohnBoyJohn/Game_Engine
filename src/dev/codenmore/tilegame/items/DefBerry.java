package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class DefBerry extends Item{
	
	private int defPoints = 3;

	public DefBerry(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setDef(m.getDef() + defPoints);
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die Def Punkte des Monsters " + m.getName() + " wurden um " + defPoints + " gesteigert.");
		return res;
	}

}
