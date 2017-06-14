package dev.codenmore.tilegame.items;

import java.awt.image.BufferedImage;

import dev.codenmore.tilegame.monsters.Monster;

public class Herb extends Item{
	
	private int healPoints = 30;

	public Herb(BufferedImage texture, String name, int id) {
		super(texture, name, id);
	}
	
	@Override
	public void use(Monster m){
		m.setHp(m.getHp() + healPoints);
		if(m.getHp_max() < m.getHp())
			m.setHp(m.getHp_max());
	}
	
	@Override
	public String getItemEffectMessage(Monster m){
		String res = String.valueOf("Monster " + m.getName() + " wurde geheilt.");
		return res;
	}

}
