package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class MagicTear extends Item{
	
	private int magicPoints = 30;

	public MagicTear(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setHp(m.getMp() + magicPoints);
		if(m.getMp_max() < m.getMp())
			m.setMp(m.getMp_max());
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Die Magiepunkte von Monster " + m.getName() + " wurden gefüllt.");
		return res;
	}

}
